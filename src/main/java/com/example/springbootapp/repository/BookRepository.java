package com.example.springbootapp.repository;

import com.example.springbootapp.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Books,Long> {}
