package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.config.MyConfiguration;
import cn.archforce.zhifou.dao.IQuestionDao;
import cn.archforce.zhifou.mapper.QuestionMapper;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.service.IQuestionService;
import cn.archforce.zhifou.utils.TextUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:23
 * @since JDK 1.8
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private IQuestionDao questionDao;

    @Autowired
    private MyConfiguration myConfiguration;

    @Override
    public List<Question> selectQuestionByIndex(Integer sort, Integer startIndex, Integer num) {
        String order = sort.equals(0) ? "viewed_num desc" : "create_time desc";
        PageHelper.startPage(startIndex, num, order);
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
        String fileName = calendar.getTimeInMillis() + myConfiguration.SUFFIX_TEXT;
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
