package com.narmadacart.service;

import com.narmadacart.entity.Category;
import com.narmadacart.entity.Product;
import com.narmadacart.entity.User;
import com.narmadacart.repository.CategoryRepository;
import com.narmadacart.repository.ProductRepository;
import com.narmadacart.repository.UserRepository;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository
                          userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    public Product addProduct(Product product, String categoryId, Long userId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        product.setCategory(category);
        product.setUser(user);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> getAllProductsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return productRepository.findByUser(user);
    }


}
