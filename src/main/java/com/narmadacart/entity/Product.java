package com.narmadacart.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private Long id;
       private String name;
       private String description;
       private String image_url;
       private Double price;
       private Integer stock;
       @ManyToOne
       @JoinColumn(name = "category_id")
       private Category category;
       @ManyToOne
       @JoinColumn(name = "user_id")
       private User user;

       public Product() {
           System.out.println("Product created");
       }
}
