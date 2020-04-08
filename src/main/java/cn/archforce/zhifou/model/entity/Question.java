package cn.archforce.zhifou.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author 隔壁老李
 * @date 2020/4/7 14:45
 * 问题实体类
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 问题编号
     * 主键，自动递增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容地址
     */
    @Column(name = "content")
    private String content;

    /**
     * 提问者id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 发布时间，格式化
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 回答的数量
     */
    @Column(name = "answered_num")
    private Integer answeredNum;

    /**
     * 浏览量
     */
    @Column(name = "viewed_num")
    private Integer viewedNum;

    /**
     * 发布者信息
     * 不会作为表字段
     */
    @Transient
    private User author;

}
