package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.AnswerMapper;
import cn.archforce.zhifou.dao.QuestionMapper;
import cn.archforce.zhifou.dao.UserMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import cn.archforce.zhifou.model.entity.Answer;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.QuestionService;
import cn.archforce.zhifou.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/7 15:15
 * 问题服务层实现
 */
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getQuestionDetails(Integer questionId) {
        //通过id获取问题信息
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if (question == null){
            //获取问题失败
            return CodeUtil.getServerError();
        }
        User author = userMapper.getUserById(question.getUserId());
        if (author == null){
            //获取问题发布者信息失败
            return CodeUtil.getServerError();
        }
        //将发布者信息绑定到问题中返回
        question.setAuthor(author);
        JSONObject object = new JSONObject();
        object.put("code", CodeUtil.SUCCESS);
        object.put("question", question);
        return object.toJSONString();
    }

    @Override
    public String getQuestionList() {
        return null;
    }

    @Override
    public String addAnswer(Integer userId, Integer questionId, String content) {

        return null;
    }

    @Override
    public String getAnswerList(Integer questionId, Integer sort, Integer startIndex, Integer num) {
        String orderBy;
        if (sort == 1){
            orderBy = "like_num DESC";
        }else {
            orderBy = "create_time DESC";
        }
        //设置页码，数据量和排序方式
        PageHelper.startPage(startIndex, num, orderBy);
        List<Answer> answers = answerMapper.selectByQuestionId(questionId);
        if (getUserInfo(answers)){
            JSONObject object = new JSONObject();
            object.put("code", CodeUtil.SUCCESS);
            object.put("answers", answers);
            return object.toJSONString();
        }
        return CodeUtil.getServerError();
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
                while (iterator.hasNext()){
                    answer = iterator.next();
                    user = userMapper.getUserById(answer.getUserId());
                    answer.setUser(user);
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
