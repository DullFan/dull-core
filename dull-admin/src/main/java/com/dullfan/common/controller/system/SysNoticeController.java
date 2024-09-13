package com.dullfan.common.controller.system;

import com.dullfan.common.controller.ABaseController;
import com.dullfan.common.entity.dto.SysNoticeUpdateDto;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.entity.dto.SysNoticeDto;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.netty.websocket.ChatHandler;
import com.dullfan.system.service.SysNoticeService;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告管理
 */
@RestController("sysNoticeController")
@RequestMapping("/notice")
public class SysNoticeController extends ABaseController {

    @Resource
    private SysNoticeService sysNoticeService;

    @Resource
    private ChatHandler chatHandler;

    @GetMapping("/selectByUserId")
    public Result selectByUserId(@RequestParam Long userId){
        if(!SecurityUtils.isAdmin(userId)){
            userId = SecurityUtils.getUserId();
        }
        return success(sysNoticeService.selectByUserId(userId));
    }

    @GetMapping("/selectByCreateUserId")
    public Result selectByCreateUserId(@RequestParam Long userId){
        if(!SecurityUtils.isAdmin(userId)){
            userId = SecurityUtils.getUserId();
        }
        return success(sysNoticeService.selectByCreateId(userId));
    }

    @GetMapping("/selectByNoticeId")
    public Result selectByNoticeId(@RequestParam Long noticeId){
        return success(sysNoticeService.selectByNoticeId(noticeId));
    }

    @PutMapping("/readNotice")
    public Result readNotice(@RequestParam Long noticeId){
        return determineOperationOutcome(sysNoticeService.readNotice(noticeId));
    }

    @PostMapping("/sendNotice")
    public Result sendNotice(@Validated @RequestBody SysNoticeDto sysNoticeDto){
        Result result = determineOperationOutcome(sysNoticeService.add(sysNoticeDto));
        chatHandler.sendAnnouncement(sysNoticeDto);
        return result;
    }

    @DeleteMapping("/delNotice")
    public Result delNotice(@RequestParam Long noticeId){
        return determineOperationOutcome(sysNoticeService.remove(noticeId));
    }

    @DeleteMapping("/delNoticeBatchIds")
    public Result updateNotice(@RequestParam List<Long> noticeIds){
        return determineOperationOutcome(sysNoticeService.removeBatchIds(noticeIds));
    }


    @PutMapping("/updateNotice")
    public Result updateNotice(@RequestBody SysNoticeUpdateDto sysNoticeUpdateDto){
        SysNoticeDto sysNoticeDto = sysNoticeService.update(sysNoticeUpdateDto);
        System.out.println(sysNoticeDto);
        chatHandler.sendAnnouncement(sysNoticeDto);
        return success();
    }

}
