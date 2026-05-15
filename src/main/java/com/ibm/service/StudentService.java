package com.ibm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ibm.dto.StudentDto;
import com.ibm.entity.Student;
import com.ibm.exception.StudentNotFoundException;
import com.ibm.repository.StudentRepository;

@Service
public class StudentService {
	// ❌ REMOVE THIS - no more List
	// List<Student> st = new ArrayList<>();
	@Autowired
	private StudentRepository studentRepo; // ← Inject Repository
	// ==================== ADD ====================

	public Student add(StudentDto st) {

		Student s = new Student();
		s.setId(st.getId());
		s.setName(st.getName());
		return studentRepo.save(s);

		// save() = INSERT if new, UPDATE if ID exists
	}

	// ==================== GET ALL ====================
	public List<Student> getAll() {
		return studentRepo.findAll();
		// SELECT * FROM students
	}

	// ==================== GET BY ID ====================
	public Student getStudentById(int id) {
		return studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
		// Optional = might have value, might be empty (null safe)
//        if (student.isPresent()) {
//            return student.get();   // return the student
//        }
//        return null;                // student not found
		// Shorter way to write above:
		// return studentRepo.findById(id).orElse(null);
	}

	// ==================== UPDATE ====================
	public Student updateStudent(int id, String name) {
		return studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
//        if (existing.isPresent()) {
//            Student s = existing.get();
//            s.setName(name);
//            return studentRepo.save(s);  // save() with existing ID = UPDATE
//        }
//        return null;
	}

	// ==================== DELETE BY ID ====================
	public void removeById(int id) {
		studentRepo.deleteById(id);
		// DELETE FROM students WHERE id = ?
	}

	// ==================== DELETE BY OBJECT ====================
	public void remove(Student s) {
		studentRepo.delete(s);
	}

	// ==================== FIND BY NAME ============================
	public List<Student> findByName(String name) {
		List<Student> students = studentRepo.findByName(name);
		if (students.isEmpty()) {
			throw new StudentNotFoundException("No students found with name: " + name);
		}
		return students;
	}

	public List<Student> getPageStudent(int num) {
		Pageable pg = PageRequest.of(0, 3, Sort.by("name").descending());
		return null;
	}

	public Page<Student> searchByNamePaged(String keyword, int pageNumber, int pageSize) {
		Pageable pg = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
		Page<Student> result = studentRepo.findByNameContainingIgnoreCase(keyword, pg);
		if (result.isEmpty()) {
			throw new StudentNotFoundException("No students found with keyword: " + keyword);
		}
		return result;
	}
}

//package com.ibm.service;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.ibm.model.Student;
//@Service
//public class StudentService {
//	
//    List<Student> st = new ArrayList<>();
//    
//    
//    StudentService(){
//    	st.add(new Student(101,"Srihari"));
//    	st.add(new Student(102,"harry"));
//    	st.add(new Student(103,"nyx"));
//    }
//    // ADD
//    public void add(Student s) {
//        st.add(s);
//    }
//    // GET ALL
//    public List<Student> getAll() {
//        return st;
//    }
//    // GET BY ID
//    public Student getStudentById(int id) {
//        for (Student s : st) {
//            if (s.getId() == id) {
//                return s;
//            }
//        }
//        return null;
//    }
//    // UPDATE
//    public void updateStudent(int id, String name) {
//        for (Student s : st) {
//            if (s.getId() == id) {
//                s.setName(name);
//                break;
//            }
//        }
//    }
//    // DELETE BY ID
//    public void removeById(int id) {
//        st.removeIf(s -> s.getId() == id);
//    }
//    // DELETE BY OBJECT
//    public void remove(Student s) {
//        st.remove(s);
//    }
//}