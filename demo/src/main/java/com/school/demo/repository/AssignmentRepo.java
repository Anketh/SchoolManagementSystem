package com.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.demo.models.Assignments;


@Repository
public interface AssignmentRepo extends JpaRepository<Assignments, Long>{
	

}
