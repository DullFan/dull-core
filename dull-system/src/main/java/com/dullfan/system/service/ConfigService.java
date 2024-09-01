package com.dullfan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dullfan.system.entity.po.Config;

import java.util.List;

public interface ConfigService {
    /**
     * 根据条件查询列表
     */
    List<Config> selectListByParam(Config param);

    /**
     * 根据条件查询列表
     */
    Long selectCountByParam(Config param);

    /**
     * 分页查询
     */
    Page<Config> selectListByPage(Long current,Long size,Config param);

    /**
     * 新增
     */
    Integer add(Config bean);

    /**
     * 根据ConfigId查询对象
     */
    Config selectConfigByConfigId(Integer configId);

    /**
     * 根据ConfigId修改
     */
    Integer updateConfigByConfigId(Config bean, Integer configId);

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
    Integer updateConfigByConfigKey(Config bean, String configKey);

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

