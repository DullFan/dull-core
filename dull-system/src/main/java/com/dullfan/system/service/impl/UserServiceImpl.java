package com.dullfan.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dullfan.common.constant.UserConstants;
import com.dullfan.common.entity.po.User;
import com.dullfan.common.utils.DateUtils;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.common.utils.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.dullfan.system.mappers.SysUserMapper;
import com.dullfan.system.service.UserService;

/**
 * 用户信息表 业务接口实现
 *
 * @author DullFan
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private SysUserMapper userMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<User> selectListByParam(User param) {
        return this.userMapper.selectList(new QueryWrapper<>(param));
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Long selectCountByParam(User param) {
        return this.userMapper.selectCount(new QueryWrapper<>(param));
    }

    /**
     * 分页查询方法
     */
    @Override
    public Page<User> selectListByPage(Long current, Long size,User param) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(param);
        Page<User> objectPage = new Page<>();
        return userMapper.selectPage(objectPage,userQueryWrapper);
    }

    /**
     * 新增
     */
    @Override
    public Integer add(User bean) {
        return this.userMapper.insert(bean);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public User selectUserByUserId(Long userId) {
        return this.userMapper.selectById(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserByUserId(User bean, Long userId) {
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        bean.setUserId(userId);
        return this.userMapper.updateById(bean);
    }

    @Override
    public Integer updateUser(User bean) {
        return userMapper.updateById(bean);
    }

    /**
     * 根据UserId删除
     */
    @Override
    public Integer deleteUserByUserId(Long userId) {
        return this.userMapper.deleteById(userId);
    }

    /**
     * 根据ID批量删除
     */
    @Override
    public Integer deleteUserByUserIdBatch(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return this.userMapper.deleteBatchIds(list);
    }

    /**
     * 根据UserName获取对象
     */
    @Override
    public User selectUserByUserName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        return this.userMapper.selectOne(userQueryWrapper);
    }

    /**
     * 根据UserName修改
     */
    @Override
    public Integer updateUserByUserName(User bean, String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        return this.userMapper.update(bean, userQueryWrapper);
    }

    /**
     * 根据UserName删除
     */
    @Override
    public Integer deleteUserByUserName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        return this.userMapper.delete(userQueryWrapper);
    }

    @Override
    public boolean checkUserNameUnique(User user) {
        long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = this.selectUserByUserName(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId() != userId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
