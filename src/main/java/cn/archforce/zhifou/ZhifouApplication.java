package cn.archforce.zhifou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.archforce.zhifou.mapper")
public class ZhifouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhifouApplication.class, args);
    }

}
