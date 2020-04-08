package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.UserMapper;
import com.alibaba.fastjson.JSONObject;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.UserService;
import cn.archforce.zhifou.utils.CodeUtil;
import cn.archforce.zhifou.utils.PasswordUtil;
import cn.archforce.zhifou.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 隔壁老李
 * @date 2020/3/31 17:18
 * 对用户请求的处理的具体实现
 */
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String workNum, String password, HttpServletResponse response) {
        User user = userMapper.getUserByWorkNumAndPassword(workNum, PasswordUtil.encryption(password));
        if (user == null){
            //账号或密码错误
            return CodeUtil.getNumberPswError();
        }
        JSONObject object = new JSONObject();
        object.put("code", CodeUtil.SUCCESS);
        object.put("user", user);
        //生成token
        String token = TokenUtil.getToken(user.getWorkNum(), user.getId());
        if (token == null){
            return CodeUtil.getServerError();
        }
        System.out.println(token);
        //将token写到响应头部，以待以后的请求认证
        response.setHeader("token", token);
        return object.toJSONString();
    }

    @Override
    public String register(User user) {
        if (userMapper.getUserByWorkNum(user.getWorkNum()) != null){
            //工号已注册
            return CodeUtil.getNumberExists();
        }else if (userMapper.getUserByEmail(user.getEmail()) != null){
            //邮箱已被绑定
            return CodeUtil.getEmailBeenBound();
        }
        //密码加密
        user.setPassword(PasswordUtil.encryption(user.getPass()));
        if (userMapper.addUser(user) != 1){
            //注册失败
            return CodeUtil.getServerError();
        }
        return CodeUtil.getSuccess();
    }

    @Override
    public Boolean verifyEmail(String workNum, String email) {
        //判断是否可由工号和邮箱查询出用户信息
        if (userMapper.getUserByWorkNumAndEmail(workNum, email) == null){
            //不匹配
            return false;
        }
        return true;
    }

    @Override
    public String updatePassword(String workNum, String password) {
        if (userMapper.updatePasswordByWorkNum(workNum, password) != 1){
            //修改失败
            return CodeUtil.getServerError();
        }
        return CodeUtil.getSuccess();
    }

    @Override
    public String updateEmail(Integer id, String email) {
        return null;
    }

    @Override
    public String updateUserInfo(Integer id, String name, String department, String post) {
        return null;
    }

}