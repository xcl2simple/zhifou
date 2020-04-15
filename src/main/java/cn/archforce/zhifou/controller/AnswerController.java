package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.model.entity.Answer;
import cn.archforce.zhifou.service.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 隔壁老李
 * @date 2020/4/8 18:53
 * 问题回答
 */
@RestController
@RequestMapping("/answer")
@Api(description = "回答相关操作接口")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * 添加回答
     * @param answer
     * @return
     */
    @ApiOperation(value = "发布答案", notes = "参数userId、content、questionId对应用户ID、内容、问题ID")
    @PostMapping("/add")
    public JsonResult addAnswer(HttpServletRequest request, @RequestBody Answer answer){
        if(answer == null){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        if (answerService.addAnswer(request, answer)){
            return JsonResult.success();
        }
        return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
    }

    /**
     * 根据参数获取对应问题的回答
     * @param questionId 问题id
     * @param sort 排序方式。1：按热度排序；2：按时间排序
     * @param pageNum 起始下标
     * @param pageSize 获取数量
     * @return
     */
    @ApiOperation(value = "根据sort、pageNum、pageSize参数分页获取回答列表", notes = "排序规则：1代表按热度排序，2代表按时间排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "问题id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "sort", value = "排序规则", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询的索引", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "查询的数据条数", paramType = "query", required = true, dataType = "int")
    })
    @GetMapping("/list")
    public JsonResult getAnswerList(Long questionId, Integer sort, Integer pageNum, Integer pageSize) {
        if (questionId == null || sort == null || pageNum == null || pageSize == null || pageNum < 0 || pageSize < 0) {
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        Map<String, Object> answers = answerService.getAnswerList(questionId, sort, pageNum, pageSize);
        if (CollectionUtils.isEmpty(answers)){
            return JsonResult.success(new HashMap<String, Object>());
        }
        return JsonResult.success(answers);
    }

}
