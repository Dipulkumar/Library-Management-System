package com.localhost.library.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_Id;

    private String title;

    private String author;
    
    private String genre;
    
    private Long quantity;
    
    private String publisher;
    
    @Column(name = "publisherYear")
    private LocalDate publisherYear;
    
    private String ISBIN;
    
    private String edition;
    
    private BigDecimal price;
    
    private Long totalIssuedBook;

	
    
    

    // Getters and setters

    public Long getId() {
        return book_Id;
    }

    public void setId(Long id) {
        this.book_Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getGenre() {
    	return genre;
    }
    
    public void setGenre(String genre) {
    	this.genre = genre;
    }
    
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
    public String getPublisher() {
    	return publisher;
    }
    
    public void setPublisher(String publisher) {
    	this.publisher = publisher;
    }
    
    public LocalDate getPublisherYear() {
        return publisherYear;
    }

    public void setPublisherYear(LocalDate publisherYear) {
        this.publisherYear = publisherYear;
    }
    
    public String getISBIN() {
    	return ISBIN;
    }
    
    public void setISBIN(String ISBIN) {
    	this.ISBIN = ISBIN;
    }
    
    public String getEdition() {
    	return edition;
    }
    
    public void setEdition(String edition) {
    	this.edition = edition ;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

	public Long getTotalIssuedBook() {
		return totalIssuedBook;
	}

	public void setTotalIssuedBook(Long totalIssuedBook) {
		this.totalIssuedBook = totalIssuedBook;
	}
    
    
}

