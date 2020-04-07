package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.IQuestionDao;
import cn.archforce.zhifou.mapper.QuestionMapper;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Question> selectQuestionByIndex(Integer sort, Integer startIndex) {
        return questionMapper.selectAll();
    }

    @Override
    public boolean addQuestion(Question question) {
        if (question == null || exists(question)) {
            return false;
        }
        questionDao.add(question);
        return true;
    }

    @Override
    public boolean exists(Question question) {
        return false;
    }
}
