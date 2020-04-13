package cn.archforce.zhifou.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 10:36
 * @since JDK 1.8
 */
@Getter
@Setter
@ToString
public class BaseQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected Long id;

    /**
     * 发布者的用户ID
     */
    protected Long userId;

    /**
     * 问题、回答、文章的详情，存txt地址
     */
    protected String content;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 发布者部分信息
     * 不会作为表字段
     */
    @Transient
    protected Author author;

}
