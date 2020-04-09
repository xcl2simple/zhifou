package cn.archforce.zhifou.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author 隔壁老李
 * @date 2020/4/7 14:45
 * 问题实体类
 */
/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 10:33
 * @since JDK 1.8
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question extends BaseQuestion{

    /**
     * 标题
     */
    private String title;

    /**
     * 回答数
     */
    private Integer answeredNum;

    /**
     * 浏览量
     */
    private Integer viewedNum;

}
