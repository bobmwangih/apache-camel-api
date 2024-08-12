package com.bob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bob.entity.Book2;

@Repository
public interface BookRepository2 extends JpaRepository<Book2, String> {


	Book2 findBookByName(String name);
}