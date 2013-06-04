package org.beginningee6.book.jsf.ex02;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.beginningee6.book.ejb.BookEJB;
import org.beginningee6.book.jpa.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 書籍情報を管理するマネージドBean。
 */
@ManagedBean
@RequestScoped
public class BookControllerWithConverter {

    @EJB
    private BookEJB bookEJB;

    private Book book = new Book();
    private List<Book> bookList = new ArrayList<Book>();

    // 日付型のフィールドを持つ
    private Date publishedDate = new Date();
    // 数値型（価格表現に使用）を持つ
    private Number publishedPrice = new Integer(777);

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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Number getPublishedPrice() {
        return publishedPrice;
    }

    public void setPublishedPrice(Number publishedPrice) {
        this.publishedPrice = publishedPrice;
    }
}