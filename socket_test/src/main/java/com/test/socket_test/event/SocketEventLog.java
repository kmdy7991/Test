package com.test.socket_test.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class SocketEventLog {
    @EventListener
    public void onSocketEvent(SessionConnectEvent event) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());

        log.info("IN sessionId = {} | time = {}", header.getSessionId(), getTime());
    }

    @EventListener
    public void offSocketEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());

        log.info("OUT sessionId = {} | time = {}", header.getSessionId(), getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("yy.MM.dd HH:mm:ss").format(new Date());
    }
}
