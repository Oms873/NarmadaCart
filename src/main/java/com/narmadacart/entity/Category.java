package com.narmadacart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category {
    @Id
    private String id;
    private String name;
    private String description;

}
