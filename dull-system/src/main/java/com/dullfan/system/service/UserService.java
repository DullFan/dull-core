package com.dullfan.system.service;
import com.dullfan.common.entity.vo.PaginationResultVo;
import com.dullfan.common.entity.po.User;
import com.dullfan.common.entity.query.UserQuery;
import com.dullfan.system.entity.po.ResetPassword;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
public interface UserService{
	/**
	 * 根据条件查询列表
	 */
	List<User> selectListByParam(UserQuery param);
	/**
	 * 根据条件查询列表
	 */
	Integer selectCountByParam(UserQuery param);
	/**
	 * 分页查询
	 */
	PaginationResultVo<User> selectListByPage(UserQuery param);
	/**
	 * 新增
	 */
	Integer add(User bean);
	/**
	 * 批量新增
	 */
	Integer addBatch(List<User> listBean);

	/**
	 * 根据UserId查询对象
	 */
	User selectUserByUserId(Long userId);
	/**
	 * 根据UserId修改
	 */
	Integer updateUserByUserId(User bean,Long userId);

	/**
	 * 根据User修改
	 */
	Integer updateUser(User bean);
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
	User selectUserByUserName(String userName);
	/**
	 * 根据UserName修改
	 */
	Integer updateUserByUserName(User bean,String userName);
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
	public boolean checkUserNameUnique(User user);
}
