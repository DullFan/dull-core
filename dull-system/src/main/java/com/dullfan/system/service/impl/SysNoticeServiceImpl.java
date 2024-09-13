package com.dullfan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dullfan.common.entity.dto.SysNoticeUpdateDto;
import com.dullfan.common.entity.po.SysNotice;
import com.dullfan.common.entity.po.SysNoticeUser;
import com.dullfan.common.enums.NoticeReadTypeEnum;
import com.dullfan.common.enums.NoticeTypeEnum;
import com.dullfan.common.enums.StatusEnum;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.common.entity.dto.SysNoticeDto;
import com.dullfan.system.mappers.SysNoticeMapper;
import com.dullfan.system.mappers.SysNoticeUserMapper;
import com.dullfan.system.service.SysNoticeService;
import com.dullfan.system.service.SysNoticeUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service("sysNoticeService")
public class SysNoticeServiceImpl implements SysNoticeService {

    @Resource
    private SysNoticeMapper sysNoticeMapper;

    @Resource
    private SysNoticeUserService sysNoticeUserService;
    @Autowired
    private SysNoticeUserMapper sysNoticeUserMapper;


    @Override
    @Transactional
    public Integer add(SysNoticeDto sysNoticeDto) {
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            throw new ServiceException("该用户无法操作");
        }
        sysNoticeDto.getSysNotice().setCreateBy(SecurityUtils.getUserId().toString());
        int insert = sysNoticeMapper.insert(sysNoticeDto.getSysNotice());
        Integer adds = 0;
        // 如果是通知则可以指定用户
        if (sysNoticeDto.getSysNotice().getNoticeType().equals(NoticeTypeEnum.NOTICE.getType())) {
            adds = sysNoticeUserService.adds(sysNoticeDto.getUsers(), sysNoticeDto.getSysNotice().getNoticeId());
        }
        // 发送通知
        if (insert > 0 || adds > 0) {
            return insert;
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer remove(Long noticeId) {
        isUser(noticeId);
        QueryWrapper<SysNotice> sysNoticeQueryWrapper = new QueryWrapper<>();
        sysNoticeQueryWrapper.eq("notice_id", noticeId);
        // 删除通知关联的用户
        sysNoticeUserService.removeNoticeUser(noticeId);
        return sysNoticeMapper.delete(sysNoticeQueryWrapper);
    }


    @Override
    @Transactional
    public Integer removeBatchIds(List<Long> noticeId) {
        isUser(noticeId);
        // 删除通知关联的用户
        sysNoticeUserService.removeNoticeUserBatchIds(noticeId);
        return sysNoticeMapper.deleteByNoticeIds(noticeId);
    }

    @Override
    public SysNotice selectById(Long noticeId) {
        return sysNoticeMapper.selectById(noticeId);
    }

    @Override
    public List<SysNotice> selectList() {
        return sysNoticeMapper.selectList(null);
    }

    @Override
    public List<SysNotice> selectByCreateId(Long userId) {
        QueryWrapper<SysNotice> sysNoticeQueryWrapper = new QueryWrapper<>();
        sysNoticeQueryWrapper.eq("create_by", userId);
        return sysNoticeMapper.selectList(sysNoticeQueryWrapper);
    }

    @Override
    public List<SysNotice> selectByUserId(Long userId) {
        QueryWrapper<SysNotice> sysNoticeQueryWrapper = new QueryWrapper<>();
        sysNoticeQueryWrapper.eq("user_id", userId);
        List<Long> noticeIds = sysNoticeUserService.selectByUserId(userId);

        QueryWrapper<SysNotice> sysNoticeWrapper = new QueryWrapper<>();
        sysNoticeWrapper.eq("notice_type", NoticeTypeEnum.ANNOUNCEMENT.getType());
        sysNoticeWrapper.eq("status", StatusEnum.NORMAL.getType());

        List<SysNotice> sysNoticesByNoticeId = List.of();
        if (!noticeIds.isEmpty()) {
            sysNoticesByNoticeId = sysNoticeMapper.selectBatchIds(noticeIds)
                    .stream()
                    .filter(sysNotice -> StatusEnum.NORMAL.getType().equals(sysNotice.getStatus()))
                    .toList();
        }
        List<SysNotice> sysNoticesByStatus = sysNoticeMapper.selectList(sysNoticeWrapper);

        List<SysNotice> combinedList = new ArrayList<>();

        combinedList.addAll(sysNoticesByNoticeId);
        combinedList.addAll(sysNoticesByStatus);

        // 按照 createTime 进行降序排序
        return combinedList.stream().sorted(Comparator.comparing(SysNotice::getCreateTime).reversed()).toList();
    }

    @Override
    public List<SysNoticeUser> selectByNoticeId(Long noticeId) {
        SysNotice sysNotice = selectById(noticeId);
        if (sysNotice != null && sysNotice.getCreateBy().equals(SecurityUtils.getUserId().toString())) {
            return sysNoticeUserService.selectByNoticeId(noticeId);
        }
        throw new ServiceException("数据不存在");

    }

    @Override
    public Integer readNotice(Long noticeId) {
        SysNoticeUser sysNoticeUser = new SysNoticeUser();
        sysNoticeUser.setIsRead(NoticeReadTypeEnum.READ.getType());
        QueryWrapper<SysNoticeUser> sysNoticeUserQueryWrapper = new QueryWrapper<>();
        sysNoticeUserQueryWrapper.eq("user_id",SecurityUtils.getUserId());
        sysNoticeUserQueryWrapper.eq("notice_id",noticeId);
        return sysNoticeUserMapper.update(sysNoticeUser,sysNoticeUserQueryWrapper);
    }

    @Override
    @Transactional
    public SysNoticeDto update(SysNoticeUpdateDto sysNoticeUpdateDto) {
        SysNotice sysNotice = isUser(sysNoticeUpdateDto.getNoticeId());
        QueryWrapper<SysNotice> sysNoticeDtoQueryWrapper = new QueryWrapper<>();
        sysNoticeDtoQueryWrapper.eq("notice_id", sysNoticeUpdateDto.getNoticeId());

        BeanUtils.copyProperties(sysNoticeUpdateDto, sysNotice);
        sysNoticeMapper.update(sysNotice, sysNoticeDtoQueryWrapper);

        if (sysNotice.getNoticeType().equals(NoticeTypeEnum.NOTICE.getType())) {
            // 设置通知用户
            sysNoticeUserService.removeNoticeUser(sysNoticeUpdateDto.getNoticeId());
            sysNoticeUserService.adds(sysNoticeUpdateDto.getUsers(), sysNoticeUpdateDto.getNoticeId());
        }

        SysNoticeDto sysNoticeDto = new SysNoticeDto();
        sysNoticeDto.setUsers(sysNoticeUpdateDto.getUsers());
        sysNoticeDto.setSysNotice(sysNoticeMapper.selectById(sysNoticeUpdateDto.getNoticeId()));
        return sysNoticeDto;
    }

    private SysNotice isUser(Long noticeId) {
        SysNotice sysNotice = selectById(noticeId);
        if (sysNotice == null) {
            throw new ServiceException("请求出错");
        }
        if (!sysNotice.getCreateBy().equals(SecurityUtils.getUserId().toString())) {
            throw new ServiceException("该用户无法操作");
        }
        return sysNotice;
    }

    private void isUser(List<Long> noticeIds) {
        for (Long noticeId : noticeIds) {
            isUser(noticeId);
        }
    }


}
