package com.apirest.service.impl;

import com.apirest.model.Book;
import com.apirest.repository.BookRepository;
import com.apirest.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
    
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Integer idBook) {
        return bookRepository.findById(idBook).orElse(null);
    }

    @Override
    public void delete(Integer idBook) {
        bookRepository.deleteById(idBook);
    }   
}
