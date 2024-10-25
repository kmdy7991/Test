package com.test.socket_test.controller;

import com.test.socket_test.dto.OrderRequestDto;
import com.test.socket_test.dto.OrderResponseDto;
import com.test.socket_test.service.SocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocketController {
    private final SocketService socketService;

    @MessageMapping("/test/{id}")
    @SendTo("/recv/test/{id}")
    public OrderResponseDto test(
            @Header("simpSessionId") String sessionId,
            @DestinationVariable String id,
            OrderRequestDto requestDto
    ){
        log.info("sessionId = {}, destination = {}", sessionId, id);
        return socketService.orderCreate(requestDto);
    }

    @MessageMapping("/test")
    @SendToUser("/recv/test")
    public OrderResponseDto test2(
            @DestinationVariable String id,
            OrderRequestDto requestDto
    ){
        return socketService.orderCreate(requestDto);
    }

    @GetMapping("/test")
    public ResponseEntity<OrderResponseDto> test(
    ){
        return ResponseEntity.ok(socketService.sendUser());
    }

}
