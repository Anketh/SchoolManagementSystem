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
import com.school.demo.models.Assignments;
import com.school.demo.repository.AssignmentRepo;


@RestController
@RequestMapping("/api/")
public class AssignmentController {

	@Autowired
	private AssignmentRepo assignmentrepo;
	
	
	@GetMapping("/assignments")
	public List<Assignments> getAllAssignments(){
		return assignmentrepo.findAll();
	}
	
	@GetMapping("assignments/{id}")
	public ResponseEntity<Assignments> getAssignmentByStandard(@PathVariable Long id){
		
		Assignments assignment = assignmentrepo.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Assignment not exist with id :" + id));
		return ResponseEntity.ok(assignment);
		
	}
	
	@PostMapping("/assignments")
	public Assignments createAssignments(@RequestBody Assignments assignments) {
		return assignmentrepo.save(assignments);
	}
	
	@DeleteMapping("/assignments/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAssignment(@PathVariable Long id)
	{
		Assignments assignment = assignmentrepo.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Assignment not exist with id :" + id));
		assignmentrepo.delete(assignment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/assignments/{id}")
	public ResponseEntity<Assignments> updateAssignment(@PathVariable Long id,@RequestBody Assignments assignmentDetails){
		
		Assignments assignment = assignmentrepo.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Assignment not exist with id :" + id));
		
		assignment.setQuestion(assignmentDetails.getQuestion());
		assignment.setAnswer(assignmentDetails.getAnswer());
		
		Assignments updatedAssignment =assignmentrepo.save(assignment);

		return ResponseEntity.ok(updatedAssignment);
		
	}
	
	@PutMapping("/assignmentsanswered/{id}")
	public ResponseEntity<Assignments> updateAssignmentByStudent(@PathVariable Long id,@RequestBody Assignments assignmentDetails){
		
		Assignments assignment = assignmentrepo.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Assignment not exist with id :" + id));
		
		assignment.setAnswer(assignmentDetails.getAnswer());
		
		Assignments updatedAssignment =assignmentrepo.save(assignment);

		return ResponseEntity.ok(updatedAssignment);
		
	}
	
	
}
