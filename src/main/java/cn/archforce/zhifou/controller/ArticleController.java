package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.model.entity.Article;
import cn.archforce.zhifou.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/11 15:05
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/article")
@Api(description = "文章相关操作接口")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @ApiOperation(value = "发布文章", notes = "参数title、content对应标题、详情")
    @PostMapping("/add")
    public JsonResult addQuestion(HttpServletRequest request, @RequestBody Article article) {
        if (article == null || article.getTitle() == null || article.getTitle().equals("")) {
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        articleService.addArticle(request, article);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据标题搜索文章,关键字高亮返回", notes = "排序、页码、数据条数参数可选")
    @GetMapping("/title/search")
    public JsonResult searchByTitleAndHighlight(@RequestParam(required = false) Integer sort,
                                                @RequestParam(required = false) Integer pageNum,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(value = "searchTitle") String searchTitle) {
        Map<String, Object> questions = articleService.searchArticle(sort,pageNum, pageSize, searchTitle);
        return JsonResult.success(questions);
    }

    @ApiOperation(value = "根据标题推荐已有回答的相似文章")
    @GetMapping("/title/like")
    public JsonResult searchByTitle(@RequestParam(value = "title") String title) {
        List<Article> list = articleService.suggestArticle(title);
        return JsonResult.success(list);
    }

}
