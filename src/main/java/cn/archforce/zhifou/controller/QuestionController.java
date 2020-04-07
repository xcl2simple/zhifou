package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.model.entity.Question;
import cn.archforce.zhifou.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @GetMapping("")
    public JsonResult getQuestionByIndex(Integer sort, Integer startIndex, Integer num) {
        List<Question> questions = questionService.selectQuestionByIndex(sort, startIndex);
        if (CollectionUtils.isEmpty(questions)) {
            return JsonResult.success(new ArrayList<Question>());
        }
        return JsonResult.success(questions);
    }

    @PostMapping("/add")
    public JsonResult addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return JsonResult.success();
    }

}
