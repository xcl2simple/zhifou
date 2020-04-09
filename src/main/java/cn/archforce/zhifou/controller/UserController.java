package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.UserService;
import cn.archforce.zhifou.utils.EmailUtil;
import cn.archforce.zhifou.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 隔壁老李
 * @date 2020/3/31 14:59
 * 用户操作
 */
@RestController
@Api(description = "用户相关操作接口")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 判断工号是否合规的正则
     */
    private String numberPattern = "^S[0-9]{4}$";

    /**
     * 发送邮箱验证码
     * @param email 目标邮箱
     * @return
     */
    @PostMapping("/sendEmail")
    public JsonResult sendEmail(String email){
        //判断邮箱是否符合规范
        if (email == null || !emailUtil.isFormat(email)){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        if (emailUtil.sendSimpleMail(email)){
            return JsonResult.success();
        }
        return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
    }

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(String number, String password, HttpServletResponse response){
        //判断工号是否符合规范
        if (number == null || !number.matches(numberPattern)){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        return userService.login(number, password, response);
    }

    /**
     * 注册
     * @param user 用户信息
     * @param code 验证码
     * @return
     */
    @PostMapping("/register")
    public JsonResult register(@RequestBody User user, Integer code){
        if (user == null || !user.getWorkNum().matches(numberPattern) || !emailUtil.isFormat(user.getEmail())){
            //参数不合格
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        String email = user.getEmail();
        Object tempCode = redisUtil.get(email);
        if (tempCode == null || code != (int)tempCode){
            //验证码错误或失效
            return JsonResult.failure(ResultCodeEnum.VERIFICATION_CODE_ERROR);
        }
        return userService.register(user);
    }

    /**
     * 找回密码
     * @param number 工号
     * @param email 工号绑定的邮箱
     * @param code 验证码
     * @param password 新密码
     * @return
     */
    @PutMapping("/retrievePassword")
    public JsonResult retrievePassword(String number, String email, Integer code, String password){
        //检验参数合法性
        if (number == null || !number.matches(numberPattern) || email == null
                || !emailUtil.isFormat(email)|| code == null || password == null || password.length() != 6){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        Object tempCode = redisUtil.get(email);
        if (tempCode == null || code != (int)tempCode){
            //验证码错误或失效
            return JsonResult.failure(ResultCodeEnum.VERIFICATION_CODE_ERROR);
        }
        //检验工号和邮箱是否匹配
        if (userService.verifyEmail(number, email)){
            //匹配
            return userService.updatePassword(number, password) ? JsonResult.success() : JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        return JsonResult.failure(ResultCodeEnum.EMAIL_NOT_MATCH_WORK_NUM);
    }

    @PutMapping("/updateEmail")
    public JsonResult updateEmail(){

        return null;
    }

}
