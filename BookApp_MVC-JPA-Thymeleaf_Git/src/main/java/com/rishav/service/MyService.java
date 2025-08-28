package com.rishav.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.rishav.dto.BookSummary;
import com.rishav.entity.Book;
import com.rishav.entity.Login;
import com.rishav.repo.BookRepo;
import com.rishav.repo.LoginRepo;
import com.rishav.repo.MyRepoCustom;

@Service
public class MyService {
	@Autowired
	BookRepo bookRepo;
	@Autowired
	LoginRepo loginRepo;
	
	@Autowired
	MyRepoCustom myRepoCustom;

	public Book getBook(String name) {
		Book b=bookRepo.findById(name).orElse(null);
		return b;
	}

	public List<Book> getBooks(String name) {
		List<Book> books=myRepoCustom.getBooks(name);
		return books;
	}

	public String checkLogin(Login login) {
		Login l=loginRepo.findById(login.getId()).orElse(null);
		if(l!=null && l.getPassword().equals(login.getPassword())) {
			return l.getName();
		}
		return null;
	}

	public boolean addBook(Book book) {
		Book b=bookRepo.findById(book.getName()).orElse(null);
		if(b==null) {
			bookRepo.save(book);
			return true;
		}else {
			return false;
		}
		
	}

	public List<Book> getAllBooks() {
		return bookRepo.findAll();
		
	}

	public boolean deleteBook(String name) {
//		bookRepo.deleteById(name);
		
		Book b=bookRepo.findById(name).orElse(null);
		if(b==null) {
			return false;
		}else {
//			bookRepo.delete(b);
			bookRepo.deleteById(name);
			return true;
		}
		
	}

	public List<BookSummary> getAllBooksSummary() {
		return bookRepo.getAllBooksSummary();
//		return myRepoCustom.getAllBooksSummary();
	}

	

	public Page<Book> getBooksPage(int page, int size) {
		PageRequest pageable=PageRequest.of(page, size);
		
		return bookRepo.findAll(pageable);
	}
	
	
	public void updateBook(Book book) {
	    bookRepo.save(book); // This will update because ID (name) already exists
	}


}
