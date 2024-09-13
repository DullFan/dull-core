package com.dullfan.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeReadTypeEnum {
    UNREAD("0"),READ("1");
    private final String type;
}
