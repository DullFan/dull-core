package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.entity.po.SysConfig;
import com.dullfan.system.service.ConfigService;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController("configController")
@RequestMapping("/config")
public class ConfigController extends ABaseController {
    @Resource
    private ConfigService configService;

    /**
     * 根据条件分页查询
     */
    @GetMapping("/findDataListByPage")
    public Result loadDataList(Long current, Long size, SysConfig param) {
        return success(configService.selectListByPage(current, size, param));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysConfig bean) {
        isAdmin();
        Integer result = configService.add(bean);
        return determineOperationOutcome(result);
    }

    /**
     * 根据ConfigId查询对象
     */
    @GetMapping("/selectConfigByConfigId")
    public Result selectConfigByConfigId(@RequestParam Integer configId) {
        return success(configService.selectConfigByConfigId(configId));
    }

    /**
     * 根据ConfigId修改对象
     */
    @PutMapping("/updateConfigByConfigId")
    public Result updateConfigByConfigId(@RequestBody SysConfig bean) {
        isAdmin();
        Integer result = configService.updateConfigByConfigId(bean);
        return determineOperationOutcome(result);
    }

    /**
     * 根据ConfigId删除
     */
    @DeleteMapping("/deleteConfigByConfigId")
    public Result deleteConfigByConfigId(@RequestParam Integer configId) {
        isAdmin();
        Integer result = configService.deleteConfigByConfigId(configId);
        return determineOperationOutcome(result);
    }

    /**
     * 根据ConfigId批量删除
     */
    @DeleteMapping("/deleteConfigByConfigIdBatch")
    public Result deleteConfigByConfigIdBatch(@RequestParam List<Integer> configIds) {
        isAdmin();
        Integer result = configService.deleteConfigByConfigIdBatch(configIds);
        return determineOperationOutcome(result);
    }

    /**
     * 根据ConfigKey查询对象
     */
    @GetMapping("/selectConfigByConfigKey")
    public Result selectConfigByConfigKey(@RequestParam String configKey) {
        return success(configService.selectConfigByConfigKey(configKey));
    }

    /**
     * 根据ConfigKey修改对象
     */
    @PutMapping("/updateConfigByConfigKey")
    public Result updateConfigByConfigKey(@RequestBody SysConfig bean, @RequestParam String configKey) {
        isAdmin();
        Integer result = configService.updateConfigByConfigKey(bean, configKey);
        return determineOperationOutcome(result);
    }

    /**
     * 根据ConfigKey删除
     */
    @DeleteMapping("/deleteConfigByConfigKey")
    public Result deleteConfigByConfigKey(@RequestParam String configKey) {
        isAdmin();
        Integer result = configService.deleteConfigByConfigKey(configKey);
        return determineOperationOutcome(result);
    }
}
