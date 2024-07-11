package com.dullfan.common.controller;


import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.SecurityUtils;

import java.util.Objects;

public class ABaseController {
    protected <T> Result success(T t) {
        return Result.success(t);
    }

    protected Result success() {
        return Result.success();
    }

    protected Result error(RuntimeException e) {
        return Result.error(e.getMessage());
    }

    protected Result error(String t) {
        return Result.error(t);
    }

    protected Result error() {
        return Result.error();
    }

    protected Result determineOperationOutcome(Integer result) {
        if (result > 0) {
            return success(null);
        } else {
            return error();
        }
    }

    protected Long getUserId() {
        return SecurityUtils.getLoginUser().getUserId();
    }

    protected void isAdmin() {
        if (!SecurityUtils.isAdmin(getUserId())) throw new ServiceException("该用户无法操作");
    }

    protected void isAdminOrLoginUser(Long userId) {
        if(SecurityUtils.isAdmin(getUserId())) return;
        if (!Objects.equals(getUserId(), userId))
            throw new ServiceException("该用户无法操作");
    }
}
