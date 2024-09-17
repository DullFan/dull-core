package com.dullfan.netty.websocket;

import com.dullfan.common.constant.CacheConstants;
import com.dullfan.common.core.redis.RedisCache;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.StringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 限流
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketRateLimitHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private RedisCache redisCache;

    @Value(value = "${redis.config.max}")
    private int maxCount;

    @Value(value = "${redis.config.expireTime}")
    private int lockTime;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = inetSocketAddress.getAddress().getHostAddress();
        // Generates key
        String key = getCacheKey(ip);
        Integer count = redisCache.getCacheObject(key);
        if(count == null){
            count = 0;
        }
        if(count >= maxCount){
            ctx.channel().close();
            throw new ServiceException(StringUtils.format("连接次数已经达到上限,请{}分钟再试", lockTime));
        }
        count += 1;
        log.error(count.toString());
        redisCache.setCacheObject(key,count,lockTime,TimeUnit.MINUTES);
        ctx.fireChannelRead(msg);
    }


    /**
     * Redis缓存Key
     *
     * @param ip IP
     * @return 缓存键key
     */
    private String getCacheKey(String ip) {
        return CacheConstants.RATE_LIMIT_KEY + ip;
    }
}
