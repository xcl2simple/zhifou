package cn.archforce.zhifou.service;

import cn.archforce.zhifou.model.entity.Department;
import cn.archforce.zhifou.model.entity.Job;

import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/10 11:32
 * @roject zhifou
 */
public interface DepartmentService {

    /**
     * 获取部门列表
     * @return
     */
    List<Department> getDepartmentList();

    /**
     * 获取部门信息
     * @param id 部门id
     * @return
     */
    Department getDepartment(Long id);

    /**
     * 获取岗位列表
     * @return
     */
    List<Job> getJobList();

    /**
     * 获取岗位信息
     * @param id 岗位id
     * @return
     */
    Job getJob(Long id);

}
