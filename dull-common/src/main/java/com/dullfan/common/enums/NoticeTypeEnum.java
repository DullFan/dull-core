package com.dullfan.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {
    NOTICE("1"),ANNOUNCEMENT("2");
    private final String type;
}
