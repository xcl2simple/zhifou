package cn.archforce.zhifou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 隔壁老李
 * @date 2020/3/31 14:59
 * @roject zhifou
 * 启动类
 */
@MapperScan(basePackages = "cn.archforce.zhifou.dao")
@SpringBootApplication
public class ZhifouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhifouApplication.class, args);
    }

}
