package cn.archforce.zhifou.dao.impl;

import cn.archforce.zhifou.dao.IQuestionDao;
import cn.archforce.zhifou.mapper.QuestionMapper;
import cn.archforce.zhifou.model.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:14
 * @since JDK 1.8
 */
@Repository
public class QuestionDaoImpl implements IQuestionDao {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Question select(Long id) {
        return questionMapper.selectOneByExample(Example.builder(Question.class)
        .where(Sqls.custom().andEqualTo("id", id))
        .build());
    }

    /**
     * 添加问题
     * @param question
     * @return
     */
    @Override
    public Integer add(Question question) {
        return questionMapper.insertSelective(question);
    }
}
