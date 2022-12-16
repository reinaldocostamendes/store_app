package com.tcompany.service.impl;

import com.tcompany.model.Product;
import com.tcompany.model.Users;
import com.tcompany.repository.ProductRepository;
import com.tcompany.repository.UserRepository;
import com.tcompany.service.ProductService;
import com.tcompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
private ProductRepository productRepository;
	@Override
	public List<Product> getAll() {
		
		return (List<Product>)  productRepository.findAll();
	}

	@Override
	public Product getProductById(int productId) {
		
		return productRepository.findById(productId).orElse(null);
	}

	@Override
	public Product addOrUpdateProduct(Product product) {
		
		return productRepository.save(product);
	}

	@Override
	public Product deleteProduct(int id) throws Exception {
		Product deletedProduct = null;
		try {
			deletedProduct = productRepository.findById(id).orElse(null);
			if(deletedProduct==null) {
				throw new Exception("User not found!");
			}else {
				productRepository.deleteById(id);
			}
		}catch(Exception e) {
			throw e;	
		}
		return deletedProduct;

	}

}
