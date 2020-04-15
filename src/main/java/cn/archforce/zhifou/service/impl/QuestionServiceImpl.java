package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.DepartmentDao;
import cn.archforce.zhifou.dao.IQuestionDao;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.mapper.QuestionMapper;
import cn.archforce.zhifou.model.entity.Author;
import cn.archforce.zhifou.model.entity.Job;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.IQuestionService;
import cn.archforce.zhifou.utils.ElasticUtil;
import cn.archforce.zhifou.utils.TokenUtil;
import com.github.pagehelper.PageHelper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


/**
 * @author 隔壁老李
 * @date 2020/4/7 15:15
 * 问题服务层实现
 */
/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:23
 * @since JDK 1.8
 */
@Slf4j
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("questionService")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private IQuestionDao questionDao;

    @Override
    public Question getQuestionDetails(Long questionId) {
        //通过id获取问题信息
        Question question = questionDao.select(questionId);
        if (question == null){
            //获取问题失败
            return null;
        }
        User user = userMapper.getUserById(question.getUserId());
        if (user == null){
            //获取问题发布者信息失败
            return question;
        }
        Job job = departmentDao.getJob(user.getJobId());
        Author author = new Author(user.getId(), user.getName(), job.getJobName(), user.getAvatar());
        //将发布者信息绑定到问题中返回
        question.setAuthor(author);
        return question;
    }

    /**
     * 首页分页推荐问题
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Question> selectQuestionByIndex(Integer sort, Integer pageNum, Integer pageSize) {
        String orderBy = sort.equals(1) ? "viewed_num DESC" : "create_time DESC";
        Integer index = pageNum < 1 ? 1 : pageNum;
        Integer number = pageSize < 1 ? 1 : pageSize;
        PageHelper.startPage(index, number, orderBy);
        List<Question> questions = questionMapper.selectByExample(Example.builder(Question.class)
                .where(Sqls.custom().andEqualTo("status", 1))
                .build());


        if (setUserInfo(questions)){
            return questions;
        }
        return new ArrayList<>();
    }

    /**
     * 发布问题
     *
     * @param question
     * @return
     */
    @Override
    public boolean addQuestion(HttpServletRequest request, Question question) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        question.setUserId(userId);
        question.setCreateTime(new Date());
        question.setAnsweredNum(0);
        question.setViewedNum(0);
        question.setStatus(1);

        if (questionDao.add(question) != 1) {
            return false;
        }
        return true;
    }

    /**
     * 为问题设置作者的信息
     * @param questions 问题列表
     */
    private boolean setUserInfo(List<Question> questions){
        if (questions != null){
            try {
                Iterator<Question> iterator = questions.iterator();
                Question question;
                User user;
                Author author;
                Job job;
                while (iterator.hasNext()){
                    question = iterator.next();
                    user = userMapper.getUserById(question.getUserId());
                    job = departmentDao.getJob(user.getJobId());
                    author = new Author(user.getId(), user.getName(), job.getJobName(), user.getAvatar());
                    question.setAuthor(author);
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 根据标题查询问题
     * @param sort
     * @param pageNum
     * @param pageSize
     * @param searchTitle
     * @return
     */
    @Override
    public List<Question> searchQuestion(Integer sort, Integer pageNum, Integer pageSize, String searchTitle) {
        String dslStr =  ElasticUtil.getSearchDsl(sort, pageNum, pageSize, searchTitle);

        log.info("dsl : " + dslStr);

        // 用api执行复杂查询
        List<Question> questions = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex(Question.INDEX_NAME).addType(Question.TYPE).build();

        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<Question, Void>> hits = execute.getHits(Question.class);

        for (SearchResult.Hit<Question, Void> hit : hits) {
            Question source = hit.source;

            //获取高亮内容
            Map<String, List<String>> highlight = hit.highlight;
            if(highlight!=null){
                String title = highlight.get("title").get(0);
                source.setTitle(title);
            }
            questions.add(source);
        }

        log.info("" + questions.toString());

        return questions;
    }

    /**
     * 推荐已有回答的相似问题
     * @param title
     * @return
     */
    @Override
    public List<Question> suggestQuestion(String title) {
        String dslStr =  ElasticUtil.getSearchDsl(1, 1, 10, title);

        log.info("dsl : " + dslStr);

        // 用api执行复杂查询
        List<Question> questions = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex(Question.INDEX_NAME).addType(Question.TYPE).build();

        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<Question, Void>> hits = execute.getHits(Question.class);
        for (SearchResult.Hit<Question, Void> hit : hits) {
            Question source = hit.source;
            questions.add(source);
        }

        log.info("" + questions.toString());

        return questions;
    }

}
