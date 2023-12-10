package com.sclab.library.repository;

import com.sclab.library.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("SELECT s FROM Student s JOIN FETCH s.card c WHERE c.status = 'Active'")
    List<Student> findActiveStudentsWithCard();

}