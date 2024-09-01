package com.dullfan.system.service.impl;

import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dullfan.common.constant.CacheConstants;
import com.dullfan.common.core.redis.RedisCache;
import com.dullfan.common.core.text.Convert;
import com.dullfan.common.utils.DateUtils;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.common.utils.StringUtils;
import com.dullfan.system.entity.po.Config;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.dullfan.system.mappers.ConfigMapper;
import com.dullfan.system.service.ConfigService;

/**
 * 参数配置表 业务接口实现
 *
 * @author DullFan
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private ConfigMapper configMapper;

    @Resource
    RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {

        List<Config> configsList = configMapper.selectList(null);
        for (Config config : configsList) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public List<Config> selectListByParam(Config param) {
        QueryWrapper<Config> configQueryWrapper = new QueryWrapper<>(param);
        return this.configMapper.selectList(configQueryWrapper);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Long selectCountByParam(Config param) {
        QueryWrapper<Config> configQueryWrapper = new QueryWrapper<>(param);
        return this.configMapper.selectCount(configQueryWrapper);
    }

    /**
     * 分页查询方法
     */
    @Override
    public Page<Config> selectListByPage(Long current,Long size,Config param) {
        Page<Config> page = new Page<>(current, size);
        QueryWrapper<Config> configQueryWrapper = new QueryWrapper<>(param);
        return configMapper.selectPage(page, configQueryWrapper);
    }

    /**
     * 新增
     */
    @Override
    public Integer add(Config bean) {
        int insert = this.configMapper.insert(bean);
        if (insert > 0) {
            redisCache.setCacheObject(getCacheKey(bean.getConfigKey()), bean.getConfigValue());
        }
        return insert;
    }

    /**
     * 根据ConfigId获取对象
     */
    @Override
    public Config selectConfigByConfigId(Integer configId) {
        return this.configMapper.selectById(configId);
    }

    /**
     * 根据ConfigId修改
     */
    @Override
    public Integer updateConfigByConfigId(Config bean, Integer configId) {
        Config configQuery = new Config();
        configQuery.setConfigId(configId);
        Config config = selectConfigByConfigId(configId);
        if (!StringUtils.equals(config.getConfigKey(), bean.getConfigKey())) {
            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
        }
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        bean.setConfigId(configId);

        int row = this.configMapper.updateById(bean);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigKey());
        }
        return row;
    }

    /**
     * 根据ConfigId删除
     */
    @Override
    public Integer deleteConfigByConfigId(Integer configId) {
        int row = this.configMapper.deleteById(configId);
        if (row > 0) {
            Config config = selectConfigByConfigId(configId);
            if (config != null) {
                redisCache.deleteObject(getCacheKey(config.getConfigKey()));
            }
        }
        return row;
    }

    /**
     * 根据ID批量删除
     */
    @Override
    public Integer deleteConfigByConfigIdBatch(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int row = this.configMapper.deleteBatchIds(list);
        if (row > 0) {
            for (Integer configId : list) {
                Config config = selectConfigByConfigId(configId);
                if (config != null) {
                    redisCache.deleteObject(getCacheKey(config.getConfigKey()));
                }
            }
        }
        return row;
    }

    /**
     * 根据ConfigKey获取对象
     */
    @Override
    public String selectConfigByConfigKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        QueryWrapper<Config> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("config_key",configKey);
        Config config = this.configMapper.selectOne(configQueryWrapper);
        if (config != null) {
            redisCache.setCacheObject(getCacheKey(configKey), config.getConfigValue());
            return config.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据ConfigKey修改
     */
    @Override
    public Integer updateConfigByConfigKey(Config bean, String configKey) {
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        QueryWrapper<Config> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("config_key",configKey);
        int row = this.configMapper.update(bean, configQueryWrapper);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(configKey), bean.getConfigValue());
        }
        return row;
    }

    /**
     * 根据ConfigKey删除
     */
    @Override
    public Integer deleteConfigByConfigKey(String configKey) {
        QueryWrapper<Config> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("config_key",configKey);
        int row = this.configMapper.delete(configQueryWrapper);
        if (row > 0) {
            redisCache.deleteObject(getCacheKey(configKey));
        }
        return row;
    }

    /**
     * 查询验证码是否启用哦
     * @return 结果
     */
    @Override
    public Boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByConfigKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}