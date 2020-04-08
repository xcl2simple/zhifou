package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.UserService;
import cn.archforce.zhifou.utils.CodeUtil;
import cn.archforce.zhifou.utils.EmailUtil;
import cn.archforce.zhifou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 隔壁老李
 * @date 2020/3/31 14:59
 * 用户操作
 */
@RestController
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
    public String sendEmail(String email){
        //判断邮箱是否符合规范
        if (email == null || !emailUtil.isFormat(email)){
            return CodeUtil.getParasIllegal();
        }
        return emailUtil.sendSimpleMail(email);
    }

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public String login(String number, String password, HttpServletResponse response){
        //判断工号是否符合规范
        if (number == null || !number.matches(numberPattern)){
            return CodeUtil.getParasIllegal();
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
    public String register(User user, Integer code){
        if (user == null || !user.getWorkNum().matches(numberPattern) || !emailUtil.isFormat(user.getEmail())){
            //参数不合格
            return CodeUtil.getParasIllegal();
        }
        Object tempCode = redisUtil.get(user.getEmail());
        if (tempCode == null || code != (int)tempCode){
            //验证码错误或失效
            return CodeUtil.getCodeError();
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
    public String retrievePassword(String number, String email, Integer code, String password){
        //检验参数合法性
        if (number == null || !number.matches(numberPattern) || email == null
                || !emailUtil.isFormat(email)|| code == null || password == null || password.length() != 6){
            return CodeUtil.getParasIllegal();
        }
        Object tempCode = redisUtil.get(email);
        if (tempCode == null || code != (int)tempCode){
            //验证码错误或失效
            return CodeUtil.getCodeError();
        }
        //检验工号和邮箱是否匹配
        if (userService.verifyEmail(number, email)){
            //匹配
            return userService.updatePassword(number, password);
        }
        return CodeUtil.getMismatching();
    }

    @PutMapping("/updateEmail")
    public String updateEmail(){

        return "";
    }

}
