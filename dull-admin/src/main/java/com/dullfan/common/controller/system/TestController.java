package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.framework.web.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController("testController")
@RequestMapping("/test")
public class TestController extends ABaseController {

    @Resource
    private LoginService loginService;

    @GetMapping("/getInfo")
    public Result login() {
        return success("测试文件");
    }
}