package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.entity.po.LoginUser;
import com.dullfan.common.entity.po.User;
import com.dullfan.common.entity.query.UserQuery;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.framework.web.service.TokenService;
import com.dullfan.system.entity.po.ResetPassword;
import com.dullfan.system.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController("userController")
@RequestMapping("/user")
public class UserController extends ABaseController {
    @Resource
    private UserService userService;

    @Resource
    TokenService tokenService;

    /**
     * 根据条件分页查询
     */
    @GetMapping("/loadDataList")
    public Result loadDataList(UserQuery param) {
        return success(userService.selectListByPage(param));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody User bean) {
        Integer result = userService.add(bean);
        return determineOperationOutcome(result);
    }

    /**
     * 批量新增
     */
    @PostMapping("/addBatch")
    public Result addBatch(@RequestBody List<User> listBean) {
        isAdmin();
        Integer result = userService.addBatch(listBean);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserId查询对象
     */
    @GetMapping("/selectUserByUserId")
    public Result selectUserByUserId(Long userId) {
        return success(userService.selectUserByUserId(userId));
    }

    /**
     * 根据UserId修改对象
     */
    @PutMapping("/updateUserByUserId")
    public Result updateUserByUserId(@RequestBody User bean, @RequestParam Long userId) {
        isAdminOrLoginUser(userId);
        Integer result = userService.updateUserByUserId(bean, userId);
        return determineOperationOutcome(result);
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody ResetPassword resetPassword) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(resetPassword.getOldPassword(), password)) {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(resetPassword.getNewPassword(), password)) {
            return error("新密码不能与旧密码相同");
        }
        String newPassword = SecurityUtils.encryptPassword(resetPassword.getNewPassword());
        User user = new User();
        user.setUserId(getUserId());
        user.setPassword(newPassword);
        if (userService.updateUser(user) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(newPassword);
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("接口报错,请联系管理员");
    }

    /**
     * 根据UserId删除
     */
    @DeleteMapping("/deleteUserByUserId")
    public Result deleteUserByUserId(Long userId) {
        isAdminOrLoginUser(userId);
        Integer result = userService.deleteUserByUserId(userId);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserId批量删除
     */
    @DeleteMapping("/deleteUserByUserIdBatch")
    public Result deleteUserByUserIdBatch(@RequestParam List<Integer> list) {
        isAdmin();
        Integer result = userService.deleteUserByUserIdBatch(list);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserName查询对象
     */
    @GetMapping("/selectUserByUserName")
    public Result selectUserByUserName(String userName) {
        return success(userService.selectUserByUserName(userName));
    }

    /**
     * 根据UserName修改对象
     */
    @PutMapping("/updateUserByUserName")
    public Result updateUserByUserName(User bean, String userName) {
        User user = userService.selectUserByUserName(userName);
        isAdminOrLoginUser(user.getUserId());
        Integer result = userService.updateUserByUserName(bean, userName);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserName删除
     */
    @DeleteMapping("/deleteUserByUserName")
    public Result deleteUserByUserName(String userName) {
        User user = userService.selectUserByUserName(userName);
        isAdminOrLoginUser(user.getUserId());
        Integer result = userService.deleteUserByUserName(userName);
        return determineOperationOutcome(result);
    }
}
