package cn.archforce.zhifou.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 10:52
 * @since JDK 1.8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Article extends BaseQuestion {

    /**
     * 标题
     */
    private String title;

    /**
     * 点赞数
     */
    private Integer likeNum;

}
