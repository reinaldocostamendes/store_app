package com.tcompany.service;

import com.tcompany.model.Product;

import java.util.List;

public interface ProductService {
public List<Product> getAll();
public Product getProductById(int productId);
public Product addOrUpdateProduct(Product product);
public Product deleteProduct(int id) throws Exception;
}
