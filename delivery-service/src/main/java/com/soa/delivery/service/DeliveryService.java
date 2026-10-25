package com.soa.delivery.service;

import com.soa.delivery.entity.Delivery;
import com.soa.delivery.repository.DeliveryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery createDelivery(Delivery delivery) {

        delivery.setDeliveryStatus("PENDING");

        return repository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Delivery> getDeliveriesByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    public Delivery updateDeliveryStatus(
            Long id,
            String status) {

        Delivery delivery = repository.findById(id).orElse(null);

        if (delivery != null) {
            delivery.setDeliveryStatus(status);
            return repository.save(delivery);
        }

        return null;
    }
}