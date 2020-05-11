package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.IArticleDao;
import cn.archforce.zhifou.model.entity.Article;
import cn.archforce.zhifou.service.IArticleService;
import cn.archforce.zhifou.utils.ElasticUtil;
import cn.archforce.zhifou.utils.TokenUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/11 14:54
 * @since JDK 1.8
 */
@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private IArticleDao articleDao;

    /**
     * 发布文章
     * @param request
     * @param article
     * @return
     */
    @Override
    public boolean addArticle(HttpServletRequest request, Article article) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.getUserId(token);
        article.setUserId(userId);
        article.setCreateTime(new Date());
        article.setLikeNum(0);
        article.setStatus(1);

        if (articleDao.add(article) != 1) {
            return false;
        }
        return true;
    }

    /**
     * 根据标题搜索文章，对结果进行分页
     * @param sort
     * @param pageNum
     * @param pageSize
     * @param searchTitle
     * @return
     */
    @Override
    public Map<String, Object> searchArticle(Integer sort, Integer pageNum, Integer pageSize, String searchTitle) {
        Map<String, Object> result = new HashMap<>();
        String orderByItem = null;
        if (sort == null || sort.equals(1)){
            orderByItem = "likeNum";
        } else {
            orderByItem = "createTime";
        }
        pageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null || pageSize < 0 ? 10 : pageSize;
        searchTitle = removeChar(searchTitle);
        String dslStr =  ElasticUtil.getSearchDsl(orderByItem, pageNum, pageSize, searchTitle);
        log.info("dsl: {}", dslStr);

        // 用api执行复杂查询
        List<Article> articles = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex(Article.INDEX_NAME).addType(Article.TYPE).build();

        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<Article, Void>> hits = execute.getHits(Article.class);

        for (SearchResult.Hit<Article, Void> hit : hits) {
            Article source = hit.source;

            //获取高亮内容
            Map<String, List<String>> highlight = hit.highlight;
            if(highlight!=null){
                String title = highlight.get("title").get(0);
                source.setTitle(title);
            }
            articles.add(source);
        }
        Integer totalPage = (execute.getTotal() + pageSize - 1) / pageSize;

        log.info("TotalPage: " + totalPage + "" + articles.toString());

        result.put("total", execute.getTotal());
        result.put("totalPage", totalPage);
        result.put("list", articles);

        return result;
    }

    public List<Article> suggestArticle(String title) {
        title = removeChar(title);
        String dslStr =  ElasticUtil.getSearchDsl("likeNum", 1, 10, title);

        log.info("dsl : " + dslStr);

        // 用api执行复杂查询
        List<Article> articles = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex(Article.INDEX_NAME).addType(Article.TYPE).build();

        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<Article, Void>> hits = execute.getHits(Article.class);
        for (SearchResult.Hit<Article, Void> hit : hits) {
            Article source = hit.source;
            articles.add(source);
        }

        log.info("" + articles.toString());

        return articles;
    }

    /**
     * 处理搜索的标题，删掉"吗"、"的"、"呢"等词
     */
    private String removeChar(String searchTitle) {
        String regStr = "[吗]|[的]|[呢]|[啊]";
        Matcher mathcher = Pattern.compile(regStr).matcher(searchTitle);
        searchTitle = mathcher.replaceAll("");
        return searchTitle;
    }


}