package com.sb.ims.controller;

import com.sb.ims.dto.OrderDto;
import com.sb.ims.services.OrderService;
import com.sb.ims.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ReportService reportService;

    public OrderController(OrderService orderService , ReportService reportService) {
        this.orderService = orderService;
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> reciveOrder(@RequestBody OrderDto order){
        return orderService.save(order);
    }

    @GetMapping("/send")
    public void sendMail(){
        reportService.generateReport();
    }
}
