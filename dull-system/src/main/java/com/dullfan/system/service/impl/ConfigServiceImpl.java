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
import com.dullfan.system.entity.po.SysConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.dullfan.system.mappers.SysConfigMapper;
import com.dullfan.system.service.ConfigService;

/**
 * 参数配置表 业务接口实现
 *
 * @author DullFan
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {
    private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);
    @Resource
    private SysConfigMapper configMapper;

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
        List<SysConfig> configsList = configMapper.selectList(null);
        for (SysConfig config : configsList) {
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
    public List<SysConfig> selectListByParam(SysConfig param) {
        QueryWrapper<SysConfig> configQueryWrapper = new QueryWrapper<>(param);
        return this.configMapper.selectList(configQueryWrapper);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Long selectCountByParam(SysConfig param) {
        QueryWrapper<SysConfig> configQueryWrapper = new QueryWrapper<>(param);
        return this.configMapper.selectCount(configQueryWrapper);
    }

    /**
     * 分页查询方法
     */
    @Override
    public Page<SysConfig> selectListByPage(Long current, Long size, SysConfig param) {
        Page<SysConfig> page = new Page<>(current, size);
        QueryWrapper<SysConfig> configQueryWrapper = new QueryWrapper<>(param);
        return configMapper.selectPage(page, configQueryWrapper);
    }

    /**
     * 新增
     */
    @Override
    public Integer add(SysConfig bean) {
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
    public SysConfig selectConfigByConfigId(Integer configId) {
        return this.configMapper.selectById(configId);
    }

    /**
     * 根据ConfigId修改
     */
    @Override
    public Integer updateConfigByConfigId(SysConfig bean, Integer configId) {
        SysConfig configQuery = new SysConfig();
        configQuery.setConfigId(configId);
        SysConfig config = selectConfigByConfigId(configId);
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
            SysConfig config = selectConfigByConfigId(configId);
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
                SysConfig config = selectConfigByConfigId(configId);
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
        QueryWrapper<SysConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("config_key",configKey);
        SysConfig config = this.configMapper.selectOne(configQueryWrapper);
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
    public Integer updateConfigByConfigKey(SysConfig bean, String configKey) {
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        QueryWrapper<SysConfig> configQueryWrapper = new QueryWrapper<>();
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
        QueryWrapper<SysConfig> configQueryWrapper = new QueryWrapper<>();
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