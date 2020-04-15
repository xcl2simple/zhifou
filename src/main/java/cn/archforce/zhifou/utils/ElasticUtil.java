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
     * @param sort
     * @param startIndex
     * @param num
     * @param keyword
     * @return
     */
    public static String getSearchDsl(Integer sort, Integer startIndex, Integer num, String keyword) {
        String orderByItem = null;
        if (sort == null || sort.equals(1)){
            orderByItem = "viewedNum";
        } else {
            orderByItem = "createTime";
        }
        startIndex = startIndex == null || startIndex < 1 ? 1 : startIndex;
        num = num == null || num < 0 ? 10 : num;

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
        searchSourceBuilder.from((startIndex - 1) * num);
        // size
        searchSourceBuilder.size(num);
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
