package com.dullfan.common.entity.dto;

import com.dullfan.common.entity.po.SysNotice;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SysNoticeDto {
    /**
     * 通知内容
     */
    @Valid
    private SysNotice sysNotice;
    /**
     * 接收的用户
     */
    private List<Long> users;
}
