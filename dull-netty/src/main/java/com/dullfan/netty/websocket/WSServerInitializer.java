package com.dullfan.netty.websocket;

import com.dullfan.common.utils.SpringUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 初始化器，channel注册后，会执行里面相应的初始化方法
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    private final AuthHandler authHandler;

    private final ChatHandler chatHandler;

    private final HeartBeatHandler heartBeatHandler;
    private final WebSocketRateLimitHandler webSocketRateLimitHandler;

    public WSServerInitializer() {
        authHandler = SpringUtils.getBean(AuthHandler.class);
        chatHandler = SpringUtils.getBean(ChatHandler.class);
        heartBeatHandler = SpringUtils.getBean(HeartBeatHandler.class);
        webSocketRateLimitHandler = SpringUtils.getBean(WebSocketRateLimitHandler.class);
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        // 通过SocketChannel获取对应的管道
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(webSocketRateLimitHandler);
        pipeline.addLast(new HttpServerCodec());
        // 添加对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合,聚合成为FullHttpRequest或FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // 权限校验
        pipeline.addLast(authHandler);
        // 10分钟未读进入读空闲(ReadIdleTime)、10分钟未写进入写空闲(WriteIdleTime)、30分钟读写空闲进入读写空闲状态(AllIdleTime)
        pipeline.addLast(new IdleStateHandler(10, 10, 30, TimeUnit.MINUTES));
        pipeline.addLast(heartBeatHandler);
        // WebSocket服务器处理的协议,用于指定给
        // 客户端连接的时候访问路由:/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 通知程序
        pipeline.addLast(chatHandler);
    }
}
