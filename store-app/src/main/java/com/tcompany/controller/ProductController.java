package com.tcompany.controller;

import com.tcompany.model.Product;
import com.tcompany.model.Users;
import com.tcompany.service.CategoryService;
import com.tcompany.service.ProductService;
import com.tcompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
//@Order(2)
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(method = RequestMethod.GET, value = {"/products"})
	public ModelAndView index() {
		ModelAndView andView = new ModelAndView("/products/products");

		andView.addObject("products", productService.getAll());
		andView.addObject("product", new Product());

		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = {"/saveProduct", "*/saveProduct"})
	public ModelAndView saveProduct(@ModelAttribute @Validated Product product, BindingResult bindingResult,
									final MultipartFile photo)
			throws IOException {


		ModelAndView andView = new ModelAndView("/products/products");

		if (photo.getSize() > 0) {
			product.setPhoto(photo.getBytes());
			product.setPhotoType(photo.getContentType());
			product.setPhotoName(photo.getOriginalFilename());

		} else {
			if (product.getProductId() > 0) {
				Product productTemp = productService.getProductById(product.getProductId());
				product.setPhoto(productTemp.getPhoto());
				product.setPhotoType(productTemp.getPhotoType());
				product.setPhotoName(productTemp.getPhotoName());
			}
		}
		Product product_saved = productService.addOrUpdateProduct(product);
		andView.addObject("products", productService.getAll());
		andView.addObject("product", product_saved);
		andView.addObject("msg", "Saved successful");

		return andView;
	}

	@RequestMapping(method = RequestMethod.GET, value = {"/products/new"})
	public ModelAndView createProduct() {
		ModelAndView andView = new ModelAndView("/products/product-form");

		andView.addObject("product", new Product());
		andView.addObject("title", "Add New Product");
		andView.addObject("categories", categoryService.getAll());
		return andView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listproducts")
	public ModelAndView listProducts() {
		ModelAndView andView = new ModelAndView("/products/products");

		andView.addObject("products", productService.getAll());
		andView.addObject("product", new Product());
		andView.addObject("categories", categoryService.getAll());

		return andView;
	}

	@GetMapping("/editProduct/{id}")
	public ModelAndView editProduct(@PathVariable("id") Integer id) {
		ModelAndView andView = new ModelAndView("/products/product-form");
		Product product = productService.getProductById(id);
		andView.addObject("products", productService.getAll());
		andView.addObject("title", "Edit Product");
		andView.addObject("product", product);
		andView.addObject("categories", categoryService.getAll());
		return andView;
	}

	@RequestMapping(value = {"/deleteProduct/{id}"}, method = RequestMethod.GET)
	public ModelAndView deleteProduct(@PathVariable("id") Integer id) throws Exception {
		Product product = productService.deleteProduct(id);
		String msg = "";
		if (product == null) {
			msg = "Not found Product to delete";
		} else {
			msg = "User " + product.getProductName() + " deleted successful";
		}
		ModelAndView andView = new ModelAndView("/products/products");

		andView.addObject("products", productService.getAll());
		andView.addObject("product", new Product());
		andView.addObject("msg", msg);
		return andView;
	}


	@GetMapping("/image/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Integer id, HttpServletResponse response)
			throws ServletException, IOException {

		Product product = productService.getProductById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(product.getPhoto());
		response.getOutputStream().close();
	}
}
