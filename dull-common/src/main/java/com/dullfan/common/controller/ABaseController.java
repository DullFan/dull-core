package com.dullfan.common.controller;


import com.dullfan.common.domain.vo.AjaxResult;
import com.dullfan.common.utils.SecurityUtils;
import nonapi.io.github.classgraph.utils.LogNode;

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
}
