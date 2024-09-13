package com.dullfan.netty.listener;

import com.dullfan.common.entity.dto.SysNoticeDto;
import com.dullfan.netty.event.SendAnnouncementEvent;
import com.dullfan.common.entity.po.SysNotice;
import com.dullfan.system.service.SysNoticeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendAnnouncementListener {

    @Resource
    private SysNoticeService sysNoticeService;

    @EventListener(classes = SendAnnouncementEvent.class)
    @Async(value = "saveAnnouncement")
    public void saveAnnouncement(SendAnnouncementEvent sendAnnouncementEvent) {
        sysNoticeService.add(sendAnnouncementEvent.getSysNoticeDto());
    }

}
