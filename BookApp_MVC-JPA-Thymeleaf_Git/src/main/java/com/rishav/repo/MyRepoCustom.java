package com.rishav.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rishav.dto.BookSummary;
import com.rishav.entity.Book;

@Repository
public interface MyRepoCustom {

	List<Book> getBooks(String name);
	List<BookSummary> getAllBooksSummary();
	

}
