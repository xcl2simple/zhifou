package cn.archforce.zhifou.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 隔壁老李
 * @date 2020/3/31 15:11
 * 用户实体类
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private Integer departmentId;

    /**
     * 岗位编号
     */
    @Column(name = "job_id")
    private Integer jobId;

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
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPass() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", workNum='" + workNum + '\'' +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                ", jobId=" + jobId +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", score=" + score +
                '}';
    }
}
