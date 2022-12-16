package com.tcompany.controller;

import com.tcompany.model.Users;
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
	private UserService userService;
	@RequestMapping(method = RequestMethod.GET, value = { "/categories" })
	public ModelAndView categories() {
		ModelAndView andView = new ModelAndView("/categories/categories");

		andView.addObject("users", userService.getAll());
		andView.addObject("userobj", new Users());
		
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = { "/saveCategory", "*/saveCategory" })
	public ModelAndView saveCategory(@ModelAttribute Users user)
			 throws IOException {
		
		
		ModelAndView andView =  new ModelAndView("/categories/categories");
		if(user.getUser_id()>= 0 &&( user.getPassword()==null || user.getPassword().trim()=="")) {
			Users user_tmp = userService.getUserById(user.getUser_id());
			user.setPassword(user_tmp.getPassword());
		}else{
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		}
			Users user_saved = userService.addOrUpdateUSer(user);
			andView.addObject("users", userService.getAll());
			andView.addObject("userobj", user_saved);
		andView.addObject("msg","Saved successful");
	
		return andView;
	}
	@RequestMapping(method = RequestMethod.GET, value = { "/categories/new" })
	public ModelAndView createCategory() {
		ModelAndView andView = new ModelAndView("/categories/category-form");

		andView.addObject("userobj", new Users());
		andView.addObject("title", "Add New User");
		return andView;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/listcategories")
	public ModelAndView listCategories() {
		ModelAndView andView = new ModelAndView("/categories/categories");

		andView.addObject("users", userService.getAll());
		andView.addObject("userobj", new Users());
		
		return andView;
	}

	@GetMapping("/editCategory/{id}")
	public ModelAndView editCategory(@PathVariable("id") Integer id) {
		ModelAndView andView = new ModelAndView("/categories/category-form");
		Users user = userService.getUserById(id);
		andView.addObject("userobj", user);
		andView.addObject("title", "Edit User");
		return andView;
	}

	@RequestMapping(value = { "/deleteCategory/{id}" }, method = RequestMethod.GET)
	public ModelAndView deleteCategory(@PathVariable("id") Integer id) throws Exception {
	Users user =	userService.deleteUser(id);
	String msg = "";
	if(user==null){
		msg="Not found user to delete";
	}else{
		msg="User "+user.getFirs_name()+" deleted successful";
	}
		ModelAndView andView = new ModelAndView("/users/users");

		andView.addObject("users", userService.getAll());
		andView.addObject("userobj", new Users());
		andView.addObject("msg",msg);
		return andView;
	}

}
