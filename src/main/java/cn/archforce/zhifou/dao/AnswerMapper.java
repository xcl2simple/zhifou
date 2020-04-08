package cn.archforce.zhifou.dao;

import cn.archforce.zhifou.mappers.BaseMapper;
import cn.archforce.zhifou.model.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/7 20:28
 * 回答通用接口
 */
@Repository
@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

    /**
     * 根据问题id获取回答
     * @param questionId 问题id
     * @return
     */
    @Select("SELECT * FROM answer WHERE question_id=#{questionID}")
    List<Answer> selectByQuestionId(Integer questionId);

}
