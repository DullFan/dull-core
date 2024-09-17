package com.dullfan.system.service.impl;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dullfan.common.constant.UserConstants;
import com.dullfan.common.entity.po.SysUser;
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
    public List<SysUser> selectListByParam(SysUser param) {
        return this.userMapper.selectList(new QueryWrapper<>(param));
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Long selectCountByParam(SysUser param) {
        return this.userMapper.selectCount(new QueryWrapper<>(param));
    }

    /**
     * 分页查询方法
     */
    @Override
    public Page<SysUser> selectListByPage(Long current, Long size, SysUser param) {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>(param);
        Page<SysUser> objectPage = new Page<>();
        return userMapper.selectPage(objectPage,userQueryWrapper);
    }

    /**
     * 新增
     */
    @Override
    public Integer add(SysUser bean) {
        return this.userMapper.insert(bean);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public SysUser selectUserByUserId(Long userId) {
        return this.userMapper.selectById(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserByUserId(SysUser bean, Long userId) {
        bean.setUpdateBy(SecurityUtils.getUserId().toString());
        bean.setUpdateTime(DateUtils.getNowDate());
        bean.setUserId(userId);
        return this.userMapper.updateById(bean);
    }

    @Override
    public Integer updateUser(SysUser bean) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",bean);
        System.out.println(jsonObject.get("data"));
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
    public SysUser selectUserByUserName(String userName) {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        return this.userMapper.selectOne(userQueryWrapper);
    }

    /**
     * 根据UserName修改
     */
    @Override
    public Integer updateUserByUserName(SysUser bean, String userName) {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
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
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        return this.userMapper.delete(userQueryWrapper);
    }

    @Override
    public boolean checkUserNameUnique(SysUser user) {
        long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.selectUserByUserName(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId() != userId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
