package com.soa.delivery.repository;

import com.soa.delivery.entity.Delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository
        extends JpaRepository<Delivery, Long> {

    List<Delivery> findByOrderId(Long orderId);
}