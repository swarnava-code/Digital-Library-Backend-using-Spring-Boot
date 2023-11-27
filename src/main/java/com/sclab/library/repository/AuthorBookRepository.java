package com.sclab.library.repository;

import com.sclab.library.entity.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, String> {
}