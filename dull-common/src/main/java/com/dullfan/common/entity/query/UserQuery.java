package com.dullfan.common.entity.query;


/**
 * 用户信息表
 * 
 * @author DullFan
 */
public class UserQuery extends BaseParam {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userName;
	private String userNameFuzzy;
	/**
	 * 用户昵称
	 */
	private String nickName;
	private String nickNameFuzzy;
	/**
	 * 用户邮箱
	 */
	private String email;
	private String emailFuzzy;
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	private String phoneNumberFuzzy;
	/**
	 * 用户性别（0男 1女 2未知）
	 */
	private String sex;
	private String sexFuzzy;
	/**
	 * 头像地址
	 */
	private String avatar;
	private String avatarFuzzy;
	/**
	 * 密码
	 */
	private String password;
	private String passwordFuzzy;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 简介
	 */
	private String introduction;
	private String introductionFuzzy;
	/**
	 * 帐号状态（0正常 1停用）
	 */
	private String status;
	private String statusFuzzy;
	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	private String delFlag;
	private String delFlagFuzzy;
	/**
	 * 最后登录IP
	 */
	private String loginIp;
	private String loginIpFuzzy;
	/**
	 * 最后登录时间
	 */
	private String loginDate;
	private String loginDateStart;
	private String loginDateEnd;
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
	public void setUserId(Long userId){
		this.userId = userId;
	}
	public Long getUserId(){
		return this.userId;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getUserName(){
		return this.userName;
	}
	public void setUserNameFuzzy(String userNameFuzzy){
		this.userNameFuzzy = userNameFuzzy;
	}
	public String getUserNameFuzzy(){
		return this.userNameFuzzy;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getNickName(){
		return this.nickName;
	}
	public void setNickNameFuzzy(String nickNameFuzzy){
		this.nickNameFuzzy = nickNameFuzzy;
	}
	public String getNickNameFuzzy(){
		return this.nickNameFuzzy;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}
	public String getEmailFuzzy(){
		return this.emailFuzzy;
	}
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber(){
		return this.phoneNumber;
	}
	public void setPhoneNumberFuzzy(String phoneNumberFuzzy){
		this.phoneNumberFuzzy = phoneNumberFuzzy;
	}
	public String getPhoneNumberFuzzy(){
		return this.phoneNumberFuzzy;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getSex(){
		return this.sex;
	}
	public void setSexFuzzy(String sexFuzzy){
		this.sexFuzzy = sexFuzzy;
	}
	public String getSexFuzzy(){
		return this.sexFuzzy;
	}
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	public String getAvatar(){
		return this.avatar;
	}
	public void setAvatarFuzzy(String avatarFuzzy){
		this.avatarFuzzy = avatarFuzzy;
	}
	public String getAvatarFuzzy(){
		return this.avatarFuzzy;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPasswordFuzzy(String passwordFuzzy){
		this.passwordFuzzy = passwordFuzzy;
	}
	public String getPasswordFuzzy(){
		return this.passwordFuzzy;
	}
	public void setAge(Integer age){
		this.age = age;
	}
	public Integer getAge(){
		return this.age;
	}
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}
	public String getIntroduction(){
		return this.introduction;
	}
	public void setIntroductionFuzzy(String introductionFuzzy){
		this.introductionFuzzy = introductionFuzzy;
	}
	public String getIntroductionFuzzy(){
		return this.introductionFuzzy;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatusFuzzy(String statusFuzzy){
		this.statusFuzzy = statusFuzzy;
	}
	public String getStatusFuzzy(){
		return this.statusFuzzy;
	}
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	public String getDelFlag(){
		return this.delFlag;
	}
	public void setDelFlagFuzzy(String delFlagFuzzy){
		this.delFlagFuzzy = delFlagFuzzy;
	}
	public String getDelFlagFuzzy(){
		return this.delFlagFuzzy;
	}
	public void setLoginIp(String loginIp){
		this.loginIp = loginIp;
	}
	public String getLoginIp(){
		return this.loginIp;
	}
	public void setLoginIpFuzzy(String loginIpFuzzy){
		this.loginIpFuzzy = loginIpFuzzy;
	}
	public String getLoginIpFuzzy(){
		return this.loginIpFuzzy;
	}
	public void setLoginDate(String loginDate){
		this.loginDate = loginDate;
	}
	public String getLoginDate(){
		return this.loginDate;
	}
	public void setLoginDateStart(String loginDateStart){
		this.loginDateStart = loginDateStart;
	}
	public String getLoginDateStart(){
		return this.loginDateStart;
	}
	public void setLoginDateEnd(String loginDateEnd){
		this.loginDateEnd = loginDateEnd;
	}
	public String getLoginDateEnd(){
		return this.loginDateEnd;
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
