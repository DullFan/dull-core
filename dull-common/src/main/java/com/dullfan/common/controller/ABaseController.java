package com.dullfan.common.controller;


import com.dullfan.common.domain.vo.AjaxResult;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.SecurityUtils;

import java.util.Objects;

public class ABaseController {
    protected <T> AjaxResult success(T t) {
        return AjaxResult.success(t);
    }

    protected AjaxResult success() {
        return AjaxResult.success();
    }

    protected AjaxResult error(RuntimeException e) {
        return AjaxResult.error(e.getMessage());
    }

    protected AjaxResult error(String t) {
        return AjaxResult.error(t);
    }

    protected AjaxResult error() {
        return AjaxResult.error();
    }

    protected AjaxResult determineOperationOutcome(Integer result) {
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
