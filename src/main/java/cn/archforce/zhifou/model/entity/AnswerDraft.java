package cn.archforce.zhifou.model.entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/13 17:42
 * @since JDK 1.8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "draft_answer")
public class AnswerDraft {

    @Id
    private Long id;

    private Long questionId;

    private Long userId;

    private String content;

    private Date createTime;

}
