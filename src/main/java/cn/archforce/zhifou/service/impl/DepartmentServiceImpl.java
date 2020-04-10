package cn.archforce.zhifou.service.impl;

import cn.archforce.zhifou.dao.DepartmentDao;
import cn.archforce.zhifou.model.entity.Department;
import cn.archforce.zhifou.model.entity.Job;
import cn.archforce.zhifou.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 隔壁老李
 * @date 2020/4/10 11:34
 * @roject zhifou
 */
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> getDepartmentList() {
        List<Department> list = departmentDao.getDepartmentList();
        return (list != null) ? list : new ArrayList<>();
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentDao.getDepartment(id);
    }

    @Override
    public List<Job> getJobList() {
        List<Job> list = departmentDao.getJobList();
        return (list != null) ? list : new ArrayList<>();
    }

    @Override
    public Job getJob(Long id) {
        return departmentDao.getJob(id);
    }
}
