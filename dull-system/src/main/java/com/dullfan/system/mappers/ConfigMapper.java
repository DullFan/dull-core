package com.dullfan.system.mappers;

import com.dullfan.system.entity.po.Config;
import com.dullfan.system.entity.query.ConfigQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ConfigMapper extends ABaseMapper<Config, ConfigQuery> {
	/**
	 * 根据ConfigId查询
	 */
	Config selectByConfigId(@Param("configId") Integer configId);

	/**
	 * 根据ConfigId删除
	 */
	Integer deleteByConfigId(@Param("configId") Integer configId);

	/**
	 * 根据ConfigId批量删除
	 */
	Integer deleteByConfigIdBatch(@Param("list") List<Integer> list);

	/**
	 * 根据ConfigKey查询
	 */
	Config selectByConfigKey(@Param("configKey") String configKey);

	/**
	 * 根据ConfigKey删除
	 */
	Integer deleteByConfigKey(@Param("configKey") String configKey);
}
