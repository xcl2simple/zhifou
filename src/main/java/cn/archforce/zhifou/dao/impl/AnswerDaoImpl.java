package cn.archforce.zhifou.dao.impl;

import cn.archforce.zhifou.dao.AnswerDao;
import cn.archforce.zhifou.mapper.AnswerMapper;
import cn.archforce.zhifou.model.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/8 19:22
 */
@Repository
public class AnswerDaoImpl implements AnswerDao {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public int addAnswer(Answer answer) {
        return answerMapper.insert(answer);
    }

    @Override
    public List<Answer> selectAnswerByQuestionId(Long questionId) {
        return answerMapper.selectByExample(Example.builder(Answer.class)
        .where(Sqls.custom().andEqualTo("questionId", questionId))
        .build());
    }
}
