package cn.archforce.zhifou.dao;

import cn.archforce.zhifou.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author 隔壁老李
 * @date 2020/3/31 15:34
 * 用户信息映射
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户id获取用户信息
     * @param id 用户id
     * @return
     */
    @Select("SELECT * FROM user WHERE id=#{id}")
    User getUserById(Long id);

    /**
     * 通过工号和密码获取用户信息（登录验证）
     * @param workNum 员工工号
     * @param password 加密后的密码
     * @return
     */
    @Select("SELECT * FROM user WHERE work_num=#{workNum} AND password=#{password}")
    User getUserByWorkNumAndPassword(String workNum, String password);

    /**
     * 通过工号和邮箱获取用户信息（忘记密码时验证工号与邮箱是否匹配）
     * @param workNum 员工工号
     * @param email 工号绑定的邮箱
     * @return
     */
    @Select("SELECT * FROM user WHERE work_num=#{workNum} AND email=#{email}")
    User getUserByWorkNumAndEmail(String workNum, String email);

    /**
     * 通过工号获取用户信息（注册时判断工号是否被注册）
     * @param workNum 员工工号
     * @return
     */
    @Select("SELECT * FROM user WHERE work_num=#{workNum}")
    User getUserByWorkNum(String workNum);

    /**
     * 通过邮箱获取用户信息（注册时判断邮箱是否被绑定）
     * @param email
     * @return
     */
    @Select("SELECT * FROM user WHERE email=#{email}")
    User getUserByEmail(String email);

    /**
     * 添加新的用户（注册）
     * @param user 用户对象
     * @return
     */
    @Insert("INSERT INTO user(work_num, name, department_id, job_id, email, avatar, password, score) " +
            "VALUES(#{workNum}, #{name}, #{departmentId}, #{jobId}, #{email}, #{avatar}, #{password}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(User user);

    /**
     * 修改密码
     * @param workNum 员工工号
     * @param password 新的密码
     * @return
     */
    @Update("UPDATE user SET password=#{password} WHERE work_num=#{workNum}")
    int updatePasswordByWorkNum(String workNum, String password);

    /**
     * 修改绑定的邮箱
     * @param id 当前登录的用户id
     * @param email 新的邮箱
     * @return
     */
    @Update("UPDATE user SET email=#{email} WHERE id=#{id}")
    int updateEmailById(Long id, String email);

    /**
     * 修改用户基本信息
     * @param id 当前登录用户id
     * @param name 新名字
     * @param departmentId 新部门编号
     * @param jobId 新岗位
     * @param avatar 头像地址
     * @return
     */
    @Update("UPDATE user SET name=#{name}, department_id=#{departmentId}, job_id=#{jobId}, avatar=#{avatar} WHERE id=#{id}")
    int updateUserInfoById(Long id, String name, Long departmentId, Long jobId, String avatar);

    /**
     * 增加积分
     * @param id 用户id
     * @param score 新增分数
     * @return
     */
    @Update("UPDATE user SET score=#{score}+score WHERE id=#{id}")
    int addScoreById(Long id, Long score);

}
