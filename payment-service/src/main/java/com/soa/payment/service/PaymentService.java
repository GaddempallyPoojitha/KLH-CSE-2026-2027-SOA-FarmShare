package com.soa.payment.service;

import com.soa.payment.entity.Payment;
import com.soa.payment.repository.PaymentRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment createPayment(Payment payment) {

        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    public Payment updatePaymentStatus(
            Long id,
            String status) {

        Payment payment = repository.findById(id).orElse(null);

        if (payment != null) {
            payment.setPaymentStatus(status);
            return repository.save(payment);
        }

        return null;
    }
}