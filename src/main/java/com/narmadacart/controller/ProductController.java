package com.narmadacart.controller;

import com.narmadacart.config.JwtUtil;
import com.narmadacart.entity.Product;
import com.narmadacart.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAnyRole;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;
    private JwtUtil jwtUtil;

    public ProductController(ProductService productService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @PostMapping("/{categoryId}")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @PathVariable String categoryId, HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        Product savedProduct = productService.addProduct(product, categoryId, userId);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

}
