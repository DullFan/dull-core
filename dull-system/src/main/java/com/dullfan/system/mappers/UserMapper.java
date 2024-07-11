package com.dullfan.system.mappers;

import com.dullfan.common.entity.po.User;
import com.dullfan.common.entity.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper extends ABaseMapper<User, UserQuery> {
	/**
	 * 根据UserId查询
	 */
	User selectByUserId(@Param("userId") Long userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(@Param("userId") Long userId);

	/**
	 * 根据UserId批量删除
	 */
	Integer deleteByUserIdBatch(@Param("list") List<Integer> list);

	/**
	 * 根据UserName查询
	 */
	User selectByUserName(@Param("userName") String userName);

	/**
	 * 根据UserName删除
	 */
	Integer deleteByUserName(@Param("userName") String userName);

	/**
	 * 根据PhoneNumber查询
	 */
	User selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	/**
	 * 根据PhoneNumber删除
	 */
	Integer deleteByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	/**
	 * 根据Email查询
	 */
	User selectByEmail(@Param("email") String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteByEmail(@Param("email") String email);
}
