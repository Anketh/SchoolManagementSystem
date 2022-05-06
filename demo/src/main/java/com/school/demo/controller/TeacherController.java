package com.school.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.demo.exception.ResourseNotFoundException;
import com.school.demo.models.Teachers;
import com.school.demo.repository.TeacherRepo;



@RestController
@RequestMapping("/api/")
public class TeacherController {
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	//get all Teacher
	@GetMapping("/teachers")
	public List<Teachers> getAllTeachers(){
		return teacherRepo.findAll();
	}
	
	// create Student rest api
		@PostMapping("/teachers")
		public Teachers createStudent(@RequestBody Teachers teacher) {
			return teacherRepo.save(teacher);
		}
	
		@GetMapping("/teachers/{id}")
		public ResponseEntity<Teachers> getTeacherById(@PathVariable Long id) {
			Teachers teacher = teacherRepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			return ResponseEntity.ok(teacher);
		}
		
		
		@DeleteMapping("/teachers/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
			Teachers teacher = teacherRepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :" + id));
			
			teacherRepo.delete(teacher);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		
		@PutMapping("/teachers/{id}")
		public ResponseEntity<Teachers> updateStudent(@PathVariable Long id, @RequestBody Teachers teacherDetails){
			Teachers teacher = teacherRepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Teacher not exist with id :" + id));
			
			
//			teacher.setTid(teacherDetails.getTid());
			teacher.setTname(teacherDetails.getTname());
			teacher.setTaddress(teacherDetails.getTaddress());
			teacher.setSubject(teacherDetails.getSubject());
			
			
			Teachers updatedTeacher = teacherRepo.save(teacher);
			return ResponseEntity.ok(updatedTeacher);
		}


}
