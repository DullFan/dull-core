package com.dullfan.netty.websocket;

import com.dullfan.common.enums.NoticeTypeEnum;
import com.dullfan.common.utils.JsonUtils;
import com.dullfan.netty.entity.NettyTypeEnum;
import com.dullfan.netty.entity.WebSocketMsg;
import com.dullfan.netty.event.SendAnnouncementEvent;
import com.dullfan.netty.manager.UserChannelManager;
import com.dullfan.common.entity.dto.SysNoticeDto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 处理信息
 * TextWebSocketFrame:用于WebSocket专门处理的文本数据对象,Frame是数据的载体
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private UserChannelManager userChannelManager;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        // 处理发送过来的数据
        WebSocketMsg webSocketMsg = JsonUtils.fromJson(msg.text(), WebSocketMsg.class);

        // 验证数据类型
        if (Objects.equals(webSocketMsg.getType(), NettyTypeEnum.NOTICE_TYPE.getType())) {
            SysNoticeDto sysNoticeDto = JsonUtils.fromJson(webSocketMsg.getMsg().toString(), SysNoticeDto.class);
            sendAnnouncement(sysNoticeDto);
            applicationContext.publishEvent(new SendAnnouncementEvent(this,sysNoticeDto));
        }
    }

    /**
     * 客户端离开服务端之后触发
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        userChannelManager.remove(channel);
    }

    /**
     * 发生异常并且捕获,移除channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        // 关闭连接
        channel.close();
        userChannelManager.remove(channel);
    }

    /**
     * 发送公告
     */
    public void sendAnnouncement(SysNoticeDto sysNoticeDto) {
        // 处理公告和通知
        if (sysNoticeDto.getSysNotice().getNoticeType().equals(NoticeTypeEnum.NOTICE.getType())) {
            userChannelManager.writeAndFlush(sysNoticeDto.getUsers(), sysNoticeDto.getSysNotice(), NettyTypeEnum.NOTICE_TYPE);
        } else if (sysNoticeDto.getSysNotice().getNoticeType().equals(NoticeTypeEnum.ANNOUNCEMENT.getType())) {
            userChannelManager.writeAndFlush(sysNoticeDto.getSysNotice(), NettyTypeEnum.NOTICE_TYPE);
        }
    }
}