package org.beginningee6.book.jsf.ex05;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.beginningee6.book.ejb.BookEJB;
import org.beginningee6.book.jpa.Book;

/**
 * 書籍情報を管理するマネージドBean。
 * 
 * Ajaxの仕組みを利用して、同一HTMLページ内に
 * 動的にアクションメソッドの実行結果を表示する例を
 * 示している。
 */
@ManagedBean
@RequestScoped
public class BookControllerWithAjax {
	
	@EJB
	private BookEJB bookEJB;
	
	private Book book = new Book();
	private List<Book> bookList = new ArrayList<Book>();
	
	public String doCreateBook() {
		
		book = bookEJB.createBook(book);
		bookList = bookEJB.findBooks();
		
		// Ajaxの仕組みを利用して、サブミットした
		// 書籍登録画面内に上記のBookエンティティの永続化を
		// 反映した形で書籍の一覧を表示ようにするため、
		// 元のページである書籍登録画面を遷移先として
		// 指定する。
		return "newBook.xhtml";
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
