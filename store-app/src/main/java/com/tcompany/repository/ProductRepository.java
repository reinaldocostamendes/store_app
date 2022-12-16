package com.tcompany.repository;

import com.tcompany.model.Product;
import com.tcompany.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer> {
	@Query("select p from Product p where trim(upper(p.product_name)) like %?1%")
	List<Product> findByName(String name);


}