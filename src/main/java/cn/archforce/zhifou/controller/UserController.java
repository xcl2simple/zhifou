package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.UserService;
import cn.archforce.zhifou.utils.EmailUtil;
import cn.archforce.zhifou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @ApiOperation(value = "发送验证码邮件", notes = "参数email是用户邮箱")
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
    @ApiOperation(value = "登录", notes = "workNum、password分别为工号和密码")
    @PostMapping("/login")
    public JsonResult login(String workNum, String password, HttpServletResponse response){
        //判断工号是否符合规范
        if (workNum == null || !workNum.matches(numberPattern)){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        return userService.login(workNum, password, response);
    }

    /**
     * 注销
     * @param response
     * @return
     */
    @ApiOperation(value = "注销")
    @PostMapping("/logout")
    public JsonResult logout(HttpServletResponse response){
        return userService.logout(response);
    }

    /**
     * 注册
     * @param user 用户信息
     * @param code 验证码
     * @return
     */
    @ApiOperation(value = "注册", notes = "除了头像以外的所有用户信息，以及验证码")
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
     * @param workNum 工号
     * @param email 工号绑定的邮箱
     * @param code 验证码
     * @param password 新密码
     * @return
     */
    @ApiOperation(value = "找回密码", notes = "工号，邮箱，验证码，新密码")
    @PutMapping("/retrievePassword")
    public JsonResult retrievePassword(String workNum, String email, Integer code, String password){
        //检验参数合法性
        if (workNum == null || !workNum.matches(numberPattern) || email == null
                || !emailUtil.isFormat(email)|| code == null || password == null || password.length() != 6){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        Object tempCode = redisUtil.get(email);
        if (tempCode == null || code != (int)tempCode){
            //验证码错误或失效
            return JsonResult.failure(ResultCodeEnum.VERIFICATION_CODE_ERROR);
        }
        //检验工号和邮箱是否匹配
        if (userService.verifyEmail(workNum, email)){
            //匹配
            return userService.updatePassword(workNum, password) ? JsonResult.success() : JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        return JsonResult.failure(ResultCodeEnum.EMAIL_NOT_MATCH_WORK_NUM);
    }

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/getInfo")
    public JsonResult getInfo(HttpServletRequest request){
        return userService.getInfo(request);
    }

    /**
     * 获取积分排行榜
     * @param topNum 排名前多少位,默认20
     * @return
     */
    @ApiOperation(value = "获取积分排行榜")
    @GetMapping("/user/score/list")
    public JsonResult getScoreList(@RequestParam("topNum") Integer topNum){
        if (topNum <= 0){
            return userService.getScoreList(20);
        }
        return userService.getScoreList(topNum);
    }

    @PutMapping("/updateEmail")
    public JsonResult updateEmail(){

        return null;
    }

}
