package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Teachers;
import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.repository.TeacherRepo;





@RestController
@RequestMapping("/user")
public class TeacherController {
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	
	@GetMapping("/teachers")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
	public List<Teachers> getAllTeachers(){
		return teacherRepo.findAll();
	}
	
	
		@PostMapping("/teachers")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
		public Teachers createStudent(@RequestBody Teachers teacher) {
			return teacherRepo.save(teacher);
		}
	
		@GetMapping("/teachers/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
		public ResponseEntity<Teachers> getTeacherById(@PathVariable Long id) {
			Teachers teacher = teacherRepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			return ResponseEntity.ok(teacher);
		}
		
		
		@DeleteMapping("/teachers/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN')")
		public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
			Teachers teacher = teacherRepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :" + id));
			
			teacherRepo.delete(teacher);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		
		@PutMapping("/teachers/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
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
