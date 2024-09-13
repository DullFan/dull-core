package com.dullfan.system.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dullfan.common.entity.po.SysNoticeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysNoticeUserMapper extends BaseMapper<SysNoticeUser> {

    Integer adds(@Param("userList") List<SysNoticeUser>userList);

    Integer deleteByNoticeIds(@Param("noticeList") List<Long> noticeIds);
}