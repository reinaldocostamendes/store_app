package com.tcompany.controller;

import com.tcompany.model.Category;
import com.tcompany.model.Users;
import com.tcompany.service.CategoryService;
import com.tcompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
//@Order(2)
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@RequestMapping(method = RequestMethod.GET, value = { "/categories" })
	public ModelAndView categories() {
		ModelAndView andView = new ModelAndView("/categories/categories");

		andView.addObject("categories", categoryService.getAll());
		andView.addObject("category", new Category());
		
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = { "/saveCategory", "*/saveCategory" })
	public ModelAndView saveCategory(@ModelAttribute Category category)
			 throws IOException {
		ModelAndView andView = new ModelAndView("/categories/categories");
			Category category_saved =categoryService.addOrUpdateCategory(category);
			andView.addObject("categories", categoryService.getAll());
			andView.addObject("category", category_saved);
		andView.addObject("msg","Saved successful");
	
		return andView;
	}
	@RequestMapping(method = RequestMethod.GET, value = { "/categories/new" })
	public ModelAndView createCategory() {
		ModelAndView andView = new ModelAndView("/categories/category-form");

		andView.addObject("category", new Category());
		andView.addObject("title", "Add New Category");
		return andView;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/listcategories")
	public ModelAndView listCategories() {
		ModelAndView andView = new ModelAndView("/categories/categories");

		andView.addObject("categories", categoryService.getAll());
		andView.addObject("category", new Category());
		
		return andView;
	}

	@GetMapping("/editCategory/{id}")
	public ModelAndView editCategory(@PathVariable("id") Integer id) {
		ModelAndView andView = new ModelAndView("/categories/category-form");
	Category category = categoryService.getCategoryById(id);
		andView.addObject("category", category);
		andView.addObject("title", "Edit Category");
		return andView;
	}

	@RequestMapping(value = { "/deleteCategory/{id}" }, method = RequestMethod.GET)
	public ModelAndView deleteCategory(@PathVariable("id") Integer id) throws Exception {
	Category category =	categoryService.deleteCategory(id);
	String msg = "";
	if(category==null){
		msg="Not found category to delete";
	}else{
		msg="Category "+category.getCategoryName()+" deleted successful";
	}
		ModelAndView andView = new ModelAndView("/categories/categories");

		andView.addObject("categories", categoryService.getAll());
		andView.addObject("category", new Category());
		andView.addObject("msg",msg);
		return andView;
	}

}
