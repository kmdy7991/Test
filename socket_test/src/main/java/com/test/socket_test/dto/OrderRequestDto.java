package com.test.socket_test.dto;

import com.test.socket_test.emuns.Status;

public record OrderRequestDto(String message, Status status) {
}
