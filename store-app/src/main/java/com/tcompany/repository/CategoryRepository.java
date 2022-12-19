package com.tcompany.repository;

import com.tcompany.model.Category;
import com.tcompany.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query("select c from Category c where trim(upper(c.categoryName)) like %?1%")
	List<Category> findByName(String name);


}