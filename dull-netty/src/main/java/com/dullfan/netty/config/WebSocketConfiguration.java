package com.dullfan.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty.websocket")
@Data
public class WebSocketConfiguration {

    private int port;

    private String contextPath = "/ws";

    /**
     * 0 for automatic setting（The default is CPU * 2）
     */
    private int workerThreads = 0;

    /**
     * 0 for automatic setting (The default is CPU * 2)
     */
    private int bossThreads = 1;

    /**
     * Only in Linux environments can this be set to true
     * @see <a href="https://stackoverflow.com/questions/35568365/netty-epolleventloopgroup-vs-nioeventloopgroup-which-should-i-choose-on-centos">link<a/>
     */
    private boolean epoll = false;
}

