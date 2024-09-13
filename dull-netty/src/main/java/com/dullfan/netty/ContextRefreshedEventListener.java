package com.dullfan.netty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ChatServer chatServer;
    private final ConfigurableApplicationContext context;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            webSocketServerBoot();
        } catch (Exception e) {
            log.error("WebSocket启动异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
            context.close();
            System.exit(-1);
        }
    }

    private void webSocketServerBoot() throws Exception {
        chatServer.init();
        chatServer.start();
    }
}