package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.common.ResultCodeEnum;
import cn.archforce.zhifou.model.entity.Article;
import cn.archforce.zhifou.service.IArticleService;
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

    @ApiOperation(value = "根据sort、pageNum、pageSize参数分页获取文章列表", notes = "排序规则：1代表按热度排序，2代表按时间排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "排序规则", paramType = "query", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询的索引", paramType = "query", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "查询的数据条数", paramType = "query", required = false, dataType = "int")
    })
    @GetMapping("/list")
    public JsonResult getArticlesByIndex(@RequestParam(required = false) Integer sort,
                                         @RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize) {
        Map<String, Object> articles = articleService.selectArticlesByIndex(sort, pageNum, pageSize);
        if (CollectionUtils.isEmpty(articles)){
            return JsonResult.success(new HashMap<String, Object>());
        }
        return JsonResult.success(articles);
    }

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

    @ApiOperation(value = "根据文章ID获取文章详情")
    @GetMapping("")
    public JsonResult getArticleDetails(@RequestParam(value = "articleId") Long articleId){
        if (articleId == null){
            return JsonResult.failure(ResultCodeEnum.PARAM_IS_INVALID);
        }
        Article article = articleService.selectArticleById(articleId);
        return (article == null) ? JsonResult.failure(ResultCodeEnum.SEVER_EXCEPTION) : JsonResult.success(article);
    }

}
