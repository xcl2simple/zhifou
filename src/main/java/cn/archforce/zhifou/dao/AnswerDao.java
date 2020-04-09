package cn.archforce.zhifou.dao;

import cn.archforce.zhifou.model.entity.Answer;

import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/7 20:28
 * 回答通用接口
 */
public interface AnswerDao {

    /**
     * 根据问题id获取回答
     * @param questionId 问题id
     * @return
     */
    List<Answer> selectAnswerByQuestionId(Long questionId);

}
