package com.tcompany.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "categoryId_fk")
    private Category category;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productDescription")
    private String productDescription;

    @Column(name = "productEnabled")
    private boolean productEnabled;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2) DEFAULT 0.00")
    private BigDecimal price;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2) DEFAULT 0.00")
    private BigDecimal cost;

    @Column(name = "quantity")
    private int quantity;

    @Lob
    @Column(name = "photo", length = 1000)
    private byte[] photo;
    @Column(name = "photoName")
    private String photoName;
    @Column(name = "photoType")
    private String photoType;
    public Product() {
    }
}
