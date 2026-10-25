package com.soa.transparency.service;

import com.soa.transparency.entity.Transparency;
import com.soa.transparency.repository.TransparencyRepository;
import org.springframework.stereotype.Service;

@Service
public class TransparencyService {

    private final TransparencyRepository repository;

    public TransparencyService(TransparencyRepository repository) {
        this.repository = repository;
    }

    public Transparency calculateTransparency(
            Long orderId,
            double productAmount) {

        double farmerAmount = productAmount;
        double packagingCharge = 10;
        double deliveryCharge = 20;
        double platformFee = 5;

        double totalAmount =
                farmerAmount
                + packagingCharge
                + deliveryCharge
                + platformFee;

        double farmerPercentage =
                (farmerAmount / totalAmount) * 100;

        Transparency transparency = new Transparency();

        transparency.setOrderId(orderId);
        transparency.setProductAmount(productAmount);
        transparency.setFarmerAmount(farmerAmount);
        transparency.setPackagingCharge(packagingCharge);
        transparency.setDeliveryCharge(deliveryCharge);
        transparency.setPlatformFee(platformFee);
        transparency.setTotalAmount(totalAmount);
        transparency.setFarmerPercentage(farmerPercentage);

        return repository.save(transparency);
    }

    public Transparency getTransparency(Long orderId) {

        return repository.findById(orderId).orElse(null);
    }
}