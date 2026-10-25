
package com.soa.order.service;

import com.soa.order.entity.Order;
import com.soa.order.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    private final RestClient productClient;
    private final RestClient transparencyClient;
    private final RestClient paymentClient;
    private final RestClient deliveryClient;

    public OrderService(OrderRepository repository) {
        this.repository = repository;

        this.productClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();

        this.transparencyClient = RestClient.builder()
                .baseUrl("http://localhost:8084")
                .build();

        this.paymentClient = RestClient.builder()
                .baseUrl("http://localhost:8083")
                .build();

        this.deliveryClient = RestClient.builder()
                .baseUrl("http://localhost:8085")
                .build();
    }

    public CheckoutResponse createOrder(CheckoutRequest request) {

        // 1. Create and save the order
        Order order = new Order();

        order.setCustomerId(request.customerId);
        order.setProductId(request.productId);
        order.setQuantity(request.quantity);

        // 2. Get product details from Product Service
        ProductResponse product = productClient.get()
                .uri("/products/" + order.getProductId())
                .retrieve()
                .body(ProductResponse.class);

        if (product == null) {
            throw new RuntimeException(
                    "Product not found");
        }

        // 3. Calculate product amount
        double amount = product.price * order.getQuantity();

        order.setProductAmount(amount);
        order.setStatus("CREATED");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = repository.save(order);

        // 4. Calculate transparent price breakdown
        TransparencyResponse transparency =
                transparencyClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transparency/{orderId}")
                        .queryParam("productAmount", amount)
                        .build(savedOrder.getId()))
                .retrieve()
                .body(TransparencyResponse.class);

        if (transparency == null) {
            throw new RuntimeException(
                    "Transparency calculation failed");
        }

        // 5. Create payment using the complete total
        PaymentRequest paymentRequest = new PaymentRequest();

        paymentRequest.orderId = savedOrder.getId();
        paymentRequest.amount = transparency.totalAmount;
        paymentRequest.paymentMethod = request.paymentMethod;

        PaymentResponse payment = paymentClient.post()
                .uri("/payments")
                .body(paymentRequest)
                .retrieve()
                .body(PaymentResponse.class);

        if (payment == null) {
            throw new RuntimeException(
                    "Payment creation failed");
        }

        // 6. Create delivery after payment response
        DeliveryRequest deliveryRequest = new DeliveryRequest();

        deliveryRequest.orderId = savedOrder.getId();
        deliveryRequest.deliveryAddress =
                request.deliveryAddress;
        deliveryRequest.deliveryPartner = "Not Assigned";

        DeliveryResponse delivery = deliveryClient.post()
                .uri("/deliveries")
                .body(deliveryRequest)
                .retrieve()
                .body(DeliveryResponse.class);

        if (delivery == null) {
            throw new RuntimeException(
                    "Delivery creation failed");
        }

        // 7. Return all details together
        CheckoutResponse response = new CheckoutResponse();

        response.order = savedOrder;
        response.transparency = transparency;
        response.payment = payment;
        response.delivery = delivery;

        return response;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Request from Postman
    public static class CheckoutRequest {
        public Long customerId;
        public Long productId;
        public int quantity;
        public String paymentMethod;
        public String deliveryAddress;
    }

    // Product Service response
    public static class ProductResponse {
        public Long id;
        public String name;
        public double price;
    }

    // Transparency Service response
    public static class TransparencyResponse {
        public Long orderId;
        public double productAmount;
        public double farmerAmount;
        public double packagingCharge;
        public double deliveryCharge;
        public double platformFee;
        public double totalAmount;
        public double farmerPercentage;
    }

    // Payment request
    public static class PaymentRequest {
        public Long orderId;
        public double amount;
        public String paymentMethod;
    }

    // Payment response
    public static class PaymentResponse {
        public Long id;
        public Long orderId;
        public double amount;
        public String paymentMethod;
        public String paymentStatus;
        public LocalDateTime paymentDate;
    }

    // Delivery request
    public static class DeliveryRequest {
        public Long orderId;
        public String deliveryAddress;
        public String deliveryPartner;
    }

    // Delivery response
    public static class DeliveryResponse {
        public Long id;
        public Long orderId;
        public String deliveryAddress;
        public String deliveryPartner;
        public String deliveryStatus;
        public LocalDateTime estimatedDeliveryDate;
    }

    // Complete checkout response
    public static class CheckoutResponse {
        public Order order;
        public TransparencyResponse transparency;
        public PaymentResponse payment;
        public DeliveryResponse delivery;
    }
}