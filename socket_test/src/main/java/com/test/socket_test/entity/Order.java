package com.test.socket_test.entity;

import com.test.socket_test.emuns.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Order(Status status, String message) {
        this.status = status;
        this.message = message;
    }
}