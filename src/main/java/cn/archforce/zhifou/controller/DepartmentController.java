package cn.archforce.zhifou.controller;

import cn.archforce.zhifou.common.JsonResult;
import cn.archforce.zhifou.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 隔壁老李
 * @date 2020/4/10 11:16
 * 部门，岗位
 */
@RestController
@Api(description = "部门，岗位相关接口")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "获取部门列表")
    @GetMapping("/department/list")
    public JsonResult getDepartmentList(){
        return JsonResult.success(departmentService.getDepartmentList());
    }

    @ApiOperation(value = "根据部门id获取部门信息")
    @GetMapping("/department/{departmentId}")
    public JsonResult getDepartment(@PathVariable Long departmentId){
        return JsonResult.success(departmentService.getDepartment(departmentId));
    }

    @ApiOperation(value = "获取岗位列表")
    @GetMapping("/job/list")
    public JsonResult getJobList(){
        return JsonResult.success(departmentService.getJobList());
    }

    @ApiOperation(value = "根据id获取岗位信息")
    @GetMapping("/job/{jobId}")
    public JsonResult getJob(@PathVariable Long jobId){
        return JsonResult.success(departmentService.getJob(jobId));
    }

}
