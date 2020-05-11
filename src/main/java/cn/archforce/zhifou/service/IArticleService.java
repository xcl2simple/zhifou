package cn.archforce.zhifou.service;

import cn.archforce.zhifou.model.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/11 14:54
 * @since JDK 1.8
 */
public interface IArticleService {

    Map<String, Object> searchArticle(Integer sort, Integer pageNum, Integer pageSize, String searchTitle);

    List<Article> suggestArticle(String title);
}
