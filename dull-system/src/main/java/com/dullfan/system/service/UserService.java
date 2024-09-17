package com.dullfan.system.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dullfan.common.entity.po.SysUser;

import java.util.List;

public interface UserService{
	/**
	 * 根据条件查询列表
	 */
	List<SysUser> selectListByParam(SysUser param);
	/**
	 * 根据条件查询列表
	 */
	Long selectCountByParam(SysUser param);
	/**
     * 分页查询
     */
	Page<SysUser> selectListByPage(Long current, Long size, SysUser param);
	/**
	 * 新增
	 */
	Integer add(SysUser bean);

	/**
	 * 根据UserId查询对象
	 */
	SysUser selectUserByUserId(Long userId);
	/**
	 * 根据UserId修改
	 */
	Integer updateUserByUserId(SysUser bean, Long userId);

	/**
	 * 根据User修改
	 */
	Integer updateUser(SysUser bean);
	/**
	 * 根据UserId删除
	 */
	Integer deleteUserByUserId(Long userId);
	/**
	 * 根据UserId批量删除
	 */
	Integer deleteUserByUserIdBatch(List<Integer> list);
	/**
	 * 根据UserName查询对象
	 */
	SysUser selectUserByUserName(String userName);
	/**
	 * 根据UserName修改
	 */
	Integer updateUserByUserName(SysUser bean, String userName);
	/**
	 * 根据UserName删除
	 */
	Integer deleteUserByUserName(String userName);

	/**
	 * 校验用户名称是否唯一
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
    boolean checkUserNameUnique(SysUser user);
}
