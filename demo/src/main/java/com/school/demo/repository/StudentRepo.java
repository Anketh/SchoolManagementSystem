package com.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.demo.models.Students;

@Repository
public interface StudentRepo extends JpaRepository<Students, Long>{

}
