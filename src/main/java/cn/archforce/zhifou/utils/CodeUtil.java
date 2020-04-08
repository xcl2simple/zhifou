package cn.archforce.zhifou.utils;

/**
 * @author 隔壁老李
 * @date 2020/3/31 17:59
 * 状态码
 */
public class CodeUtil {
    /**
     * 请求成功
     */
    public static final Integer SUCCESS = 0;

    /**
     * 服务器异常
     */
    public static final Integer SERVER_ERROR = -1;

    /**
     * 参数不合法
     */
    public static final Integer PARAS_ILLEGAL = 1;

    /**
     * 工号不存在或密码错误
     */
    public static final Integer NUMBER_PSW_ERROR = 2;

    /**
     * 工号已经注册
     */
    public static final Integer NUMBER_EXISTS = 3;

    /**
     * 邮箱已被绑定
     */
    public static final Integer EMAIl_BEEN_BOUND = 4;

    /**
     * 未登录
     */
    public static final Integer NOT_LOGIN = 5;

    /**
     * 邮箱与工号不匹配
     */
    public static final Integer MISMATCHING = 6;

    /**
     * 验证码错误
     */
    public static final Integer CODE_ERROR = 7;

    /**
     * 已经点过赞了
     */
    public static final Integer ALREADY_LIKE = 8;

    /**
     * 还没点赞
     */
    public static final Integer NOT_LIKE = 9;

    /**
     * 已经收藏了
     */
    public static final Integer ALREADY_COLLECT = 10;

    /**
     * 还没收藏
     */
    public static final Integer NOT_COLLECT = 11;

    public static String getSuccess() {
        return "{\"code\":" + SUCCESS + ", msg:请求成功}";
    }

    public static String getServerError() {
        return "{\"code\":" + SERVER_ERROR + ", msg:服务器异常}";
    }

    public static String getParasIllegal() {
        return "{\"code\":" + PARAS_ILLEGAL + ", msg:参数非法}";
    }

    public static String getNumberPswError() {
        return "{\"code\":" + NUMBER_PSW_ERROR + ", msg:工号或密码错误}";
    }

    public static String getNumberExists() {
        return "{\"code\":" + NUMBER_EXISTS + ", msg:工号已注册}";
    }

    public static String getEmailBeenBound() {
        return "{\"code\":" + EMAIl_BEEN_BOUND + ", msg:邮箱已被绑定}";
    }
    
    public static String getNotLogin() {
        return "{\"code\":" + NOT_LOGIN + ", msg:未登录}";
    }

    public static String getMismatching() {
        return "{\"code\":" + MISMATCHING + ", msg:邮箱与工号不匹配}";
    }

    public static String getCodeError() {
        return "{\"code\":" + CODE_ERROR + ", msg:验证码错误}";
    }

    public static String getAlreadyLike() {
        return "{\"code\":" + ALREADY_LIKE + ", msg:已喜欢}";
    }

    public static String getNotLike() {
        return "{\"code\":" + NOT_LIKE + ", msg:未喜欢}";
    }

    public static String getAlreadyCollect() {
        return "{\"code\":" + ALREADY_COLLECT + ", msg:已收藏}";
    }

    public static String getNotCollect() {
        return "{\"code\":" + NOT_COLLECT + ", msg:未收藏}";
    }

}
