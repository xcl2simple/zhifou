package cn.archforce.zhifou.service;

import cn.archforce.zhifou.model.entity.User;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 隔壁老李
 * @date 2020/3/31 16:48
 * 对用户请求的处理与实现的接口
 */
public interface UserService {

    /**
     * 对身份进行验证
     * @param workNum 工号
     * @param password 密码
     * @param response
     * @return
     */
    String login(String workNum, String password, HttpServletResponse response);

    /**
     * 用户注册
     * @param user 用户对象
     * @return
     */
    String register(User user);

    /**
     * 忘记密码时，验证工号与绑定邮箱是否匹配
     * @param number 工号
     * @param email 邮箱
     * @return
     */
    Boolean verifyEmail(String number, String email);

    /**
     * 忘记密码时，修改密码
     * @param number 工号
     * @param password 新密码
     * @return
     */
    String updatePassword(String number, String password);

    /**
     * 修改绑定的邮箱
     * @param id 用户id
     * @param email 新邮箱
     * @return
     */
    String updateEmail(Integer id, String email);

    /**
     * 修改用户基本信息
     * @param id 用户id
     * @param name 姓名
     * @param department 部门
     * @param post 岗位
     * @return
     */
    String updateUserInfo(Integer id, String name, String department, String post);

}
