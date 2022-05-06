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
import com.school.demo.models.Students;
import com.school.demo.repository.StudentRepo;




@RestController
@RequestMapping("/api/")
public class StudentController {
	
	@Autowired
	private StudentRepo studentrepo;
	
	//get all Students
	@GetMapping("/students")
	public List<Students> getAllStudents(){
		return studentrepo.findAll();
	}
	
	// create Student rest api
		@PostMapping("/students")
		public Students createStudent(@RequestBody Students student) {
			return studentrepo.save(student);
		}
	
		@GetMapping("/students/{id}")
		public ResponseEntity<Students> getStudentsById(@PathVariable Long id) {
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			return ResponseEntity.ok(student);
		}
		
		
		@DeleteMapping("/students/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :" + id));
			
			studentrepo.delete(student);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		
		@PutMapping("/students/{id}")
		public ResponseEntity<Students> updateStudent(@PathVariable Long id, @RequestBody Students studentDetails){
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			
			
//			student.setSid(studentDetails.getSid());
			student.setSname(studentDetails.getSname());
			student.setSaddress(studentDetails.getSaddress());
			student.setSDoB(studentDetails.getSDoB());
			student.setSGender(studentDetails.getSGender());
			student.setSMarks(studentDetails.getSMarks());
			student.setStandard(studentDetails.getStandard());
			student.setAttendence(studentDetails.getAttendence());
			student.setRegistration_no(studentDetails.getRegistration_no());
			
			
			Students updatedStudent = studentrepo.save(student);
			return ResponseEntity.ok(updatedStudent);
		}
		
		@PutMapping("/teacheracesstostudent/{id}")
		public ResponseEntity<Students> updateTStudent(@PathVariable Long id, @RequestBody Students studentDetails){
			Students student = studentrepo.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Student not exist with id :" + id));
			
			student.setSMarks(studentDetails.getSMarks());
			student.setAttendence(studentDetails.getAttendence());
			
			
			Students updatedStudent = studentrepo.save(student);
			return ResponseEntity.ok(updatedStudent);
		}

}
