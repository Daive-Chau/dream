package com.demo.controller;

import com.demo.exception.BusinessException;
import com.demo.model.Income;
import com.demo.request.InfoParam;
import com.demo.service.FileService;
import com.demo.service.IncomeService;
import com.demo.service.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 简单税务系统
 */

@RestController
@Api(value = "简单税务系统", tags = "简单税务系统")
@RequestMapping(value = "/demo")
public class DemoController {

    @Resource
    private StaffService staffService;
    @Resource
    private IncomeService incomeService;
    @Resource
    private FileService fileService;

    @ApiOperation("登录接口")
    @GetMapping("/login")
    public void login(@ApiParam(value = "用户名", required = true) @RequestParam String username,
                               @ApiParam(value = "密码", required = true) @RequestParam String password) {
        if (!staffService.checkUser(username, password)) {
            throw new BusinessException("用户名或密码错误");
        }
    }

    @ApiOperation("工资excel导入接口")
    @PostMapping("/importExcel")
    public void importExcel(MultipartFile file) throws IOException {
        List<Income> incomes = fileService.readExcel(file.getInputStream());
        incomeService.batchSave(incomes);
    }

    @ApiOperation("工资结算导出接口")
    @GetMapping("/exportExcel")
    public void exportExcel(@ApiParam(value = "身份证", required = false)  String idCard,
                            @ApiParam(value = "姓名", required = false)  String realName,
                            @ApiParam(value = "职级", required = false)  String level,
                            @ApiParam(value = "开始时间", required = false)  Date beginTime,
                            @ApiParam(value = "结束时间", required = false)  Date endTime,
                            HttpServletRequest request, HttpServletResponse response) {
        InfoParam param = new InfoParam();
        param.setIdCard(idCard);
        param.setRealName(realName);
        param.setLevel(level);
        param.setBeginTime(beginTime);
        param.setEndTime(endTime);
        incomeService.exportExcel(param, request, response);
    }

    @ApiOperation("主业务查询接口")
    @PostMapping("/getInfo")
    public List<Income> getInfo(@RequestBody InfoParam param) {
        return incomeService.getIncomes(param);
    }

    @ApiOperation("模板下载")
    @GetMapping("/downTemplate")
    public void downTemplate(HttpServletResponse response){
        incomeService.downTemplate(response);
    }
}
