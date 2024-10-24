package com.test.socket_test.repository;

import com.test.socket_test.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketRepository extends JpaRepository<Order, Long> {
}
