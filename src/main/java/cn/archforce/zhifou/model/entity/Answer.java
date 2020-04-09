package cn.archforce.zhifou.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author 隔壁老李
 * @date 2020/4/7 16:58
 * 回答实体类
 */
/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 10:13
 * @since JDK 1.8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "answer")
public class Answer extends BaseQuestion{

    /**
     * 所属问题的ID
     */
    private Long questionId;

    /**
     * 点赞数
     */
    private Integer likeNum;

}


