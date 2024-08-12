package com.bob.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bob.entity.Book2;
import com.bob.repository.BookRepository2;

@Service
public class BookService2 {

    private final BookRepository2 bookRepository;

    public BookService2(BookRepository2 bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book2 addBook(Book2 book) {
        return bookRepository.save(book);
    }
}
