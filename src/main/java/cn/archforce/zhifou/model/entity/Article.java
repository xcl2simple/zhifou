package cn.archforce.zhifou.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 10:52
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article extends BaseQuestion {


    /**
     * 对应elasticsearch索引
     */
    public static final String INDEX_NAME = "zhifou";

    /**
     * 对应elasticsearch中type类型
     */
    public static final String TYPE = "article";

    /**
     * 标题
     */
    private String title;

    /**
     * 点赞数
     */
    private Integer likeNum;


    /**
     * 状态，1-有效/0-被删除
     */
    private Integer status;

}
