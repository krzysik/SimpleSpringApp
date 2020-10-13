package com.example.springbootapp.controller;

import com.example.springbootapp.Exception.BookNotFoundException;
import com.example.springbootapp.domain.Books;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springbootapp.repository.BookRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Books> getAllBooks(){
      return this.bookRepository.findAll();
    }
    @PostMapping("/add")
    public Books createBook(@RequestBody Books books){
        return this.bookRepository.save(books);
    }
    @PostMapping("/books/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable(value = "id") Long id ) throws BookNotFoundException{
        Books books = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found for this" +
                                                                                                     " " +
                                                                                                 "id:"+id));
        return ResponseEntity.ok().body(books);

    }
    @PutMapping("/books/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable(value = "id") Long id ,@RequestBody Books books) throws BookNotFoundException{

        Books book = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found for this " +
                                                                                                    "id :"+id));
    book.setName(books.getName());
    book.setDescription(books.getDescription());
    book.setNumberOfPages(books.getNumberOfPages());

    return ResponseEntity.ok(this.bookRepository.save(book));

    }
    @DeleteMapping("/books/{id}")
    public Map<String,Boolean> deleteBook(@PathVariable(value = "id") Long id) throws BookNotFoundException{

        Books book = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found for this " +
                                                                                                    "id:"+id));
        this.bookRepository.delete(book);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);

        return response;

    }


}
