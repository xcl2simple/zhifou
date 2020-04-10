package cn.archforce.zhifou.model.entity;

import lombok.*;

/**
 * @author 隔壁老李
 * @date 2020/4/10 12:56
 * 作者部分信息
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位名称
     */
    private String job;

    /**
     * 头像地址
     */
    private String avatar;

}
