package com.ibm.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ibm.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    //                                                     ↑        ↑
    //                                               Entity Type   ID Type
    // That's it! No code needed!
    // Spring automatically gives you these methods:
    // save(entity)          → INSERT or UPDATE
    // findAll()             → SELECT * FROM students
    // findById(id)          → SELECT * WHERE id = ?
    // deleteById(id)        → DELETE WHERE id = ?
    // delete(entity)        → DELETE by object
    // count()               → SELECT COUNT(*)
    // existsById(id)        → check if record exists
	
	
	List<Student> findByName(String name);
	Page<Student> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}