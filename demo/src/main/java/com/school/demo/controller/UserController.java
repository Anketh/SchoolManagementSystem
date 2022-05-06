package com.school.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.demo.models.Roles;
import com.school.demo.models.Users;
import com.school.demo.repository.UserRepo;

@RestController
@RequestMapping("/api/")
public class UserController {

	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/registration")
	public Users createUser(@RequestBody Users users) {
		
		Users user=new Users(users.getFirstName(),users.getLastName(),users.getUserName(),users.getPassword(),Arrays.asList(new Roles("ROLE_USER")));
		
		
		

			return userRepo.save(user);
		
			
	}
	
	@GetMapping("/registration")
	public List<Users> getAllUsers(){
		return userRepo.findAll();
	}
	

	
}
