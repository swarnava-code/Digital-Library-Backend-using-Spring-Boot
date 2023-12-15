package com.sclab.library.repository;

import com.sclab.library.entity.Student;
import com.sclab.library.enumeration.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("SELECT s FROM Student s JOIN FETCH s.card c WHERE c.status = :status")
    List<Student> findStudentsByCardStatus(@Param("status") CardStatus status);

    @Query("SELECT s FROM Student s JOIN FETCH s.card c WHERE c.totalIssuedBook = :totalIssuedBook")
    List<Student> findStudentsByTotalIssuedBook(@Param("totalIssuedBook") int totalIssuedBook);

}