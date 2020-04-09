package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.service.IQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/7 14:38
 * 问题
 */
/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 11:19
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/question")
@Api(description = "问题相关操作接口")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @ApiOperation(value = "根据sort、startIndex、num参数分页获取问题列表", notes = "排序规则：0代表按热度排序，1代表按时间排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "排序规则", paramType = "query", required = false, dataType = "int"),
            @ApiImplicitParam(name = "startIndex", value = "分页查询的索引", paramType = "query", required = false, dataType = "int"),
            @ApiImplicitParam(name = "num", value = "查询的数据条数", paramType = "query", required = false, dataType = "int")
    })
    @GetMapping("")
    public JsonResult getQuestionByIndex(Integer sort, Integer startIndex, Integer num) {
        List<Question> questions = questionService.selectQuestionByIndex(sort, startIndex);
        if (CollectionUtils.isEmpty(questions)) {
            return JsonResult.success(new ArrayList<Question>());
        }
        return JsonResult.success(questions);
    }

    @ApiOperation(value = "发布问题", notes = "参数title、content、userId对应标题、详情、用户ID")
    @PostMapping("/add")
    public JsonResult addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return JsonResult.success();
    }

    /**
     * 根据问题id获取问题详情
     * @param questionId 问题id
     * @return
     */
    @ApiOperation(value = "获取问题详情", notes = "参数questionId为问题ID")
    @GetMapping("/{questionId}")
    public JsonResult getQuestionDetails(@PathVariable Long questionId){
        if (questionId == null){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        Question question = questionService.getQuestionDetails(questionId);
        if (question == null){
            return JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION);
        }
        return JsonResult.success(question);
    }

}
