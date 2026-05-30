package com.localhost.library.service;

import com.localhost.library.model.Book;
import com.localhost.library.model.BookIssue;
import com.localhost.library.model.FineStatus;
import com.localhost.library.model.UserInfo;
import com.localhost.library.repository.BookIssueRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class BookIssueServiceImpl implements BookIssueService {
	
	@Autowired
    private BookService bookService;
	
	@Autowired
    private UserService userService;
    
    @Autowired
    private BookIssueRepository bookIssueRepository;
    
    @Autowired private SettingService settingService;
    


    @Override
    public List<BookIssue> getAllIssues() {
        return bookIssueRepository.findAll();
    }

    public List<BookIssue> getAllIssuesByUser(UserInfo user) {
        return bookIssueRepository.findAllByUser(user);
    }

    @Override
    public BookIssue saveIssue(BookIssue issue) {
    	Book book = bookService.getBookById(issue.getBookId());
    	Long bookQuantity = book.getQuantity();
    	long totalIssuedBook = Objects.requireNonNullElse(book.getTotalIssuedBook(), 0L);
    	
    	if(totalIssuedBook >= bookQuantity) {
    		throw new RuntimeException("Not enough book to issue");
    	}
    	
    	UserInfo user = userService.getUserById(issue.getUserId());
    	issue.setUser(user);
        issue.setBook(book);
      
        
        BookIssue issuedBook =  bookIssueRepository.save(issue);
        
        book.setTotalIssuedBook(totalIssuedBook + 1);
        bookService.updateBook(book.getId(), book);
        
        return issuedBook;
    }
    
    
    /** Return a book and finalize the fine as of 'actualReturnDate' (today if null). */
    
    public void returnBook(Long issueId) {
        BookIssue issue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        if (issue.getActualReturnDate() != null) {
            return; // already returned
        }
        
        issue.setActualReturnDate(LocalDate.now());
        
        // Free inventory
        Book book = issue.getBook();
        long totalIssuedBook = Objects.requireNonNullElse(book.getTotalIssuedBook(), 0L);
        book.setTotalIssuedBook(Math.max(0, (Long) (totalIssuedBook - 1)));
        bookService.updateBook(book.getId(), book);

        return;
    }
    
    
    @Override
    public List<BookIssue> calculateLateFee(List<BookIssue> bookIssues){
    	for (BookIssue bookIssue : bookIssues) {
    		if (bookIssue.getDueDate() == null) {
    	        continue; // skip already returned books
    	    }
    		bookIssue.setFineAmount(settingService.getLateFee(bookIssue.getDueDate()));
    		  bookIssueRepository.save(bookIssue);
    	}
    	return bookIssues;
    }
    
    
    /** Pay fine (no payment gateway here; just mark as PAID). */
    
    @Override
    public void payFine(Long issueId) {
        BookIssue issue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

       
        bookIssueRepository.save(issue);
    }
    
    
    /** Delete an issue record (admin) and fix inventory if needed. */
    @Override
    public void deleteIssue(Long id) {
        BookIssue issue = bookIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        Book book = issue.getBook();
        if (book != null) {
            long totalIssuedBook = Objects.requireNonNullElse(book.getTotalIssuedBook(), 0L);
            if (!issue.isReturned()) {
                // if deletion happens while still issued, free one copy
                book.setTotalIssuedBook(Math.max(0, (Long) (totalIssuedBook - 1)));
                bookService.updateBook(book.getId(), book);
            }
        }

        bookIssueRepository.deleteById(id);
    }
    

	/*
	 * @Override public void deleteIssue(Long id) {
	 * bookIssueRepository.deleteById(id); Book book = bookService.getBookById(id);
	 * Long totalIssuedBook = book.getTotalIssuedBook();
	 * book.setTotalIssuedBook(totalIssuedBook - 1); }
	 */
}


