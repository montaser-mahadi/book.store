package com.samrt.dubai.book.store.controller;

import com.samrt.dubai.book.store.entity.Book;
import com.samrt.dubai.book.store.repository.BookRepository;
import com.samrt.dubai.book.store.repository.ClassificationRepository;
import com.samrt.dubai.book.store.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {


    // inject book repository in book controller by using notation @Autowired
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private ClassificationService classificationService;

    // get all book from MySQL Database
    @GetMapping(path="/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // get book data by id
    @GetMapping(path="book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id)
    {
        Optional<Book> bookData = bookRepository.findById(id);
        if(bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // save new book after checked it found in the database

    @PostMapping(path="/newBook")
    public ResponseEntity<?> createBook(@RequestBody Book book)
    {
        try {
            String massage="";
           Optional<Book> NameFound = bookRepository.findByName(book.getName());
           Optional<Book> IsbnFound = bookRepository.findByIsbn(book.getIsbn());
            if(IsbnFound.isPresent() || NameFound.isPresent()) {
                 massage = "This book name already exist!";
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(massage);
            }
            else {
                massage = "this book saved!";
                Book newBook = bookRepository.save(new Book(book.getName(), book.getDescription(),book.getAuthor(),
                        book.getClassification(), book.getPrice(), book.getAvailableQuantity(), book.getIsbn()));
                return  ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(massage);
            }

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path="book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Integer id, @RequestBody Book book)
    {
        try {
            String massage="";
            Optional<Book> bookData = bookRepository.findById(id);

            if(bookData.isPresent())
            {
                Optional<Book> isbnFound = bookRepository.findByIsbn(book.getIsbn());
                Optional<Book> nameFound = bookRepository.findByName(book.getName());
                if(isbnFound.isPresent() || isbnFound.isPresent())
                {
                    System.out.println("Can't update the book data when name and isbn exist!");
                    return new ResponseEntity<>(HttpStatus.resolve(403));
                }else {
                    Book updateBook = bookData.get();
                    updateBook.setIsbn(book.getIsbn());
                    updateBook.setName(book.getName());
                    updateBook.setAuthor(book.getAuthor());
                    updateBook.setDescription(book.getDescription());
                    updateBook.setPrice(book.getPrice());
                    updateBook.setClassification(book.getClassification());
                    return new ResponseEntity<>(bookRepository.save(updateBook), HttpStatus.OK);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path="/book/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("id") Integer id)
    {
        try {
            Optional<Book> isFound = bookRepository.findById(id);
            if(isFound.isPresent())
            {
                bookRepository.deleteById(id);
                return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(HttpStatus.valueOf("This Book It's not Founded " + HttpStatus.NOT_FOUND));
            }
        }catch (Exception exception){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path="/deleteAllBooks")
    public ResponseEntity<HttpStatus> deleteAllBooks(){
        try{
            bookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
