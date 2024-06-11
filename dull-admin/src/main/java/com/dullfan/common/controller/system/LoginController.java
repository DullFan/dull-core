package com.dullfan.common.controller.system;

import com.dullfan.common.constant.Constants;
import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.core.redis.RedisCache;
import com.dullfan.common.domain.po.LoginBody;
import com.dullfan.common.domain.po.LoginUser;
import com.dullfan.common.domain.po.User;
import com.dullfan.common.domain.vo.AjaxResult;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.framework.web.service.LoginService;
import com.dullfan.framework.web.service.TokenService;
import com.dullfan.system.service.ConfigService;
import com.dullfan.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("loginController")
public class LoginController extends ABaseController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajaxResult = AjaxResult.success();
        String token = loginService.login(loginBody);
        ajaxResult.put(Constants.TOKEN, token);
        return ajaxResult;
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        User user = SecurityUtils.getLoginUser().getUser();
        return AjaxResult.success(user);
    }

}
