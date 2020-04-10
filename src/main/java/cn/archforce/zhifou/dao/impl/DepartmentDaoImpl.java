package cn.archforce.zhifou.dao.impl;

import cn.archforce.zhifou.dao.DepartmentDao;
import cn.archforce.zhifou.mapper.DepartmentMapper;
import cn.archforce.zhifou.mapper.JobMapper;
import cn.archforce.zhifou.model.entity.Department;
import cn.archforce.zhifou.model.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/10 11:43
 * @roject zhifou
 */
@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public List<Department> getDepartmentList() {
        return departmentMapper.selectAll();
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentMapper.selectOneByExample(Example.builder(Department.class)
                .where(Sqls.custom().andEqualTo("id", id))
                .build());
    }

    @Override
    public List<Job> getJobList() {
        return jobMapper.selectAll();
    }

    @Override
    public Job getJob(Long id) {
        return jobMapper.selectOneByExample(Example.builder(Job.class)
                .where(Sqls.custom().andEqualTo("id", id))
                .build());
    }
}
