package com.dullfan.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.common.entity.po.SysNoticeUser;
import com.dullfan.system.mappers.SysNoticeUserMapper;
import com.dullfan.system.service.SysNoticeUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sysNoticeUserService")
public class SysNoticeUserServiceImpl implements SysNoticeUserService {

    @Resource
    private SysNoticeUserMapper sysNoticeUserMapper;

    @Override
    public Integer add(Long userId, Long noticeId) {
        SysNoticeUser sysNoticeUser = new SysNoticeUser();
        sysNoticeUser.setNoticeId(noticeId);
        sysNoticeUser.setUserId(userId);
        sysNoticeUser.setCreateBy(SecurityUtils.getUserId().toString());
        return sysNoticeUserMapper.insert(sysNoticeUser);
    }

    @Override
    public Integer adds(List<Long> userIds, Long noticeId) {
        List<SysNoticeUser> sysNoticeUserList = new ArrayList<>();
        for (Long userId : userIds) {
            SysNoticeUser sysNoticeUser = new SysNoticeUser();
            sysNoticeUser.setNoticeId(noticeId);
            sysNoticeUser.setUserId(userId);
            sysNoticeUser.setCreateBy(SecurityUtils.getUserId().toString());
            sysNoticeUserList.add(sysNoticeUser);
        }
        return sysNoticeUserMapper.adds(sysNoticeUserList);
    }

    @Override
    public Integer removeNoticeUser(Long noticeId) {
        QueryWrapper<SysNoticeUser> sysNoticeUserQueryWrapper = new QueryWrapper<>();
        sysNoticeUserQueryWrapper.eq("notice_id", noticeId);
        return sysNoticeUserMapper.delete(sysNoticeUserQueryWrapper);
    }

    @Override
    public Integer removeNoticeUserBatchIds(List<Long> noticeIds) {
        return sysNoticeUserMapper.deleteByNoticeIds(noticeIds);
    }

    @Override
    public List<SysNoticeUser> selectByNoticeId(Long noticeId) {
        QueryWrapper<SysNoticeUser> sysNoticeUserQueryWrapper = new QueryWrapper<>();
        sysNoticeUserQueryWrapper.eq("notice_id", noticeId);
        return sysNoticeUserMapper.selectList(sysNoticeUserQueryWrapper);
    }

    @Override
    public List<Long> selectByUserId(Long userId) {
        QueryWrapper<SysNoticeUser> sysNoticeUserQueryWrapper = new QueryWrapper<>();
        sysNoticeUserQueryWrapper.eq("user_id", userId);
        List<SysNoticeUser> sysNoticeUsers = sysNoticeUserMapper.selectList(sysNoticeUserQueryWrapper);
        return sysNoticeUsers.stream().map(SysNoticeUser::getNoticeId).toList();
    }

}
