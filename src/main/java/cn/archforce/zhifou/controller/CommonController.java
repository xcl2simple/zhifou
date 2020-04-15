package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.service.ICommonService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/8 14:11
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/common")
@Api(description = "公共接口")
public class CommonController {

    @Autowired
    private ICommonService commonService;

    @PostMapping("/upload")
    public JsonResult uploadFileToServer(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }

        String path = null;
        try {
             path = commonService.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path != null ? JsonResult.success(path) : JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
    }

}
