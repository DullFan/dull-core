package com.dullfan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dullfan.system.entity.po.SysConfig;

import java.util.List;

public interface ConfigService {
    /**
     * 根据条件查询列表
     */
    List<SysConfig> selectListByParam(SysConfig param);

    /**
     * 根据条件查询列表
     */
    Long selectCountByParam(SysConfig param);

    /**
     * 分页查询
     */
    Page<SysConfig> selectListByPage(Long current, Long size, SysConfig param);

    /**
     * 新增
     */
    Integer add(SysConfig bean);

    /**
     * 根据ConfigId查询对象
     */
    SysConfig selectConfigByConfigId(Integer configId);

    /**
     * 根据ConfigId修改
     */
    Integer updateConfigByConfigId(SysConfig bean, Integer configId);

    /**
     * 根据ConfigId删除
     */
    Integer deleteConfigByConfigId(Integer configId);

    /**
     * 根据ConfigId批量删除
     */
    Integer deleteConfigByConfigIdBatch(List<Integer> list);

    /**
     * 根据ConfigKey查询对象
     */
    String selectConfigByConfigKey(String configKey);

    /**
     * 根据ConfigKey修改
     */
    Integer updateConfigByConfigKey(SysConfig bean, String configKey);

    /**
     * 根据ConfigKey删除
     */
    Integer deleteConfigByConfigKey(String configKey);

    /**
     * 查询验证码是否启动
     */
    Boolean selectCaptchaEnabled();

    /**
     * 加载参数缓存数据
     */
    void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    void resetConfigCache();
}

