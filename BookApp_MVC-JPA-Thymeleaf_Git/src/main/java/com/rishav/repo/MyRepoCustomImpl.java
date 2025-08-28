package com.rishav.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rishav.dto.BookSummary;
import com.rishav.entity.Book;

import jakarta.persistence.EntityManager;

import org.hibernate.Session;

@Component
public class MyRepoCustomImpl implements MyRepoCustom{

	@Autowired
	EntityManager entityManager;
	@Override
	public List<Book> getBooks(String name) {
		Session session=entityManager.unwrap(Session.class);
		List<Book> books=session.createQuery("select b from Book b where b.name like :name",Book.class)
		.setParameter("name", "%"+name+"%")
		.list();
		
//		List<Book> books=ses.createQuery("select b from Book b where b.name like :name",Book.class)
//				.setParameter("name", "%"+name+"%")
//				.list();
		return books;
	}
	@Override
	public List<BookSummary> getAllBooksSummary() {
		Session session=entityManager.unwrap(Session.class);
		List<BookSummary> books=session.createQuery("SELECT new com.rishav.dto.BookSummary(b.name, b.aname, b.pname, b.price) FROM Book b", BookSummary.class).list();
		return books;
	}

}
