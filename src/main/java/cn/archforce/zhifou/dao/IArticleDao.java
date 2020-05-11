package cn.archforce.zhifou.dao;

import cn.archforce.zhifou.model.entity.Article;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/11 14:49
 * @since JDK 1.8
 */
public interface IArticleDao {

    Article select(Long id);

    Integer add(Article article);
}
