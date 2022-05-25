package com.szy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szy.common.R;
import com.szy.entity.Employee;
import com.szy.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @RequestMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper <Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
        if(one == null || !one.getPassword().equals(password)){
            return R.error("登录失败");
        }
        if(one.getStatus() == 0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee", one.getId());
        return R.success(one);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    //添加员工
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long id = (Long) request.getSession().getAttribute("employee");
//
//        employee.setUpdateUser(id);
//        employee.setCreateUser(id);

        employeeService.save(employee);
        return R.success("添加成功");
    }
    //员工信息的分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        //分页构造器
        Page pageInfo = new Page(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }
    //根据id修改员工信息
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
//        Long id =(Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        employeeService.updateById(employee);

        return R.success("修改成功");
    }
    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getByTd(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return  R.success(employee);
        }
        return R.error("无对应员工信息");
    }
}
