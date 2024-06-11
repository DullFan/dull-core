package com.dullfan.system.service.impl;

import java.util.List;

import com.dullfan.common.constant.UserConstants;
import com.dullfan.common.domain.query.SimplePage;
import com.dullfan.common.domain.vo.PaginationResultVo;
import com.dullfan.common.enums.PageSizeEnum;
import com.dullfan.common.domain.po.User;
import com.dullfan.common.domain.query.UserQuery;
import com.dullfan.common.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.dullfan.system.mappers.UserMapper;
import com.dullfan.system.service.UserService;

/**
 * 用户信息表 业务接口实现
 *
 * @author DullFan
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<User> selectListByParam(UserQuery param) {
        return this.userMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer selectCountByParam(UserQuery param) {
        return this.userMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVo<User> selectListByPage(UserQuery param) {
        int count = this.selectCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSizeEnum.SIZE15.getSize() : param.getPageSize();
        SimplePage page = new SimplePage(param.getPageNum(), count, pageSize);
        param.setSimplePage(page);
        List<User> list = this.selectListByParam(param);
        PaginationResultVo<User> result = new PaginationResultVo(count, page.getPageSize(), page.getPageNum(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(User bean) {
        return this.userMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<User> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userMapper.insertBatch(listBean);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public User selectUserByUserId(Long userId) {
        return this.userMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserByUserId(User bean, Long userId) {
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(userId);
        return this.userMapper.updateByParam(bean, userQuery);
    }

    /**
     * 根据UserId删除
     */
    @Override
    public Integer deleteUserByUserId(Long userId) {
        return this.userMapper.deleteByUserId(userId);
    }

    /**
     * 根据ID批量删除
     */
    @Override
    public Integer deleteUserByUserIdBatch(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return this.userMapper.deleteByUserIdBatch(list);
    }

    /**
     * 根据UserName获取对象
     */
    @Override
    public User selectUserByUserName(String userName) {
        return this.userMapper.selectByUserName(userName);
    }

    /**
     * 根据UserName修改
     */
    @Override
    public Integer updateUserByUserName(User bean, String userName) {
        UserQuery userQuery = new UserQuery();
        userQuery.setUserName(userName);

        return this.userMapper.updateByParam(bean, userQuery);
    }

    /**
     * 根据UserName删除
     */
    @Override
    public Integer deleteUserByUserName(String userName) {
        return this.userMapper.deleteByUserName(userName);
    }

    /**
     * 根据PhoneNumber获取对象
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return this.userMapper.selectByPhoneNumber(phoneNumber);
    }

    /**
     * 根据PhoneNumber修改
     */
    @Override
    public Integer updateUserByPhoneNumber(User bean, String phoneNumber) {
        UserQuery userQuery = new UserQuery();
        userQuery.setPhoneNumber(phoneNumber);

        return this.userMapper.updateByParam(bean, userQuery);
    }

    /**
     * 根据PhoneNumber删除
     */
    @Override
    public Integer deleteUserByPhoneNumber(String phoneNumber) {
        return this.userMapper.deleteByPhoneNumber(phoneNumber);
    }

    /**
     * 根据Email获取对象
     */
    @Override
    public User selectUserByEmail(String email) {
        return this.userMapper.selectByEmail(email);
    }

    /**
     * 根据Email修改
     */
    @Override
    public Integer updateUserByEmail(User bean, String email) {
        UserQuery userQuery = new UserQuery();
        userQuery.setEmail(email);

        return this.userMapper.updateByParam(bean, userQuery);
    }

    /**
     * 根据Email删除
     */
    @Override
    public Integer deleteUserByEmail(String email) {
        return this.userMapper.deleteByEmail(email);
    }

    @Override
    public boolean checkUserNameUnique(User user) {
        long userId = StringTools.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.selectByUserName(user.getUserName());
        if (StringTools.isNotNull(info) && info.getUserId() != userId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
