package com.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.demo.models.Teachers;

@Repository
public interface TeacherRepo extends JpaRepository<Teachers, Long>{

}



