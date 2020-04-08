package cn.archforce.zhifou.service;

/**
 * @author 隔壁老李
 * @date 2020/4/7 15:13
 * 问题服务层接口
 */
public interface QuestionService {

    /**
     * 根据问题id获取问题详情
     * @param questionId 问题id
     * @return
     */
    String getQuestionDetails(Integer questionId);

    /**
     * 获取问题列表
     * @return
     */
    String getQuestionList();

    /**
     * 添加回答
     * @param userId 回答者id
     * @param questionId 问题id
     * @param content 回答内容
     * @return
     */
    String addAnswer(Integer userId, Integer questionId, String content);

    /**
     * 根据参数获取问题的回答列表
     * @param questionId
     * @param sort
     * @param startIndex
     * @param num
     * @return
     */
    String getAnswerList(Integer questionId, Integer sort, Integer startIndex, Integer num);

}
