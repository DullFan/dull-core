package com.dullfan.netty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NettyTypeEnum {
    NOTICE_TYPE(1,"通知公告");

    private final Integer type;
    private final String detail;
}
