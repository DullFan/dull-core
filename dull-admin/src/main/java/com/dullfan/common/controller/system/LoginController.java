package com.dullfan.common.controller.system;

import com.dullfan.common.constant.Constants;
import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.entity.po.LoginBody;
import com.dullfan.common.entity.po.SysUser;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.framework.web.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("loginController")
public class LoginController extends ABaseController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginBody loginBody) {
        Result ajaxResult = Result.success();
        String token = loginService.login(loginBody);
        ajaxResult.put(Constants.TOKEN, token);
        return ajaxResult;
    }

    @GetMapping("/getInfo")
    public Result getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return Result.success(user);
    }

}
