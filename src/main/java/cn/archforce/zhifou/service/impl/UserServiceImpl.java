package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.dao.DepartmentDao;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.model.entity.Score;
import cn.archforce.zhifou.model.entity.User;
import cn.archforce.zhifou.service.UserService;
import cn.archforce.zhifou.utils.PasswordUtil;
import cn.archforce.zhifou.utils.RedisUtil;
import cn.archforce.zhifou.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 隔壁老李
 * @date 2020/3/31 17:18
 * 对用户请求的处理的具体实现
 */
@Slf4j
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    RedisUtil redisUtil;

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
        log.info("token: " + token);
        //将token写到响应头部，以待以后的请求认证
        response.setHeader("token", token);
        response.setHeader("Access-Control-Expose-Headers", "token");
        return JsonResult.success(token);
    }

    @Override
    public JsonResult logout(HttpServletResponse response) {
        response.setHeader("token", "");
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
        user.setScore(0L);
        if (userMapper.addUser(user) != 1){
            //注册失败
            return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        return JsonResult.success();
    }

    @Override
    public Boolean verifyEmail(String workNum, String email) {
        //判断是否可由工号和邮箱查询出用户信息
        return  (userMapper.getUserByWorkNumAndEmail(workNum, email) != null);
    }

    @Override
    public Boolean updatePassword(String workNum, String password) {
        String encryptPassword = PasswordUtil.encryption(password);
        return  (userMapper.updatePasswordByWorkNum(workNum, encryptPassword) == 1);
    }

    @Override
    public JsonResult getInfo(HttpServletRequest request) {
        Long id = TokenUtil.getUserId(request.getHeader("token"));
        User user = userMapper.getUserById(id);
        if (user == null){
            return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        String department = departmentDao.getDepartment(user.getDepartmentId()).getDepartmentName();
        String job = departmentDao.getJob(user.getJobId()).getJobName();
        Json json = new Json("{\"id\":" + user.getId() + ",\"name\":\"" + user.getName() + "\",\"department\":\"" + department
                + "\",\"job\":\"" + job + "\",\"avatar\":\"" + user.getAvatar() + "\"}");
        return JsonResult.success(json);
    }

    @Override
    public String updateEmail(Long id, String email) {
        return null;
    }

    @Override
    public String updateUserInfo(Long id, String name, Long departmentId, Long jobId) {
        return null;
    }

    @Override
    public JsonResult getScoreList(Integer topNum) {
        Set<Object> scoreSet = redisUtil.zGetList(topNum);
        if (scoreSet == null){
            //Redis缓存中的数据不满足条件，查询数据库
            List<Score> scores = userMapper.getScoreList(topNum);
            if (scores == null){
                log.info("获取积分排行榜失败");
                return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION, new ArrayList<>());
            }
            log.info("数据库获取排行榜: " + scores);
            //更新缓存
            redisUtil.zAddList(scores);
            return JsonResult.success(scores);
        }else {
            log.info("redis缓存获取排行榜：" + scoreSet);
            return JsonResult.success(scoreSet);
        }
    }
}