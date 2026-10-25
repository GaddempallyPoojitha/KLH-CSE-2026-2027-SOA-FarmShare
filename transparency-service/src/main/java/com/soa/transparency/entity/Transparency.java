package com.soa.transparency.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transparency {

    @Id
    private Long orderId;

    private double productAmount;

    private double farmerAmount;

    private double packagingCharge;

    private double deliveryCharge;

    private double platformFee;

    private double totalAmount;

    private double farmerPercentage;

    public Transparency() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public double getFarmerAmount() {
        return farmerAmount;
    }

    public void setFarmerAmount(double farmerAmount) {
        this.farmerAmount = farmerAmount;
    }

    public double getPackagingCharge() {
        return packagingCharge;
    }

    public void setPackagingCharge(double packagingCharge) {
        this.packagingCharge = packagingCharge;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getFarmerPercentage() {
        return farmerPercentage;
    }

    public void setFarmerPercentage(double farmerPercentage) {
        this.farmerPercentage = farmerPercentage;
    }
}