
package com.localhost.library.service;

import com.localhost.library.model.Book;
import com.localhost.library.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
       return bookRepository.save(book);
    }
    
    public Book updateBook(Long id, Book newBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setGenre(newBook.getGenre());
            book.setQuantity(newBook.getQuantity());
            book.setPublisher(newBook.getPublisher());
            book.setPublisherYear(newBook.getPublisherYear());
            book.setISBIN(newBook.getISBIN());
            book.setEdition(newBook.getEdition());
            book.setPrice(newBook.getPrice());
            return bookRepository.save(book);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Book> searchBooks(String title) {
        return bookRepository.findByTitleContaining(title);
    }
    

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
		return false;
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}


