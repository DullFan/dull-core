package com.dullfan.common.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class SysNoticeUpdateDto {
    /**
     * 公告ID
     */
    @TableId(value = "notice_id",type = IdType.AUTO)
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    @Pattern(regexp = "[01]", message = "公告状态只能是 0(正常) 1(关闭)")
    private String status;

    /**
     * 接收的用户
     */
    private List<Long> users;
}
