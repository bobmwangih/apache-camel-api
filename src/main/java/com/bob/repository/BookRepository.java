package com.bob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bob.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findBookByName(String name);
}
