package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.config.MyConfiguration;
import cn.archforce.zhifou.service.ICommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/8 14:14
 * @since JDK 1.8
 */
@Slf4j
@Service
public class CommonService implements ICommonService {

    @Autowired
    private MyConfiguration myConfiguration;

    @Override
    public String save(MultipartFile file) throws IOException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String yearAndMonth = year + (month / 10 > 0 ? "" + month : "0" + month);
        String filePath = myConfiguration.getUploadRoot() + yearAndMonth + "/" + file.getOriginalFilename();
        File dest = new File(filePath);
        if (!dest.exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        log.info("upload path:{}", filePath);
        return filePath;
    }

}
