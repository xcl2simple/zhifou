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
     * 对应elasticsearch索引
     */
    public static final String INDEX_NAME = "zhifou";

    /**
     * 对应elasticsearch中type类型
     */
    public static final String TYPE = "question";

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

    /**
     * 状态，1-有效/0-被删除
     */
    private Integer status;

}
