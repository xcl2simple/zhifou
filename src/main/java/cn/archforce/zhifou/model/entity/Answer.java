package cn.archforce.zhifou.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 10:13
 * @since JDK 1.8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer extends BaseQuestion{

    /**
     * 所属问题的ID
     */
    private Long questionId;

    /**
     * 点赞数
     */
    private Integer likeNum;

}
