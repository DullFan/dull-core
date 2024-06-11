package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.domain.po.User;
import com.dullfan.common.domain.query.UserQuery;
import com.dullfan.common.domain.vo.AjaxResult;
import com.dullfan.common.utils.SecurityUtils;
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
    public AjaxResult loadDataList(UserQuery param) {
        return success(userService.selectListByPage(param));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody User bean) {
        Integer result = userService.add(bean);
        return determineOperationOutcome(result);
    }

    /**
     * 批量新增
     */
    @PostMapping("/addBatch")
    public AjaxResult addBatch(@RequestBody List<User> listBean) {
        Integer result = userService.addBatch(listBean);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserId查询对象
     */
    @GetMapping("/selectUserByUserId")
    public AjaxResult selectUserByUserId(Long userId) {
        return success(userService.selectUserByUserId(userId));
    }

    /**
     * 根据UserId修改对象
     */
    @PutMapping("/updateUserByUserId")
    public AjaxResult updateUserByUserId(User bean, Long userId) {

        Integer result = userService.updateUserByUserId(bean, userId);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserId删除
     */
    @DeleteMapping("/deleteUserByUserId")
    public AjaxResult deleteUserByUserId(Long userId) {
        Integer result = userService.deleteUserByUserId(userId);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserId批量删除
     */
    @DeleteMapping("/deleteUserByUserIdBatch")
    public AjaxResult deleteUserByUserIdBatch(@RequestParam List<Integer> list) {
        Integer result = userService.deleteUserByUserIdBatch(list);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserName查询对象
     */
    @GetMapping("/selectUserByUserName")
    public AjaxResult selectUserByUserName(String userName) {
        return success(userService.selectUserByUserName(userName));
    }

    /**
     * 根据UserName修改对象
     */
    @PutMapping("/updateUserByUserName")
    public AjaxResult updateUserByUserName(User bean, String userName) {
        Integer result = userService.updateUserByUserName(bean, userName);
        return determineOperationOutcome(result);
    }

    /**
     * 根据UserName删除
     */
    @DeleteMapping("/deleteUserByUserName")
    public AjaxResult deleteUserByUserName(String userName) {
        Integer result = userService.deleteUserByUserName(userName);
        return determineOperationOutcome(result);
    }

    /**
     * 根据PhoneNumber查询对象
     */
    @GetMapping("/selectUserByPhoneNumber")
    public AjaxResult selectUserByPhoneNumber(String phoneNumber) {
        return success(userService.selectUserByPhoneNumber(phoneNumber));
    }

    /**
     * 根据PhoneNumber修改对象
     */
    @PutMapping("/updateUserByPhoneNumber")
    public AjaxResult updateUserByPhoneNumber(User bean, String phoneNumber) {
        Integer result = userService.updateUserByPhoneNumber(bean, phoneNumber);
        return determineOperationOutcome(result);
    }

    /**
     * 根据PhoneNumber删除
     */
    @DeleteMapping("/deleteUserByPhoneNumber")
    public AjaxResult deleteUserByPhoneNumber(String phoneNumber) {
        Integer result = userService.deleteUserByPhoneNumber(phoneNumber);
        return determineOperationOutcome(result);
    }

    /**
     * 根据Email查询对象
     */
    @GetMapping("/selectUserByEmail")
    public AjaxResult selectUserByEmail(String email) {
        return success(userService.selectUserByEmail(email));
    }

    /**
     * 根据Email修改对象
     */
    @PutMapping("/updateUserByEmail")
    public AjaxResult updateUserByEmail(User bean, String email) {
        Integer result = userService.updateUserByEmail(bean, email);
        return determineOperationOutcome(result);
    }

    /**
     * 根据Email删除
     */
    @DeleteMapping("/deleteUserByEmail")
    public AjaxResult deleteUserByEmail(String email) {
        Integer result = userService.deleteUserByEmail(email);
        return determineOperationOutcome(result);
    }
}
