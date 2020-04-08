package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.service.IQuestionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List<Question> questions = questionService.selectQuestionByIndex(sort, startIndex, num);
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

}
