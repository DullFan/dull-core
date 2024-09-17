package com.dullfan.system.service;


import com.dullfan.common.entity.po.SysNoticeUser;

import java.util.List;

public interface SysNoticeUserService {

    Integer add(Long userId, Long noticeId);

    Integer adds(List<Long> userIds, Long noticeId);

    Integer removeNoticeUser(Long noticeId);

    Integer removeNoticeUserBatchIds(List<Long> noticeIds);

    /**
     * 获取通知接收用户
     */
    List<SysNoticeUser> selectByNoticeId(Long noticeId);

    /**
     * 获取用户收到的通知
     */
    List<Long> selectByUserId(Long userId);
}
