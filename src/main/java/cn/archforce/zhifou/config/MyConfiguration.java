package cn.archforce.zhifou.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/8 12:00
 * @since JDK 1.8
 */
@Data
@Component
@ConfigurationProperties(prefix = "archforce.zhifou")
public class MyConfiguration {

    public static final String PATH_UPLOAD = "/upload/";
    public static final String PATH_TEXT = "/text/";
    public static final String SUFFIX_TEXT = ".txt";

    public static String UPLOAD_PATH, TEXT_PATH;

    /**
     * 文件保存根目录
     */
    private String root;

    /**
     * 服务器地址、端口
     */
    private String uri;

    /**
     * C++服务端IP
     */
    private String rpcServerHost;

    /**
     * C++服务端端口
     */
    private Integer rpcServerPort;

    /**
     * 获取文件上传地址
     * @return
     */
    public String getUploadRoot() {
        if (UPLOAD_PATH == null) {
            UPLOAD_PATH = this.root.concat(PATH_UPLOAD).replaceAll("//", "/");
        }
        return UPLOAD_PATH;
    }

    /**
     * 获取富文本内容存放地址
     * @return
     */
    public String getTextRoot() {
        if (TEXT_PATH == null) {
            TEXT_PATH = this.root.concat(PATH_TEXT).replaceAll("//","/");
        }
        return TEXT_PATH;
    }

}
