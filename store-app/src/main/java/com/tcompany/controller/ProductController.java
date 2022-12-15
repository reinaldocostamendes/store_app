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
public class ProductController {
	@Autowired
	private UserService userService;
	@RequestMapping(method = RequestMethod.GET, value = { "/products" })
	public ModelAndView products() {
		ModelAndView andView = new ModelAndView("/products/products");

		andView.addObject("users", userService.getAll());
		andView.addObject("userobj", new Users());
		
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = { "/saveProduct", "*/saveProducts" })
	public ModelAndView saveProduct(@ModelAttribute Users user)
			 throws IOException {
		
		
		ModelAndView andView =  new ModelAndView("/products/products");
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
	@RequestMapping(method = RequestMethod.GET, value = { "/products/new" })
	public ModelAndView create() {
		ModelAndView andView = new ModelAndView("/products/product-form");

		andView.addObject("userobj", new Users());
		andView.addObject("title", "Add New User");
		return andView;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/listProducts")
	public ModelAndView users() {
		ModelAndView andView = new ModelAndView("/products/products");

		andView.addObject("users", userService.getAll());
		andView.addObject("userobj", new Users());
		
		return andView;
	}

	@GetMapping("/editProduct/{id}")
	public ModelAndView edit(@PathVariable("id") Integer id) {
		ModelAndView andView = new ModelAndView("/users/product-form");
		Users user = userService.getUserById(id);
		andView.addObject("userobj", user);
		andView.addObject("title", "Edit User");
		return andView;
	}

	@RequestMapping(value = { "/deleteProduct/{id}" }, method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") Integer id) throws Exception {
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
