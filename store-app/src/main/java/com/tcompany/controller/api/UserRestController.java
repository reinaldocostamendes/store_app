package com.tcompany.controller.api;

import com.tcompany.dto.UserAdpter;
import com.tcompany.dto.UserDto;
import com.tcompany.model.Users;
import com.tcompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping( value = "/api/v1/users" )
@Order(2)
public class UserRestController {
	@Autowired
	private UserService userService;
	@RequestMapping(method = RequestMethod.GET, value = { "/listAll" })
	public List<Users> listAll() {
		return userService.getAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = { "/create"})
	public Users save(@RequestBody UserDto user)
			 throws IOException {
			Users user_saved = userService.addOrUpdateUSer(UserAdpter.toEntity(user));

		return user_saved;
	}

	@RequestMapping(method = RequestMethod.PUT, value = { "/update"})
	public Users update(@RequestBody UserDto user)
			throws IOException {
		Users user_saved = userService.addOrUpdateUSer(UserAdpter.toEntity(user));

		return user_saved;
	}

	@RequestMapping(value = { "/deleteUser/{id}" }, method = RequestMethod.GET)
	public String delete(@PathVariable("id") Integer id) throws Exception {
	Users user =	userService.deleteUser(id);
	String msg = "";
	if(user==null){
		msg="Not found user to delete";
	}else{
		msg="User "+user.getFirs_name()+" deleted successful";
	}
		return msg;
	}

}
