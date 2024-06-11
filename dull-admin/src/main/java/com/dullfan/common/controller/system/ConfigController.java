package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.domain.vo.AjaxResult;
import com.dullfan.system.domain.po.Config;
import com.dullfan.system.domain.query.ConfigQuery;
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
    @GetMapping("/loadDataList")
    public AjaxResult loadDataList(@RequestParam ConfigQuery param){
        return success(configService.selectListByPage(param));
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Config bean) {
        Integer result = configService.add(bean);
        return determineOperationOutcome(result);
    }
    /**
     * 批量新增
     */
    @PostMapping("/addBatch")
    public AjaxResult addBatch(@RequestBody List<Config> listBean) {
        Integer result = configService.addBatch(listBean);
        return determineOperationOutcome(result);
    }
    /**
     * 根据ConfigId查询对象
     */
    @GetMapping("/selectConfigByConfigId")
    public AjaxResult selectConfigByConfigId(@RequestParam Integer configId) {
        return success(configService.selectConfigByConfigId(configId));
    }
    /**
     * 根据ConfigId修改对象
     */
    @PutMapping("/updateConfigByConfigId")
    public AjaxResult updateConfigByConfigId(@RequestBody Config bean,@RequestParam Integer configId) {
        Integer result = configService.updateConfigByConfigId(bean,configId);
        return determineOperationOutcome(result);
    }
    /**
     * 根据ConfigId删除
     */
    @DeleteMapping("/deleteConfigByConfigId")
    public AjaxResult deleteConfigByConfigId(@RequestParam Integer configId) {
        Integer result = configService.deleteConfigByConfigId(configId);
        return determineOperationOutcome(result);
    }
    /**
     * 根据ConfigId批量删除
     */
    @DeleteMapping("/deleteConfigByConfigIdBatch")
    public AjaxResult deleteConfigByConfigIdBatch(@RequestParam List<Integer> list) {
        Integer result = configService.deleteConfigByConfigIdBatch(list);
        return determineOperationOutcome(result);
    }
    /**
     * 根据ConfigKey查询对象
     */
    @GetMapping("/selectConfigByConfigKey")
    public AjaxResult selectConfigByConfigKey(@RequestParam String configKey) {
        return success(configService.selectConfigByConfigKey(configKey));
    }
    /**
     * 根据ConfigKey修改对象
     */
    @PutMapping("/updateConfigByConfigKey")
    public AjaxResult updateConfigByConfigKey(@RequestBody Config bean,@RequestParam String configKey) {
        Integer result = configService.updateConfigByConfigKey(bean,configKey);
        return determineOperationOutcome(result);
    }
    /**
     * 根据ConfigKey删除
     */
    @DeleteMapping("/deleteConfigByConfigKey")
    public AjaxResult deleteConfigByConfigKey(@RequestParam String configKey) {
        Integer result = configService.deleteConfigByConfigKey(configKey);
        return determineOperationOutcome(result);
    }
}
