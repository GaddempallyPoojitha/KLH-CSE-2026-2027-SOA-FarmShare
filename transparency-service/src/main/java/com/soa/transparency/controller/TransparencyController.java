package com.soa.transparency.controller;

import com.soa.transparency.entity.Transparency;
import com.soa.transparency.service.TransparencyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transparency")
@CrossOrigin
public class TransparencyController {

    private final TransparencyService service;

    public TransparencyController(TransparencyService service) {
        this.service = service;
    }

    @PostMapping("/{orderId}")
    public Transparency calculate(
            @PathVariable Long orderId,
            @RequestParam double productAmount) {

        return service.calculateTransparency(
                orderId,
                productAmount);
    }

    @GetMapping("/{orderId}")
    public Transparency getTransparency(
            @PathVariable Long orderId) {

        return service.getTransparency(orderId);
    }
}