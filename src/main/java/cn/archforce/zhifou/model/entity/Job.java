package cn.archforce.zhifou.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 隔壁老李
 * @date 2020/4/10 11:27
 * 岗位
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job")
public class Job {

    @Id
    private Long id;

    /**
     * 岗位名称
     */
    private String jobName;

}
