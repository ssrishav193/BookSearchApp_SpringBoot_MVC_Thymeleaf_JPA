package com.rishav.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.rishav.dto.BookSummary;
//import com.rishav.BookAppMvcJpaThymeleafApplication;
import com.rishav.entity.Book;
import com.rishav.entity.Login;
import com.rishav.service.MyService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class MyController {

 
	@Autowired
	MyService myService;

	
	@PostMapping("/SearchBooks")
	public String searchBooks(@RequestParam String name, ModelMap m) {  //@RequestParam -> used to store value coming in request parameter (jo servlet me request.getParameter() se karn padta tha)
		List<Book> books=myService.getBooks(name);
		
		if(books.isEmpty()) {
			m.addAttribute("msg", "Book Not Found!");
			return "index.html";
		}else {
			m.addAttribute("books", books);
			return "BookDetails.html";
		}
		
	}
	
	@GetMapping("/getImage")
	public void getImage(@RequestParam String name, HttpServletResponse response) throws IOException {  //@RequestParam -> used to store value coming in request parameter (jo servlet me request.getParameter() se karn padta tha)
		Book book=myService.getBook(name);
		byte[] image=book.getImage();
		
		if(image==null || image.length==0) {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/book.png");
			image=is.readAllBytes();	
		}
		response.getOutputStream().write(image);
		
	}
	
	@GetMapping("/admin-login")
	public String adminLogin() {
		return "admin-login";
	}
	
	
	
	@PostMapping("/Login")
	//public String login(@RequestParam String id, @RequestParam String password) {
	public String login(@ModelAttribute Login login, ModelMap m, HttpSession session) {
		String name=myService.checkLogin(login);
		if(name==null) {
			m.addAttribute("msg", "Invalid Credentials!");
			return "admin-login";
		}else {
			session.setAttribute("name", name);
			return "admin-home";
			
		}
		
	}
	
	@GetMapping("/admin-home")
	public String adminHome(HttpSession session, ModelMap m) {
		String name=(String)session.getAttribute("name");
		if(name==null) {
			m.addAttribute("msg", "Please Login First!");
			return "admin-login";
		}
		return "admin-home";
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "admin-login";
		
	}
	
//	Why can't we get image or pdf (as byte[]) directly inside a @ModelAttribute Book?
/*
Spring's @ModelAttribute mechanism binds form fields to Java object properties only when the field values are plain text (like String, int, etc.).
But in our case:
	-We’re uploading files, not plain text.
	-MultipartFile is not the same as byte[].
	-Spring doesn't automatically convert a file upload into a byte[] for a property like private byte[] image; in our Book class.
*/
	
	
//	@PostMapping("AddBook")
//	public String addBook(@RequestParam String name, @RequestParam String aname, @RequestParam String pname, @RequestParam int price, @RequestPart MultipartFile image, ModelMap m) throws IOException {
//		Book book = new Book();
//		book.setName(name);
//		book.setAname(aname);
//		book.setPname(pname);
//		book.setPrice(price);
//		book.setImage(image.getBytes());
//		boolean result=myService.addBook(book);
//		if(result) {
//			m.addAttribute("msg", "Book Added Successfully!");
//		}else {
//			m.addAttribute("msg", "Book Already Exist!");
//		}
//	
//		return "admin-home";
//	}
	
	@PostMapping("AddBook")
	public String addBook(@ModelAttribute Book book, @RequestPart("imageFile") MultipartFile image, @RequestPart("contentFile") MultipartFile content, ModelMap m) throws IOException {
		
		book.setImage(image.getBytes());
		book.setContent(content.getBytes());
		if(content.getBytes()!=null && content.getBytes().length!=0) {
			book.setHasContent(true);
		}
		boolean result=myService.addBook(book);
		if(result) {
			m.addAttribute("msg", "Book Added Successfully!");
		}else {
			m.addAttribute("msg", "Book Already Exist!");
		}
	
		return "admin-home";
	}
	
	@GetMapping("all-books")
	public String allBooks(HttpSession session, ModelMap m) {
		String name=(String)session.getAttribute("name");
		if(name==null) {
			m.addAttribute("msg", "Please Login First!");
			return "admin-login";
		}
		List<Book> books=myService.getAllBooks();
		m.addAttribute("books", books);
		return "all-books";
	}
	
	@GetMapping("all-books2")
	public String allBooks2(HttpSession session, ModelMap m) {
		String name=(String)session.getAttribute("name");
		if(name==null) {
			m.addAttribute("msg", "Please Login First!");
			return "admin-login";
		}
		List<BookSummary> books=myService.getAllBooksSummary();
		m.addAttribute("books", books);
		return "all-books2";
	}
	
	@GetMapping("all-books3")
	public String allBooks3(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="3") int size, HttpSession session, ModelMap m) {
		String name=(String)session.getAttribute("name");
		if(name==null) {
			m.addAttribute("msg", "Please Login First!");
			return "admin-login";
		}
		Page<Book> bookPage=myService.getBooksPage(page, size);
		
		m.addAttribute("books", bookPage.getContent());
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", bookPage.getTotalPages());
		return "all-books3";
	}
	
	@GetMapping("/viewPDF")
	public void viewPDF(@RequestParam String name, HttpServletResponse response) throws IOException {
		Book book=myService.getBook(name);
		byte[] pdf=book.getContent();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=" + name + ".pdf");
		response.getOutputStream().write(pdf);
		
	}
	
	@GetMapping("/downloadPDF")
	public void downloadPDF(@RequestParam String name, HttpServletResponse response) throws IOException {
		Book book=myService.getBook(name);
		byte[] pdf=book.getContent();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + name + ".pdf");
		response.getOutputStream().write(pdf);
		
	}
	
	@GetMapping("/deleteBook")
	public String deleteBook(@RequestParam String name, ModelMap m) {

	    boolean result=myService.deleteBook(name);
	    if(result) {
	    	m.addAttribute("msg", "Book deleted successfully!");
	    }else {
	    	m.addAttribute("msg", "Book not found!");
	    }
	    List<Book> books = myService.getAllBooks();
	    m.addAttribute("books", books);
	    return "all-books";
	}
	
	@GetMapping("/editBook")
	public String editBook(@RequestParam String name, ModelMap m, HttpSession session) {
	    String adminName = (String) session.getAttribute("name");
	    if (adminName == null) {
	        m.addAttribute("msg", "Please Login First!");
	        return "admin-login";
	    }

	    Book book = myService.getBook(name);
	    m.addAttribute("book", book);
	    return "edit-book";
	}


	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute Book book,
	                         @RequestParam("imageFile") MultipartFile imageFile,
	                         @RequestParam("contentFile") MultipartFile contentFile,
	                         ModelMap m) {
	    try {
	        Book existing = myService.getBook(book.getName());
	        if (existing == null) {
	            m.addAttribute("msg", "Book not found!");
	            return "edit-book";
	        }

	        existing.setAname(book.getAname());
	        existing.setPname(book.getPname());
	        existing.setPrice(book.getPrice());

	        if (!imageFile.isEmpty()) {
	            existing.setImage(imageFile.getBytes());
	        }
	        if (!contentFile.isEmpty()) {
	            existing.setContent(contentFile.getBytes());
	            existing.setHasContent(true);
	        }

	        myService.updateBook(existing); // call service to save it

	        m.addAttribute("book", existing);
	        m.addAttribute("msg", "Book updated successfully!");
	        return "edit-book";

	    } catch (Exception e) {
	        m.addAttribute("msg", "Error while updating book: " + e.getMessage());
	        return "edit-book";
	    }
	}





}
