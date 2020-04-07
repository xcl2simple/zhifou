package cn.archforce.zhifou.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 12:51
 * @since JDK 1.8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例，用来控制哪些接口暴露给Swagger来展现
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.archforce.zhifou.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建API的基本信息
     *
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("知否项目RESTful APIs")
                .description("华锐ATP产品部应届生演练计划")
                .version("1.0")
                .build();
    }
}
