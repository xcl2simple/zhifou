package cn.archforce.zhifou.service;

import cn.archforce.zhifou.model.entity.Question;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:23
 * @since JDK 1.8
 */
public interface IQuestionService {

    Map<String, Object> selectQuestionByIndex(Integer sort, Integer pageNum,Integer pageSize);

    boolean addQuestion(HttpServletRequest request, Question question);

    /**
     * 根据问题id获取问题详情
     * @param questionId 问题id
     * @return
     */
    Question getQuestionDetails(Long questionId);

    Map<String, Object> searchQuestion(Integer sort, Integer pageNum, Integer pageSize, String searchTitle);

    List<Question> suggestQuestion(String title);

}
