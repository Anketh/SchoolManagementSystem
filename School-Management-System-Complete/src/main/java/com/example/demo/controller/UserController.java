package com.example.demo.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.UserConstant;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@PostMapping("/admit")
	public String senddata(@RequestBody User user) {
		user.setRole(UserConstant.DEFAULT_ROLE);
		String encyptedpassword =passwordEncoder.encode(user.getPassword());
		user.setPassword(encyptedpassword);
		repository.save(user);
		return "Inserted Successfully";
	}
	
	@GetMapping("/access/{user_id}/{userRole}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
	public String giveAccessToUser(@PathVariable Long user_id,@PathVariable String userRole, Principal principal) {
		User user =repository.findById(user_id).get();
		List<String> activeRoles=getRoleByLoggedInUser(principal);
//		String newRole="";
		if(activeRoles.contains(userRole)) {
//			newRole =user.getRole()+","+userRole;
			user.setRole(userRole);
			repository.save(user);
			return "Hi "+user.getUsername()+" new role assigned to you by "+principal.getName();
		}else {
			return "You dont have the Rights to change role to " + userRole;
		}
		
		
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> loadUser(){
		return repository.findAll();
	}
	
	@GetMapping("/test")
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	public String testUSer() {
		return "Only student access this";
	}
	
	
	
	private List<String> getRoleByLoggedInUser(Principal principal){
		String roles=getloggedInUser(principal).getRole();
		List<String> assignedRoles=Arrays.stream(roles.split(",")).collect(Collectors.toList());
		if(assignedRoles.contains("ROLE_ADMIN")) {
			return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
		}
		if(assignedRoles.contains("ROLE_TEACHER")) {
			return Arrays.stream(UserConstant.TEACHER_ACCESS).collect(Collectors.toList());
		}
//		if(assignedRoles.contains("ROLE_STUDENT")) {
//			return Arrays.stream(STUDENT_ACCESS).collect(Collectors.toList());
//		}
		return Collections.emptyList();
	}
	
	
	private User getloggedInUser(Principal principal) {
		
		return repository.findByUsername(principal.getName()).get();
		
	}

}
