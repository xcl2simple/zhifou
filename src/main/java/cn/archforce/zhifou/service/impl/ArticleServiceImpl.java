package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.DepartmentDao;
import cn.archforce.zhifou.dao.IArticleDao;
import cn.archforce.zhifou.dao.UserMapper;
import cn.archforce.zhifou.mapper.ArticleMapper;
import cn.archforce.zhifou.model.entity.*;
import cn.archforce.zhifou.service.IArticleService;
import cn.archforce.zhifou.utils.ElasticUtil;
import cn.archforce.zhifou.utils.TokenUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

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
    private ArticleMapper articleMapper;

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentDao departmentDao;

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
     * 文章首页推荐
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> selectArticlesByIndex(Integer sort, Integer pageNum, Integer pageSize) {
        String orderBy = (sort == null || sort.equals(1)) ? "like_num DESC" : "create_time DESC";
        Integer index = pageNum == null || pageNum < 1 ? 1 : pageNum;
        Integer number = pageSize == null || pageSize < 1 ? 10 : pageSize;
        Page page = PageHelper.startPage(index, number, orderBy);
        List<Article> articles = articleMapper.selectByExample(Example.builder(Article.class)
                .where(Sqls.custom().andEqualTo("status", 1))
                .build());

        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("totalPage", page.getPages());
        result.put("list", articles);

        return result;
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
        pageSize = pageSize == null || pageSize < 1 ? 10 : pageSize;
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
        setAuthorInfo(articles);

        Integer totalPage = (execute.getTotal() + pageSize - 1) / pageSize;
        log.info("TotalPage: " + totalPage + "" + articles.toString());

        result.put("total", execute.getTotal());
        result.put("totalPage", totalPage);
        result.put("list", articles);

        return result;
    }

    @Override
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
        setAuthorInfo(articles);
        log.info("" + articles.toString());

        return articles;
    }

    @Override
    public Article selectArticleById(Long articleId){
        Article article = articleDao.select(articleId);
        if (article != null){
            //将文章添加至列表，直接使用已有的方法，减少重复代码
            List<Article> list = new ArrayList<>();
            list.add(article);
            setAuthorInfo(list);
            log.info("" + article.toString());
        }
        return article;
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

    /**
     * 为文章实体设置发布者信息
     * @param articles
     * @return
     */
    private boolean setAuthorInfo(List<Article> articles){
        if (articles != null){
            try {
                Iterator<Article> iterator = articles.iterator();
                Article article;
                User user;
                Author author;
                Job job;
                while (iterator.hasNext()){
                    article = iterator.next();
                    user = userMapper.getUserById(article.getUserId());
                    job = departmentDao.getJob(user.getJobId());
                    author = new Author(user.getId(), user.getName(), job.getJobName(), user.getAvatar());
                    article.setAuthor(author);
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
