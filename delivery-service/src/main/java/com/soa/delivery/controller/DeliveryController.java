package com.soa.delivery.controller;

import com.soa.delivery.entity.Delivery;
import com.soa.delivery.service.DeliveryService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping
    public Delivery createDelivery(
            @RequestBody Delivery delivery) {

        return service.createDelivery(delivery);
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return service.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(
            @PathVariable Long id) {

        return service.getDeliveryById(id);
    }

    @GetMapping("/order/{orderId}")
    public List<Delivery> getDeliveriesByOrderId(
            @PathVariable Long orderId) {

        return service.getDeliveriesByOrderId(orderId);
    }

    @PutMapping("/{id}/status")
    public Delivery updateDeliveryStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return service.updateDeliveryStatus(id, status);
    }
}