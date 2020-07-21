package cn.archforce.zhifou.model.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author 隔壁老李
 * @date 2020/5/14 17:26
 * 分数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 分数
     */
    private Long score;

}
