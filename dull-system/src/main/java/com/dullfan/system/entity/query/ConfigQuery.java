package com.dullfan.system.entity.query;


import com.dullfan.common.entity.query.BaseParam;

/**
 * 参数配置表
 * 
 * @author DullFan
 */
public class ConfigQuery extends BaseParam {
	/**
	 * 参数主键
	 */
	private Integer configId;
	/**
	 * 参数名称
	 */
	private String configName;
	private String configNameFuzzy;
	/**
	 * 参数键名
	 */
	private String configKey;
	private String configKeyFuzzy;
	/**
	 * 参数键值
	 */
	private String configValue;
	private String configValueFuzzy;
	/**
	 * 系统内置（Y是 N否）
	 */
	private String configType;
	private String configTypeFuzzy;
	/**
	 * 创建者
	 */
	private String createBy;
	private String createByFuzzy;
	/**
	 * 创建时间
	 */
	private String createTime;
	private String createTimeStart;
	private String createTimeEnd;
	/**
	 * 更新者
	 */
	private String updateBy;
	private String updateByFuzzy;
	/**
	 * 更新时间
	 */
	private String updateTime;
	private String updateTimeStart;
	private String updateTimeEnd;
	/**
	 * 备注
	 */
	private String remark;
	private String remarkFuzzy;
	public void setConfigId(Integer configId){
		this.configId = configId;
	}
	public Integer getConfigId(){
		return this.configId;
	}
	public void setConfigName(String configName){
		this.configName = configName;
	}
	public String getConfigName(){
		return this.configName;
	}
	public void setConfigNameFuzzy(String configNameFuzzy){
		this.configNameFuzzy = configNameFuzzy;
	}
	public String getConfigNameFuzzy(){
		return this.configNameFuzzy;
	}
	public void setConfigKey(String configKey){
		this.configKey = configKey;
	}
	public String getConfigKey(){
		return this.configKey;
	}
	public void setConfigKeyFuzzy(String configKeyFuzzy){
		this.configKeyFuzzy = configKeyFuzzy;
	}
	public String getConfigKeyFuzzy(){
		return this.configKeyFuzzy;
	}
	public void setConfigValue(String configValue){
		this.configValue = configValue;
	}
	public String getConfigValue(){
		return this.configValue;
	}
	public void setConfigValueFuzzy(String configValueFuzzy){
		this.configValueFuzzy = configValueFuzzy;
	}
	public String getConfigValueFuzzy(){
		return this.configValueFuzzy;
	}
	public void setConfigType(String configType){
		this.configType = configType;
	}
	public String getConfigType(){
		return this.configType;
	}
	public void setConfigTypeFuzzy(String configTypeFuzzy){
		this.configTypeFuzzy = configTypeFuzzy;
	}
	public String getConfigTypeFuzzy(){
		return this.configTypeFuzzy;
	}
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	public String getCreateBy(){
		return this.createBy;
	}
	public void setCreateByFuzzy(String createByFuzzy){
		this.createByFuzzy = createByFuzzy;
	}
	public String getCreateByFuzzy(){
		return this.createByFuzzy;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeStart(){
		return this.createTimeStart;
	}
	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}
	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	public String getUpdateBy(){
		return this.updateBy;
	}
	public void setUpdateByFuzzy(String updateByFuzzy){
		this.updateByFuzzy = updateByFuzzy;
	}
	public String getUpdateByFuzzy(){
		return this.updateByFuzzy;
	}
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	public void setUpdateTimeStart(String updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	public String getUpdateTimeStart(){
		return this.updateTimeStart;
	}
	public void setUpdateTimeEnd(String updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}
	public String getUpdateTimeEnd(){
		return this.updateTimeEnd;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setRemarkFuzzy(String remarkFuzzy){
		this.remarkFuzzy = remarkFuzzy;
	}
	public String getRemarkFuzzy(){
		return this.remarkFuzzy;
	}
}
