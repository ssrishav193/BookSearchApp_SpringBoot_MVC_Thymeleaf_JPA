package com.rishav.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rishav.dto.BookSummary;
import com.rishav.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, String>{
	@Query("SELECT new com.rishav.dto.BookSummary(b.name, b.aname, b.pname, b.price, b.hasContent) FROM Book b")
	List<BookSummary> getAllBooksSummary();

}
