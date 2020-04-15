package cn.archforce.zhifou.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 隔壁老李
 * @date 2020/4/6 15:10
 * token身份验证
 */
public class TokenUtil {

    /**
     * 过期时间15天
     */
    private static final long EXPIRE_TIME = 15 * 24 * 60 * 60 * 1000;

    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "zhifou";

    /**
     * 生成token，15天后过期
     *
     * @param workNum 工号
     * @param userId 用户ID
     * @return 加密的token
     */
    public static String getToken(String workNum, Long userId) {
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("type", "JWT");
            header.put("alg", "HMAC256");
            //附带username，password信息，生成token
            return JWT.create()
                    .withHeader(header)
                    .withClaim("workNum", workNum)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token 令牌
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取登录工号
     *
     * @param token
     * @return
     */
    public static String getWorkNum(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("workNum").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取登录用户ID
     *
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
