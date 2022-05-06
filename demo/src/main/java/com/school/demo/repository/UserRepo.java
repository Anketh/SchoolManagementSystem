package com.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.demo.models.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, Long>{
	
	


}
