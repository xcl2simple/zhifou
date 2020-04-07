package cn.archforce.zhifou.dao;

import cn.archforce.zhifou.model.entity.Question;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:13
 * @since JDK 1.8
 */
public interface IQuestionDao {

    Question select(Long id);

    Integer add(Question question);

}
