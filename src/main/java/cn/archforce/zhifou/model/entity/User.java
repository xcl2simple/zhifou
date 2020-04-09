package cn.archforce.zhifou.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 隔壁老李
 * @date 2020/3/31 15:11
 * 用户实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工号
     */
    @Column(name = "work_num")
    private String workNum;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 部门编号
     */
    @Column(name = "department_id")
    private Long departmentId;

    /**
     * 岗位编号
     */
    @Column(name = "job_id")
    private Long jobId;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 头像地址
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 积分
     */
    @Column(name = "score")
    private Long score;

}
