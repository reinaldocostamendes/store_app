package com.tcompany.service;

import com.tcompany.model.Category;
import com.tcompany.model.Users;

import java.util.List;

public interface CategoryService {
public List<Category> getAll();
public Category getCategoryById(int categoryId);
public Category addOrUpdateCategory(Category user);
public Category deleteCategory(int id) throws Exception;
}
