package cn.archforce.zhifou.utils;

/**
 * @author 隔壁老李
 * @date 2020/4/7 21:25
 * 简单的加密解密工具
 */
public class PasswordUtil {

    /**
     * 秘钥
     */
    private final static Integer KEY = 111111;

    /**
     * 简单的加密与解密，与秘钥按位与运算，一次加密，两次解密
     * @param password 原密码
     * @return
     */
    public static String encryption(String password){
        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length; i++){
            chars[i] = (char)(chars[i] ^ KEY);
        }
        System.out.println(new String(chars));
        return new String(chars);
    }

}
