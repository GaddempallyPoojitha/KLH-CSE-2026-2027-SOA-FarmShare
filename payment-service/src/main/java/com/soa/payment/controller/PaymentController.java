package com.soa.payment.controller;

import com.soa.payment.entity.Payment;
import com.soa.payment.service.PaymentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Payment createPayment(
            @RequestBody Payment payment) {

        return service.createPayment(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(
            @PathVariable Long id) {

        return service.getPaymentById(id);
    }

    @GetMapping("/order/{orderId}")
    public List<Payment> getPaymentsByOrderId(
            @PathVariable Long orderId) {

        return service.getPaymentsByOrderId(orderId);
    }

    @PutMapping("/{id}/status")
    public Payment updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return service.updatePaymentStatus(id, status);
    }
}