package com.autorent.web.service;

import com.autorent.web.entity.Order;
import com.autorent.web.repository.OrderRepository;
import com.autorent.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public void saveOrder(@AuthenticationPrincipal CurrentUser currentUser, Order order) {
        orderRepository.save(order);
    }
}
