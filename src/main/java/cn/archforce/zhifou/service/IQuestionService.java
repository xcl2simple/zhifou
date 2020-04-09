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

    List<Question> selectQuestionByIndex(Integer sort, Integer startIndex,Integer num);

    boolean addQuestion(Question question);

    /**
     * 根据问题id获取问题详情
     * @param questionId 问题id
     * @return
     */
    Question getQuestionDetails(Long questionId);

    boolean exists(Question question);

}
