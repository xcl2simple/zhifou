package cn.archforce.zhifou.mappers;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 隔壁老李
 * @date 2020/4/7 14:29
 * 通用mapper
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
