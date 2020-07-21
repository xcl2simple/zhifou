package cn.archforce.zhifou.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 隔壁老李
 * @date 2020/4/10 11:26
 * 部门
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    private Long id;

    /**
     * 部门名称
     */
    @Column(name = "department_name")
    private String departmentName;

}
