package com.dullfan.netty;

import com.dullfan.common.exception.ServiceException;
import com.dullfan.netty.config.WebSocketConfiguration;
import com.dullfan.netty.websocket.WSServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Netty 服务启动类
 */
@Component
@Slf4j
public class ChatServer {

    @Resource
    private final WebSocketConfiguration webSocketConfiguration;

    private ServerBootstrap server;

    public ChatServer(WebSocketConfiguration webSocketConfiguration) {
        this.webSocketConfiguration = webSocketConfiguration;
    }


    public void init() {
        EventLoopGroup bossGroup, workerGroup;
        server = new ServerBootstrap();

        int bossThreads = webSocketConfiguration.getBossThreads();
        int workerThreads = webSocketConfiguration.getWorkerThreads();
        boolean epoll = webSocketConfiguration.isEpoll();

        if (epoll) {
            bossGroup = new EpollEventLoopGroup(bossThreads,
                    new DefaultThreadFactory("WebSocketBossGroup", true));
            workerGroup = new EpollEventLoopGroup(workerThreads,
                    new DefaultThreadFactory("WebSocketWorkerGroup", true));
            server.channel(EpollServerSocketChannel.class);
        } else {
            bossGroup = new NioEventLoopGroup(bossThreads);
            workerGroup = new NioEventLoopGroup(workerThreads);
            server.channel(NioServerSocketChannel.class);
        }

        server.group(bossGroup, workerGroup)
                .childHandler(new WSServerInitializer())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void start() throws Exception {
        log.info("WebSocketServer - Starting...");
        ChannelFuture channelFuture = server.bind(webSocketConfiguration.getPort()).sync();
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.info("WebSocketServer - Start completed.");
            } else {
                throw new ServiceException("WebSocket启动失败！");
            }
        });
    }
}
