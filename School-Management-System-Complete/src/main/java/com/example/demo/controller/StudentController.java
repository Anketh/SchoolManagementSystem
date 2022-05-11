package com.example.demo.controller;


import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.example.demo.common.UserConstant;
import com.example.demo.entity.Students;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.repository.StudentRepo;
import com.example.demo.repository.UserRepository;






@RestController
@RequestMapping("/user")
public class StudentController {
	
	@Autowired
	private StudentRepo studentrepo;
	
	@Autowired
	private UserRepository repository;
	
	
	@GetMapping("/students")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
	public List<Students> getAllStudents(){
		return studentrepo.findAll();
	}
	
	
		@PostMapping("/students")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_STUDENT')")
		public Students createStudent(@RequestBody Students student) {
			
			
			student.setAttendence("");
			student.setSMarks("");
			
			return studentrepo.save(student);
		}
	
		@GetMapping("/students/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_STUDENT')")
		public ResponseEntity<Students> getStudentsById(@PathVariable Long id) {
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			return ResponseEntity.ok(student);
		}
		
		
		@DeleteMapping("/students/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
		public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :" + id));
			
			studentrepo.delete(student);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		
		@PutMapping("/students/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_STUDENT')")
		public ResponseEntity<Students> updateStudent(@PathVariable Long id, @RequestBody Students studentDetails){
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			
			
//			student.setSid(studentDetails.getSid());
			student.setSname(studentDetails.getSname());
			student.setSaddress(studentDetails.getSaddress());
			student.setSDoB(studentDetails.getSDoB());
			student.setSGender(studentDetails.getSGender());
//			student.setSMarks(studentDetails.getSMarks());
			student.setStandard(studentDetails.getStandard());
//			student.setAttendence(studentDetails.getAttendence());
			student.setRegistration_no(studentDetails.getRegistration_no());
			
			
			Students updatedStudent = studentrepo.save(student);
			return ResponseEntity.ok(updatedStudent);
		}
		
		@PutMapping("/teacheracesstostudent/{id}")
		@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
		public ResponseEntity<Students> updateTStudent(@PathVariable Long id, @RequestBody Students studentDetails){
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			
			student.setSMarks(studentDetails.getSMarks());
			student.setAttendence(studentDetails.getAttendence());
			
			
			Students updatedStudent = studentrepo.save(student);
			return ResponseEntity.ok(updatedStudent);
		}
		
		
		
		

}
