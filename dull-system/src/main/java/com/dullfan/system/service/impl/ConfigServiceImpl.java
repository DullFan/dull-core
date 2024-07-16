package com.dullfan.system.service.impl;

import java.util.Collection;
import java.util.List;

import com.dullfan.common.constant.CacheConstants;
import com.dullfan.common.core.redis.RedisCache;
import com.dullfan.common.core.text.Convert;
import com.dullfan.common.entity.query.SimplePage;
import com.dullfan.common.entity.vo.PaginationResultVo;
import com.dullfan.common.enums.PageSizeEnum;
import com.dullfan.common.utils.DateUtils;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.common.utils.StringUtils;
import com.dullfan.system.entity.po.Config;
import com.dullfan.system.entity.query.ConfigQuery;
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
// TODO 更新者、更新时间还未更新
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
        List<Config> configsList = configMapper.selectList(new ConfigQuery());
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
    public List<Config> selectListByParam(ConfigQuery param) {
        return this.configMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer selectCountByParam(ConfigQuery param) {
        return this.configMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVo<Config> selectListByPage(ConfigQuery param) {
        int count = this.selectCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSizeEnum.SIZE15.getSize() : param.getPageSize();
        SimplePage page = new SimplePage(param.getPageNum(), count, pageSize);
        param.setSimplePage(page);
        List<Config> list = this.selectListByParam(param);
        return new PaginationResultVo<>(count, page.getPageSize(), page.getPageNum(), page.getPageTotal(), list);
    }

    /**
     * 新增
     */
    @Override
    public Integer add(Config bean) {
        Integer insert = this.configMapper.insert(bean);
        if (insert > 0) {
            redisCache.setCacheObject(getCacheKey(bean.getConfigKey()), bean.getConfigValue());
        }
        return insert;
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<Config> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        Integer insert = this.configMapper.insertBatch(listBean);
        if (insert > 0) {
            for (Config bean : listBean) {
                redisCache.setCacheObject(getCacheKey(bean.getConfigKey()), bean.getConfigValue());
            }
        }
        return insert;
    }

    /**
     * 根据ConfigId获取对象
     */
    @Override
    public Config selectConfigByConfigId(Integer configId) {
        return this.configMapper.selectByConfigId(configId);
    }

    /**
     * 根据ConfigId修改
     */
    @Override
    public Integer updateConfigByConfigId(Config bean, Integer configId) {
        ConfigQuery configQuery = new ConfigQuery();
        configQuery.setConfigId(configId);
        Config config = selectConfigByConfigId(configId);
        if (!StringUtils.equals(config.getConfigKey(), bean.getConfigKey())) {
            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
        }
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        Integer row = this.configMapper.updateByParam(bean, configQuery);
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
        Integer row = this.configMapper.deleteByConfigId(configId);
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
        Integer row = this.configMapper.deleteByConfigIdBatch(list);
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

        Config config = this.configMapper.selectByConfigKey(configKey);
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
        ConfigQuery configQuery = new ConfigQuery();
        configQuery.setConfigKey(configKey);
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        Integer row = this.configMapper.updateByParam(bean, configQuery);
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
        Integer row = this.configMapper.deleteByConfigKey(configKey);
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