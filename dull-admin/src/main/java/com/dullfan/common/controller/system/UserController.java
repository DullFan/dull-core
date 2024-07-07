package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.domain.po.User;
import com.dullfan.common.domain.query.UserQuery;
import com.dullfan.common.domain.vo.Result;
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

    /**
     * 根据PhoneNumber查询对象
     */
    @GetMapping("/selectUserByPhoneNumber")
    public Result selectUserByPhoneNumber(String phoneNumber) {
        return success(userService.selectUserByPhoneNumber(phoneNumber));
    }

    /**
     * 根据PhoneNumber修改对象
     */
    @PutMapping("/updateUserByPhoneNumber")
    public Result updateUserByPhoneNumber(User bean, String phoneNumber) {
        User user = userService.selectUserByPhoneNumber(phoneNumber);
        isAdminOrLoginUser(user.getUserId());
        Integer result = userService.updateUserByPhoneNumber(bean, phoneNumber);
        return determineOperationOutcome(result);
    }

    /**
     * 根据PhoneNumber删除
     */
    @DeleteMapping("/deleteUserByPhoneNumber")
    public Result deleteUserByPhoneNumber(String phoneNumber) {
        User user = userService.selectUserByPhoneNumber(phoneNumber);
        isAdminOrLoginUser(user.getUserId());
        Integer result = userService.deleteUserByPhoneNumber(phoneNumber);
        return determineOperationOutcome(result);
    }

    /**
     * 根据Email查询对象
     */
    @GetMapping("/selectUserByEmail")
    public Result selectUserByEmail(String email) {
        return success(userService.selectUserByEmail(email));
    }

    /**
     * 根据Email修改对象
     */
    @PutMapping("/updateUserByEmail")
    public Result updateUserByEmail(User bean, String email) {
        User user = userService.selectUserByEmail(email);
        isAdminOrLoginUser(user.getUserId());
        Integer result = userService.updateUserByEmail(bean, email);
        return determineOperationOutcome(result);
    }

    /**
     * 根据Email删除
     */
    @DeleteMapping("/deleteUserByEmail")
    public Result deleteUserByEmail(String email) {
        User user = userService.selectUserByEmail(email);
        isAdminOrLoginUser(user.getUserId());
        Integer result = userService.deleteUserByEmail(email);
        return determineOperationOutcome(result);
    }
}
