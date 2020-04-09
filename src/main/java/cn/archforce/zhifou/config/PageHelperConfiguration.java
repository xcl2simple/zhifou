package cn.archforce.zhifou.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/8 18:14
 * @since JDK 1.8
 */
@Configuration
public class PageHelperConfiguration {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();

        //设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        properties.setProperty("offsetAsPageNum","true");

        //置为true时，使用RowBounds分页会进行count查询
        properties.setProperty("rowBoundsWithCount","true");

        //合理化查询,启用合理化时，
        //如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        //未开启时如果pageNum<1或pageNum>pages会返回空数据
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        
        return pageHelper;
    }


}
