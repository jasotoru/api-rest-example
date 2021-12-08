package com.apirest.service;

import com.apirest.model.Book;
import java.util.List;

public interface BookService {
    
    Book create(Book book);
    void delete(Integer idBook);
    List<Book> getAll();
    Book findById(Integer idBook);
}
