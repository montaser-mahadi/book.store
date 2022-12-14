package com.samrt.dubai.book.store.repository;

import com.samrt.dubai.book.store.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByName(String name);
    Optional<Book> findByIsbn(String isbn);



}
