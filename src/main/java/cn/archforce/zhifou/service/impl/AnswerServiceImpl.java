package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.config.MyConfiguration;
import cn.archforce.zhifou.dao.AnswerDao;
import cn.archforce.zhifou.dao.DepartmentDao;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.model.entity.Answer;
import cn.archforce.zhifou.model.entity.Author;
import cn.archforce.zhifou.model.entity.Job;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.AnswerService;
import cn.archforce.zhifou.utils.TokenUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/8 18:59
 * @roject zhifou
 */
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private MyConfiguration myConfiguration;

    @Override
    public boolean addAnswer(HttpServletRequest request, Answer answer) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        answer.setUserId(userId);
        answer.setCreateTime(new Date());
        answer.setLikeNum(0);
        if (answerDao.addAnswer(answer) != 1){
            return false;
        }
        //发布回答加2分
        userMapper.addScoreById(answer.getUserId(), 2L);
        return true;
    }

    @Override
    public List<Answer> getAnswerList(Long questionId, Integer sort, Integer pageNum, Integer pageSize) {
        String orderBy;
        if (sort == 1){
            orderBy = "like_num DESC";
        }else {
            orderBy = "create_time DESC";
        }
        //设置页码，数据量和排序方式
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<Answer> answers = answerDao.selectAnswerByQuestionId(questionId);
        if (getUserInfo(answers)){
            return answers;
        }
        return new ArrayList<>();
    }

    /**
     * 获取回答者的信息
     * @param answers 回答的列表
     */
    private boolean getUserInfo(List<Answer> answers){
        if (answers != null){
            try {
                Iterator<Answer> iterator = answers.iterator();
                Answer answer;
                User user;
                Author author;
                Job job;
                while (iterator.hasNext()){
                    answer = iterator.next();
                    user = userMapper.getUserById(answer.getUserId());
                    job = departmentDao.getJob(user.getJobId());
                    author = new Author(user.getId(), user.getName(), job.getJobName(), user.getAvatar());
                    answer.setAuthor(author);
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
