package com.dullfan.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 通知公告表
 */
@Data
public class SysNotice implements Serializable {
    @Serial
    private static final long serialVersionUID = 5964672197431566536L;
    /**
    * 公告ID
    */
    @TableId(value = "notice_id",type = IdType.AUTO)
    private Long noticeId;

    /**
    * 公告标题
    */
    @NotBlank(message = "公告标题不能为空")
    private String noticeTitle;

    /**
    * 公告类型（1 通知 2 公告）
    */
    @NotBlank(message = "公告类型不能为空")
    @Pattern(regexp = "[12]", message = "公告类型只能是 1(通知) 2(公告)")
    private String noticeType;

    /**
    * 公告内容
    */
    @NotBlank(message = "公告内容不能为空")
    private String noticeContent;

    /**
    * 公告状态（0正常 1关闭）
    */
    private String status;

    /**
    * 创建者
    */
    private String createBy;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
    * 更新者
    */
    private String updateBy;

    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
    * 备注
    */
    private String remark;

}