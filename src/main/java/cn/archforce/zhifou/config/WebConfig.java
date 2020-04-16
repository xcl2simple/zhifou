package cn.archforce.zhifou.config;

import cn.archforce.zhifou.config.interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 隔壁老李
 * @date 2020/4/6 15:29
 * 拦截请求，登录验证
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MyConfiguration myConfiguration;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login") 表示不拦截这些请求
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/**")
                .excludePathPatterns("/login", "/sendEmail", "/register", "/retrievePassword", "/department/list", "job/list")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/images/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + myConfiguration.getUploadRoot());
    }

}
