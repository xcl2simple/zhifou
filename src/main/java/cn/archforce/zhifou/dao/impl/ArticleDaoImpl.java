package cn.archforce.zhifou.dao.impl;

import cn.archforce.zhifou.dao.IArticleDao;
import cn.archforce.zhifou.mapper.ArticleMapper;
import cn.archforce.zhifou.model.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/11 14:49
 * @since JDK 1.8
 */
@Repository
public class ArticleDaoImpl implements IArticleDao {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article select(Long articleId) {
        return articleMapper.selectByPrimaryKey(articleId);
    }

    /**
     * 添加文章
     * @param article
     * @return
     */
    @Override
    public Integer add(Article article) {
        return articleMapper.insertSelective(article);
    }

}
