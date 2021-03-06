package cn.archforce.zhifou.service;

import cn.archforce.zhifou.model.entity.Answer;
import cn.archforce.zhifou.model.entity.AnswerDraft;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 隔壁老李
 * @date 2020/4/8 18:57
 * @roject zhifou
 */
public interface AnswerService {

    /**
     * 添加回答
     * @param answer
     * @return
     */
    boolean addAnswer(HttpServletRequest request, Answer answer);

    /**
     * 根据参数获取问题的回答列表
     * @param questionId
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String, Object> getAnswerList(Long questionId, Integer sort, Integer pageNum, Integer pageSize);

    Integer addOrUpdateAnswerDraft(HttpServletRequest request, AnswerDraft answerDraft);
}
