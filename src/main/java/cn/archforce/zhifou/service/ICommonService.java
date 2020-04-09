package cn.archforce.zhifou.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/8 14:13
 * @since JDK 1.8
 */
public interface ICommonService {

    public String save(MultipartFile file) throws IOException;

}
