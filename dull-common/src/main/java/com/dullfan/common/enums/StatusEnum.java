package com.dullfan.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    NORMAL("0"),CLOSE("1");
    private final String type;
}
