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
 * @date 2020/4/7 16:58
 * 回答实体类
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回答编号
     * 主键，自动递增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 问题id
     */
    @Column(name = "question_id")
    private Integer questionId;

    /**
     * 回答者id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 内容地址
     */
    @Column(name = "content")
    private String content;

    /**
     * 回答时间，格式化
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 喜欢的数量
     */
    @Column(name = "like_num")
    private Integer likeNum;

    /**
     * 回答的用户
     * 不会作为表字段
     */
    @Transient
    private User user;

}
