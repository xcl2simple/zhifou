package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.UserService;
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
    public JsonResult login(String workNum, String password, HttpServletResponse response) {
        User user = userMapper.getUserByWorkNumAndPassword(workNum, PasswordUtil.encryption(password));
        if (user == null){
            //账号或密码错误
            return JsonResult.failure(ResultCodeEnum.WORK_NUM_NOT_EXIST_OR_WRONG_PASSWORD);
        }
        //生成token
        String token = TokenUtil.getToken(user.getWorkNum(), user.getId());
        if (token == null){
            return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        System.out.println(token);
        //将token写到响应头部，以待以后的请求认证
        response.setHeader("token", token);
        return JsonResult.success();
    }

    @Override
    public JsonResult register(User user) {
        if (userMapper.getUserByWorkNum(user.getWorkNum()) != null){
            //工号已注册
            return JsonResult.failure(ResultCodeEnum.WORK_NUM_IS_ALREADY_REGISTERED);
        }else if (userMapper.getUserByEmail(user.getEmail()) != null){
            //邮箱已被绑定
            return JsonResult.failure(ResultCodeEnum.EMAIL_IS_ALREADY_BOUND);
        }
        //密码加密
        user.setPassword(PasswordUtil.encryption(user.getPassword()));
        if (userMapper.addUser(user) != 1){
            //注册失败
            return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        return JsonResult.success();
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
    public Boolean updatePassword(String workNum, String password) {
        String encryptPassword = PasswordUtil.encryption(password);
        if (userMapper.updatePasswordByWorkNum(workNum, encryptPassword) != 1){
            //修改失败
            return false;
        }
        return true;
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