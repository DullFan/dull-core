package com.dullfan.netty.manager;

import com.dullfan.common.utils.JsonUtils;
import com.dullfan.netty.entity.WebSocketMsg;
import com.dullfan.netty.entity.NettyTypeEnum;
import io.micrometer.common.lang.Nullable;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class UserChannelManager {

    private final ConcurrentHashMap<Long, Set<Channel>> userChannelMap = new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();

    /**
     * Save the mapping of uid and channel
     *
     * @param uid     uid
     * @param channel channel
     */
    public void add(@NonNull Long uid, @NonNull Channel channel) {
        lock.lock();
        Set<Channel> channels = userChannelMap.get(uid);
        if (ObjectUtils.isEmpty(channels) || channels.isEmpty()) {
            HashSet<Channel> channelSet = new HashSet<>();
            channelSet.add(channel);
            userChannelMap.put(uid, channelSet);
        } else {
            channels.add(channel);
            userChannelMap.put(uid, channels);
        }
        lock.unlock();
    }

    /**
     * Remove the element by uid
     *
     * @param uid uid
     */
    public void remove(@NonNull Long uid) {
        userChannelMap.remove(uid);
    }

    /**
     * Remove the cache by channel
     *
     * @param channel channel
     */
    public void remove(@NonNull Channel channel) {
        userChannelMap.entrySet().stream().filter(entry -> entry.getValue().contains(channel))
                .forEach(entry -> entry.getValue().remove(channel));
    }

    /**
     * Get channel by uid
     *
     * @param uid uid
     * @return channel
     */
    @Nullable
    public Set<Channel> get(@NonNull Long uid) {
        return userChannelMap.get(uid);
    }

    /**
     * Clear cache
     */
    public void clearAll() {
        userChannelMap.clear();
    }


    /**
     * Write and flush by uid
     *
     * @param uid    uid
     * @param msgObj msg object, it will be automatically converted to json.
     */
    public void writeAndFlush(@NonNull Long uid, @NonNull Object msgObj, @NonNull NettyTypeEnum typeEnum) {
        Set<Channel> channelSet = userChannelMap.get(uid);
        if (ObjectUtils.isEmpty(channelSet) || channelSet.isEmpty()) {
            return;
        }
        for (Channel channel : channelSet) {
            if (channel.isActive()) {
                WebSocketMsg webSocketMsg = new WebSocketMsg()
                        .setType(typeEnum.getType())
                        .setMsg(msgObj);
                String json = JsonUtils.toJson(webSocketMsg);
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                channelFuture.addListener((ChannelFutureListener) future -> printLog(uid,json));
            }
        }
    }

    /**
     * Write and flush by uid
     *
     * @param userIds userId
     * @param msgObj  msg object, it will be automatically converted to json.
     */
    public void writeAndFlush(@NonNull List<Long> userIds, @NonNull Object msgObj, @NonNull NettyTypeEnum typeEnum) {
        for (Long uid : userIds) {
            writeAndFlush(uid, msgObj, typeEnum);
        }
    }

    /**
     * Write and flush to every user except the sender.
     *
     * @param msgObj         msg object, it will be automatically converted to json.
     * @param typeEnum       Message type.
     * @param currentChannel The sender's Channel to skip.
     */
    public void writeAndFlush(@NonNull Object msgObj, @NonNull NettyTypeEnum typeEnum, @NonNull Channel currentChannel) {
        WebSocketMsg webSocketMsg = new WebSocketMsg()
                .setType(typeEnum.getType())
                .setMsg(msgObj);
        String json = JsonUtils.toJson(webSocketMsg);

        userChannelMap.forEach((uid, channels) -> {
            for (Channel channel : channels) {
                // 跳过发送者的通道
                if (channel.isActive() && !channel.equals(currentChannel)) {
                    TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                    ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                    channelFuture.addListener((ChannelFutureListener) future -> printLog(uid,json));
                }
            }
        });
    }

    public void printLog(Long uid,String json){
        log.debug("对uid：{}, 发送websocket消息：{}", uid, json);
    }

    /**
     * Write and flush to every user.
     *
     * @param msgObj         msg object, it will be automatically converted to json.
     * @param typeEnum       Message type.
     */
    public void writeAndFlush(@NonNull Object msgObj, @NonNull NettyTypeEnum typeEnum) {
        WebSocketMsg webSocketMsg = new WebSocketMsg()
                .setType(typeEnum.getType())
                .setMsg(msgObj);
        String json = JsonUtils.toJson(webSocketMsg);

        userChannelMap.forEach((uid, channels) -> {
            for (Channel channel : channels) {
                // 跳过发送者的通道
                if (channel.isActive()) {
                    TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                    ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                    channelFuture.addListener((ChannelFutureListener) future -> printLog(uid,json));
                }
            }
        });
    }

}
