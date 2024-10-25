package com.test.socket_test.service;

import com.test.socket_test.dto.OrderRequestDto;
import com.test.socket_test.dto.OrderResponseDto;
import com.test.socket_test.entity.Order;
import com.test.socket_test.repository.SocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocketService {
    private final SocketRepository socketRepository;

    @Transactional
    public OrderResponseDto orderCreate(OrderRequestDto requestDto) {
        Order order = socketRepository.save(Order.builder()
                .status(requestDto.status())
                .message(requestDto.message())
                .build());
        return new OrderResponseDto(order.getStatus());


    }

    public OrderResponseDto sendUser() {
        return null;
    }
}
