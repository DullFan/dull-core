package com.dullfan.system.service;

import com.dullfan.common.entity.dto.SysNoticeDto;
import com.dullfan.common.entity.dto.SysNoticeUpdateDto;
import com.dullfan.common.entity.po.SysNotice;
import com.dullfan.common.entity.po.SysNoticeUser;

import java.util.List;


public interface SysNoticeService {

    Integer add(SysNoticeDto sysNoticeDto);

    Integer remove(Long noticeId);

    SysNoticeDto update(SysNoticeUpdateDto sysNoticeUpdateDto);

    Integer removeBatchIds(List<Long> noticeId);

    SysNotice selectById(Long noticeId);

    List<SysNotice> selectList();

    /**
     * 获取用户发送通知
     */
    List<SysNotice> selectByCreateId(Long userId);

    /**
     * 获取用户收到的通知
     */
    List<SysNotice> selectByUserId(Long userId);

    /**
     * 获取通知收到的用户
     */
    List<SysNoticeUser> selectByNoticeId(Long noticeId);

    Integer readNotice(Long noticeId);
}
