package com.dullfan.netty.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;


@Getter
public class WebSocketMsg implements Serializable {
    @Serial
    private static final long serialVersionUID = 3563194518857303976L;

    Object msg;

    Integer type;

    public WebSocketMsg setMsg(Object msg) {
        this.msg = msg;
        return this;
    }

    public WebSocketMsg setType(Integer type) {
        this.type = type;
        return this;
    }
}
