package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.config.MyConfiguration;
import cn.archforce.zhifou.dao.IQuestionDao;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.mapper.QuestionMapper;
import cn.archforce.zhifou.service.IQuestionService;
import cn.archforce.zhifou.utils.TextUtil;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("questionService")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private IQuestionDao questionDao;

    @Autowired
    private MyConfiguration myConfiguration;

    @Override
    public Question getQuestionDetails(Long questionId) {
        //通过id获取问题信息
        Question question = questionDao.select(questionId);
        if (question == null){
            //获取问题失败
            return null;
        }
        User author = userMapper.getUserById(question.getUserId());
        if (author == null){
            //获取问题发布者信息失败
            return question;
        }
        //将发布者信息绑定到问题中返回
        question.setAuthor(author);
        return question;
    }

    @Override
    public List<Question> selectQuestionByIndex(Integer sort, Integer startIndex) {
        return questionMapper.selectAll();
    }

    @Override
    public boolean addQuestion(Question question) {
        if (question == null || exists(question)) {
            return false;
        }

        String txtPath = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String yearAndMonth = year + (month / 10 > 0 ? "" + month : "0" + month);
        String fileName = calendar.getTimeInMillis() + MyConfiguration.SUFFIX_TEXT;
        try {
             txtPath = TextUtil.saveToLocal(question.getContent(), myConfiguration.getTextRoot() + yearAndMonth, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        question.setContent(txtPath);
        question.setCreateTime(new Date());
        question.setAnsweredNum(0);
        question.setViewedNum(0);

        questionDao.add(question);
        return true;
    }

    @Override
    public boolean exists(Question question) {
        return false;
    }
}
