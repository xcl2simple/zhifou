package cn.archforce.zhifou.dao;

import cn.archforce.zhifou.mappers.BaseMapper;
import cn.archforce.zhifou.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author 隔壁老李
 * @date 2020/4/7 15:08
 * 问题通用接口
 */
@Mapper
@Repository
public interface QuestionMapper extends BaseMapper<Question> {
}
