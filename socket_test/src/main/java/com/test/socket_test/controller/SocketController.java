package com.test.socket_test.controller;

import com.test.socket_test.dto.OrderRequestDto;
import com.test.socket_test.dto.OrderResponseDto;
import com.test.socket_test.service.SocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocketController {
    private final SocketService socketService;

    @MessageMapping("/test")
    @SendTo("/recv/test")
    public OrderResponseDto test(
            @Header("simpSessionId") String sessionId,
            OrderRequestDto requestDto
    ){
        log.info("sessionId = {}", sessionId);
        return socketService.orderCreate(requestDto);
    }
}
