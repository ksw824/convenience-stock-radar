package org.example.convradar.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/reserve")
    public ResponseEntity<Long> reserve(@RequestParam Long id, @RequestParam int quantity) {
        Long stockId = orderService.reserveItem(id, quantity);
        return ResponseEntity.ok(stockId);
    }
}
