package com.apirest.controller;

import com.apirest.model.Book;
import com.apirest.service.BookService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        Book newBook = null;
        Map<String, Object> response = new HashMap<>();
        try {
            newBook = bookService.create(book);
        } catch (DataAccessException e) {
            response.put("message", "Error creating book!!");
            response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Book created successfully!!");
        response.put("newBook", newBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer idBook) {
        Book book = null;
        Map<String, Object> response = new HashMap<>();
        try {
            book = bookService.findById(idBook);
        } catch (DataAccessException d) {
            response.put("message", "Error database access!!");
            response.put("error", d.getMessage().concat(" ").concat(d.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if (book == null) {
            response.put("message", "The book is not found!!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable("id") Integer idBook) {
        Book bookUpdate = null;
        Book book1 = bookService.findById(idBook);
        Map<String, Object> response = new HashMap<>();
        if (book1 == null) {
            response.put("message", "The book is not found!!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            book1.setIsbn(book.getIsbn());
            book1.setTitle(book.getTitle());
            book1.setAuthor(book.getAuthor());
            bookUpdate = bookService.create(book1);
        } catch (DataAccessException d) {
            response.put("message", "Error updating book!!");
            response.put("error", d.getMessage().concat(" ").concat(d.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message", "Book updated successfully!!");
        response.put("bookUpdate", bookUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Integer idBook) {
        Map<String, Object> response = new HashMap<>();
        try {
            bookService.delete(idBook);
        } catch (DataAccessException d) {
            response.put("message", "Error deleting book!!");
            response.put("error", d.getMessage().concat(" ").concat(d.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Book deleted successfully!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
