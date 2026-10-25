package com.soa.product.service;

import com.soa.product.entity.Product;
import com.soa.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product product) {

        Product existing = repository.findById(id).orElse(null);

        if (existing != null) {

            existing.setName(product.getName());
            existing.setDescription(product.getDescription());
            existing.setCategory(product.getCategory());
            existing.setPrice(product.getPrice());
            existing.setQuantity(product.getQuantity());
            existing.setFarmerId(product.getFarmerId());
            existing.setFarmLocation(product.getFarmLocation());

            return repository.save(existing);
        }

        return null;
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}