package cn.archforce.zhifou.utils;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/14 12:11
 * @since JDK 1.8
 */
public class ElasticUtil {

    /**
     * 根据参数生成查询语句
     *
     * @param orderByItem
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    public static String getSearchDsl(String orderByItem, Integer pageNum, Integer pageSize, String keyword) {

        // jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // filter
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("status",1);
        boolQueryBuilder.filter(termQueryBuilder);

        // must
        if (StringUtils.isNotBlank(keyword)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        // query
        searchSourceBuilder.query(boolQueryBuilder);
        // from
        searchSourceBuilder.from((pageNum - 1) * pageSize);
        // size
        searchSourceBuilder.size(pageSize);
        // highlight：高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("title");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlight(highlightBuilder);
        //sort
        searchSourceBuilder.sort(orderByItem, SortOrder.DESC);

        String dslStr = searchSourceBuilder.toString();

        return dslStr;
    }
}
