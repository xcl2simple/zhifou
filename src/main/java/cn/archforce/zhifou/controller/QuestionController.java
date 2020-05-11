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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "根据sort、pageNum、pageSize参数分页获取问题列表", notes = "排序规则：1代表按热度排序，2代表按时间排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "排序规则", paramType = "query", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询的索引", paramType = "query", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "查询的数据条数", paramType = "query", required = false, dataType = "int")
    })
    @GetMapping("/list")
    public JsonResult getQuestionByIndex(@RequestParam(required = false) Integer sort,
                                         @RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> questions = questionService.selectQuestionByIndex(sort, pageNum, pageSize);
        if (CollectionUtils.isEmpty(questions)){
            return JsonResult.success(new HashMap<String, Object>());
        }
        return JsonResult.success(questions);
    }

    @ApiOperation(value = "发布问题", notes = "参数title、content对应标题、详情")
    @PostMapping("/add")
    public JsonResult addQuestion(HttpServletRequest request, @RequestBody Question question) {
        if (question == null || question.getTitle() == null || question.getTitle().equals("")) {
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        questionService.addQuestion(request, question);
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

    @ApiOperation(value = "根据标题搜索问题,关键字高亮返回", notes = "排序、页码、数据条数参数可选")
    @GetMapping("/title/search")
    public JsonResult searchByTitleAndHighlight(@RequestParam(required = false) Integer sort,
                                                @RequestParam(required = false) Integer pageNum,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(value = "searchTitle") String searchTitle) {
        Map<String, Object> questions = questionService.searchQuestion(sort,pageNum, pageSize, searchTitle);
        return JsonResult.success(questions);
    }

    @ApiOperation(value = "根据标题推荐已有回答的相似问题")
    @GetMapping("/title/like")
    public JsonResult searchByTitle(@RequestParam(value = "title") String title) {
        List<Question> list = questionService.suggestQuestion(title);
        return JsonResult.success(list);
    }

}
