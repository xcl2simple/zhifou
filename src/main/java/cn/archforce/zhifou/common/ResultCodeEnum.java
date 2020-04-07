package cn.archforce.zhifou.common;

/**
 * Description: 状态码枚举类
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/5 19:28
 * @since JDK 1.8
 */
public enum ResultCodeEnum {

    SUCCESS(0, "请求成功"),
    SEVER_EXCEPTION(-1, "服务器异常"),
    PARAM_IS_INVALID(1, "参数不合法"),
    WORK_NUM_NOT_EXIST_OR_WRONG_PASSWORD(2, "工号不存在或密码错误"),
    WORK_NUM_IS_ALREADY_REGISTERED(3, "工号已注册"),
    EMAIL_IS_ALREADY_BOUND(4, "邮箱已被绑定"),
    USER_NOT_LOG_IN(5, "用户未登录"),
    EMAIL_NOT_MATCH_WORK_NUM(6, "邮箱与工号不匹配"),
    VERIFICATION_CODE_ERROR(7, "验证码错误"),
    ALREADY_LIKED(8, "已经点过赞了"),
    NOT_YET_LIKED(9, "还没有点赞"),
    ALREADY_FAVORITED(10, "已经收藏过了"),
    NOT_YET_FAVORITED(11, "还没有收藏"),
    ALREADY_FOLLOWED(12, "已经关注过了"),
    NOT_YET_FOLLOWED(13, "还没有关注");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
