package com.dullfan.common.entity.po;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RegisterBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 7736525174050341132L;

    String username;
    String password;
}
