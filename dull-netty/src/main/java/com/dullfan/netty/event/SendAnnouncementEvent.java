package com.dullfan.netty.event;

import com.dullfan.common.entity.dto.SysNoticeDto;
import com.dullfan.common.entity.po.SysNotice;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * 发送公告事件
 */
@ToString
@Getter
@Setter
public class SendAnnouncementEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = 4147478875596727429L;

    private SysNoticeDto sysNoticeDto;

    public SendAnnouncementEvent(Object source, SysNoticeDto sysNoticeDto) {
        super(source);
        this.sysNoticeDto = sysNoticeDto;
    }
}
