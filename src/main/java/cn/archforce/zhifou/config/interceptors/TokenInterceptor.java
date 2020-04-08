package cn.archforce.zhifou.config.interceptors;

import cn.archforce.zhifou.utils.CodeUtil;
import cn.archforce.zhifou.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 隔壁老李
 * @date 2020/4/6 15:29
 * 对于每个请求进行登录验证
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            response.setCharacterEncoding("utf-8");
            String token = request.getHeader("token");
            //token不存在
            if (token != null) {
                //验证token是否正确
                boolean result = TokenUtil.verify(token);
                if (result) {
                    //验证成功更新token，防止使用的期间出现登录失效的情况
                    String workNum = TokenUtil.getWorkNum(token);
                    Integer userId = TokenUtil.getUserId(token);
                    System.out.println(workNum + ", " + userId);
                    response.setHeader("token", TokenUtil.getToken(workNum, userId));
                    return true;
                }
            }
            //未登录
            response.getWriter().write(CodeUtil.getNotLogin());
            return false;
        } catch (IOException e) {
            try {
                //返回服务器异常
                response.getWriter().write(CodeUtil.getServerError());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
