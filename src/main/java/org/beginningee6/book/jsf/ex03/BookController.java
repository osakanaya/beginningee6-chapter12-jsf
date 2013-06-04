package org.beginningee6.book.jsf.ex03;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.beginningee6.book.ejb.BookEJB;
import org.beginningee6.book.jpa.Book;

/**
 * 書籍情報を管理するマネージドBean。
 */
@ManagedBean
@RequestScoped
public class BookController {
	
	@EJB
	private BookEJB bookEJB;
	
	private Book book = new Book();
	private List<Book> bookList = new ArrayList<Book>();
	
	public String doCreateBook() {
		
		book = bookEJB.createBook(book);
		bookList = bookEJB.findBooks();
		
		return "listBooks.xhtml";
	}

	public Book getBook() {	
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
}
