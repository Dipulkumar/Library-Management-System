package com.localhost.library.controller;

import com.localhost.library.model.Book;
import com.localhost.library.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
 
    @Autowired
    private BookService bookService;

    @PreAuthorize("permitAll()")
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book"; // Make sure you have books.html
    }
    
    @PreAuthorize("hasAuthority('ALL') or hasAuthority('addBook')")
    @GetMapping("/books/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('addBook')")
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }
    
    @PreAuthorize("hasAuthority('ALL') or hasAuthority('updateBook')")
    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if(book == null) {
	        return "redirect:/error";
        }
        model.addAttribute("book", book);
        return "editbook";
    }
    
    @PreAuthorize("hasAuthority('ALL') or hasAuthority('updateBook')")
    @PostMapping("/books/update")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book.getId(), book);
        return "redirect:/books";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('deleteBook')")
    @DeleteMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    
    @PreAuthorize("permitAll()")
    @GetMapping("/books/search")
    public String searchBooks(@RequestParam String title, Model model) {
        model.addAttribute("books", bookService.searchBooks(title));
        return "books";
    }
}



