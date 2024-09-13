package com.dullfan.netty.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会话管理
 */
public class UserChannelSession {

    /**
     * 用于多端同时接受消息
     * key: userId
     * value: 多个用户的channel
     */
    private static final Map<Long, List<Channel>> multiSession = new HashMap<>();


    /**
     * 用于记录用户ID和客户端channel长id的关联关系
     */
    private static final Map<String, Long> userChannelIdRelation = new HashMap<>();

    public static void putUserChannelIdRelation(String channelId, Long userId) {
        userChannelIdRelation.put(channelId, userId);
    }

    public static Long getUserIdByChannelId(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    public static void putUselessChannels(Long userId, Channel channel) {
        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.isEmpty()) {
            channels = new ArrayList<>();
        }
        channels.add(channel);
        multiSession.put(userId, channels);
    }

    public static void removeUselessChannels(Long userId, String channelId) {
        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.isEmpty()) {
            return;
        }
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            if (channelId.equals(channel.id().asLongText())) {
                channels.remove(i);
            }
        }
        multiSession.put(userId, channels);
    }

    public static List<Channel> getMultiChannels(Long userId) {
        return multiSession.get(userId);
    }


}
