package com.tcompany.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private int categoryId;
    @Column(name = "categoryName")
    private String categoryName;
    @Column(name = "categoryDescription")
    private String categoryDescription;

    @Column(name = "categoryEnabled")
    private boolean categoryEnabled;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

}
