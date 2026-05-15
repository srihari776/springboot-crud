package com.ibm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.dto.StudentDto;
import com.ibm.entity.Student;
import com.ibm.response.ApiUserResponse;
import com.ibm.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
@Tag(name = "Student Management", description = "CRUD operations for students")
public class RestFormController {
	@Autowired
	private StudentService stService;

	// ==================== ADD ====================
	@PostMapping("/add")
	@Operation(summary = "Add new student", description = "Creates a new student record in database")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Student created successfully"),
			@ApiResponse(responseCode = "400", description = "Validation failed") })
	public ResponseEntity<ApiUserResponse> add(@RequestBody @Valid StudentDto student) {
		Student s = stService.add(student);
		ApiUserResponse api = new ApiUserResponse(true, "Successfully saved", s);
		return new ResponseEntity<ApiUserResponse>(api, HttpStatus.CREATED);

	}

	// ==================== UPDATE ====================
	@PutMapping("/update")
    @Operation(
            summary = "Update student",
            description = "Updates existing student by ID"
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Student updated successfully"),
            @ApiResponse(responseCode = "404",
                         description = "Student not found"),
            @ApiResponse(responseCode = "400",
                         description = "Validation failed")
        })
	public ResponseEntity<ApiUserResponse> put(@RequestBody @Valid StudentDto student) {
		Student s = stService.updateStudent(student.getId(), student.getName());
		ApiUserResponse api;

		api = new ApiUserResponse(true, "Successfully updated", s);
		return new ResponseEntity<ApiUserResponse>(api, HttpStatus.OK);
	}

	// ==================== GET ALL ====================
	@Operation(summary = "Get all students", description = "Returns list of all students")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Students fetched successfully"),
			@ApiResponse(responseCode = "404", description = "No students found") })
	@GetMapping("/getall")
	public ResponseEntity<ApiUserResponse> getAll() {
		List<Student> students = stService.getAll();

		List<StudentDto> std = new ArrayList<>();

		for (Student s : students) {
			StudentDto sdto = new StudentDto();
			sdto.setId(s.getId());
			sdto.setName(s.getName());

			std.add(sdto);
		}
//        if (students.isEmpty()) {
//            ApiResponse api = new ApiResponse(
//                    false,
//                    "No students found",
//                    null
//            );
//            return new ResponseEntity<ApiResponse>(api, HttpStatus.NOT_FOUND);
//            
//        }
		ApiUserResponse api = new ApiUserResponse(true, "Data fetched successfully", std);
		return new ResponseEntity<ApiUserResponse>(api, HttpStatus.OK);

	}
	
	
	
	
	
	
	//===================== GET BY PAGES ==================
	// In RestFormController.java
	@GetMapping("/search/{keyword}/{pageNumber}/{pageSize}")
	@Operation(
	    summary = "Search students by name with pagination",
	    description = "Search students whose name contains keyword " +
	                  "with pagination support"
	)
	public ResponseEntity<ApiUserResponse> searchPaged(
	        @Parameter(description = "Search keyword", example = "hari")
	        @PathVariable String keyword,
	        @Parameter(description = "Page number starting from 0",
	                   example = "0")
	        @PathVariable int pageNumber,
	        @Parameter(description = "Number of records per page",
	                   example = "3")
	        @PathVariable int pageSize) {
	    Page<Student> result =
	            stService.searchByNamePaged(keyword, pageNumber, pageSize);
	    return new ResponseEntity<>(
	        new ApiUserResponse(true, "Search results", result),
	        HttpStatus.OK
	    );
	}
		

	// ==================== GET BY ID ====================
	@GetMapping("/get/{value}")
    @Operation(
            summary = "Get student by ID",
            description = "Returns single student by their ID"
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Student found"),
            @ApiResponse(responseCode = "404",
                         description = "Student not found")
        })
	public ResponseEntity<ApiUserResponse> getById(@PathVariable String value) {
//      Student s =stService.getStudentById(id);
//        ApiResponse api;
//     
//            api = new ApiResponse(
//                    true,
//                    "Student found",
//                    s
//            );
//            return new ResponseEntity<ApiResponse>(api, HttpStatus.OK);

		try {
			int id = Integer.parseInt(value);
			Student s = stService.getStudentById(id);
			StudentDto st = new StudentDto();
			st.setId(s.getId());
			st.setName(s.getName());
			ApiUserResponse api = new ApiUserResponse(true, "Student found", st);
			return new ResponseEntity<ApiUserResponse>(api, HttpStatus.OK);

		} catch (NumberFormatException e) {
			List<Student> students = stService.findByName(value);
			ApiUserResponse api = new ApiUserResponse(true, "Student found", students);
			return new ResponseEntity<ApiUserResponse>(api, HttpStatus.OK);
		}

	}

	// ==================== DELETE BY ID ====================
	@DeleteMapping("/remove/{id}")
    @Operation(
            summary = "Delete student",
            description = "Removes student from database by ID"
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404",
                         description = "Student not found")
        })
	public ResponseEntity<ApiUserResponse> removeById(@PathVariable int id) {
		stService.getStudentById(id);
		ApiUserResponse api;
		stService.removeById(id);
		api = new ApiUserResponse(true, "Student removed successfully", null);
		return new ResponseEntity<ApiUserResponse>(api, HttpStatus.OK);

//        api = new ApiResponse(
//                false,
//                "Student not found with id: " + id,
//                null
//        );
//        return new ResponseEntity<ApiResponse>(api, HttpStatus.NOT_FOUND);
		
		
		

	}
}