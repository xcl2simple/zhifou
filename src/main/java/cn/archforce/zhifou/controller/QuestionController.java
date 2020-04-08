package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.service.QuestionService;
import cn.archforce.zhifou.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 隔壁老李
 * @date 2020/4/7 14:38
 * 问题
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 根据问题id获取问题详情
     * @param questionId 问题id
     * @return
     */
    @GetMapping("/question/{questionId}")
    public String getQuestionDetails(@PathVariable Integer questionId){
        if (questionId == null){
            return CodeUtil.getParasIllegal();
        }
        return questionService.getQuestionDetails(questionId);
    }

    /**
     * 添加回答
     * @param userId
     * @param content
     * @return
     */
    @PostMapping("/answer/add")
    public String addAnswer(Integer userId, Integer questionId, String content){
        if(userId == null || questionId == null || content == null){
            return CodeUtil.getParasIllegal();
        }
        return questionService.addAnswer(userId, questionId, content);
    }

    /**
     * 根据参数获取对应问题的回答
     * @param questionId 问题id
     * @param sort 排序方式。1：按热度排序；2：按时间排序
     * @param startIndex 起始下标
     * @param num 获取数量
     * @return
     */
    @GetMapping("/answer/list")
    public String getAnswerList(Integer questionId, Integer sort, Integer startIndex, Integer num){
        if (questionId == null || sort == null || startIndex == null || num == null || startIndex < 0 || num < 0){
            return CodeUtil.getParasIllegal();
        }
        return questionService.getAnswerList(questionId, sort, startIndex, num);
    }

}
