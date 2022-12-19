package com.tcompany.service.impl;

import com.tcompany.model.Category;
import com.tcompany.model.Users;
import com.tcompany.repository.CategoryRepository;
import com.tcompany.repository.UserRepository;
import com.tcompany.service.CategoryService;
import com.tcompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
private CategoryRepository categoryRepository;
	@Override
	public List<Category> getAll() {
		
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(int userId) {
		
		return categoryRepository.findById(userId).orElse(null);
	}

	@Override
	public Category addOrUpdateCategory(Category category) {
		
		return categoryRepository.save(category);
	}

	@Override
	public Category deleteCategory(int id) throws Exception {
		Category deletedCategory= null;
		try {
			deletedCategory = categoryRepository.findById(id).orElse(null);
			if(deletedCategory==null) {
				throw new Exception("Category not found!");
			}else {
				categoryRepository.deleteById(id);
			}
		}catch(Exception e) {
			throw e;	
		}
		return deletedCategory;

	}

}
