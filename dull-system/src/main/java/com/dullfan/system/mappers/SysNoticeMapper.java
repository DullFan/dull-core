package com.dullfan.system.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dullfan.common.entity.po.SysNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    Integer deleteByNoticeIds(@Param("noticeList") List<Long> noticeList);
}