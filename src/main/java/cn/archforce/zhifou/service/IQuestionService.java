package cn.archforce.zhifou.service;

import cn.archforce.zhifou.model.entity.Question;

import java.util.List;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:23
 * @since JDK 1.8
 */
public interface IQuestionService {

    List<Question> selectQuestionByIndex(Integer sort, Integer startIndex);

    boolean addQuestion(Question question);

    boolean exists(Question question);

}
