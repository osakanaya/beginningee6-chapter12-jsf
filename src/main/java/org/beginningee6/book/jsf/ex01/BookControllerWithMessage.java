package org.beginningee6.book.jsf.ex01;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.beginningee6.book.ejb.BookEJB;
import org.beginningee6.book.jpa.Book;

/**
 * 書籍情報を管理するマネージドBean。
 * バリデータを使用せずに入力チェックを行い、入力エラーによるエラーメッセージの
 * 表示方法を示すサンプルとなっている。
 * 
 * アクションメソッドdoCreateBook()が呼び出されると、書籍登録画面に入力された
 * 各コンポーネントの入力内容がFacesServletを介して、Bookエンティティ
 * の各プロパティにバインドされる。
 * 
 * doCreateBook()内では書籍のISBN番号およびタイトルに対する入力チェックが
 * 行われ、少なくともどちらか一方の入力が無ければ入力エラーがあるものと
 * みなし、未入力の入力フィールド（h:inputTextタグ）に紐づけられたメッセージ
 * 表示コンポーネント（h:messageタグ）に警告メッセージを表示する。
 * 
 * h:messageタグへのエラーメッセージの表示は、1つのエラーメッセージを表す
 * FacesMessageオブジェクトを引数に指定してFacesContext.addMessage()を
 * 実行することにより行う。
 * 
 * 入力エラーがあった場合は、doCreateBook()はnullを返す。JSFでは、アクション
 * メソッドでnullが返却された場合、フォームをサブミットしたページを再度表示する
 * ようになっているため、書籍登録画面が再度表示されることになる。この時に
 * FacesContext.addMessage()により登録したエラーメッセージも併せて画面に
 * 表示される。
 * 
 * 書籍のISBN番号およびタイトルの入力チェックが完了した後は、Bookエンティティの
 * 永続化が行われる。永続化で例外がスローされた場合は、入力チェックと同様に
 * FacesContext.addMessage()によるエラーメッセージの登録とnullの返却が行われ、
 * 書籍登録画面がエラーメッセージとともに表示される。
 * 
 * 書籍の永続化が正常終了した場合は、"listBooks.xhtml"が返却され、書籍一覧画面が
 * 表示される。
 */
@ManagedBean
@RequestScoped
public class BookControllerWithMessage {

	@EJB
	private BookEJB bookEJB;

	private Book book = new Book();
	private List<Book> bookList = new ArrayList<Book>();

	public String doCreateBook() {

		// FacesContextを取得
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 書籍のISBN番号が未入力の場合は、エラーメッセージを表示する
		if (book.getIsbn() == null || "".equals(book.getIsbn())) {
			// FacesContext.addMessage()によりエラーメッセージを登録する
			ctx.addMessage(
					"bookForm:isbn", 	// エラーメッセージの指す入力コンポーネント。
										// この入力コンポーネントのidの値がfor属性が
										// 設定されたh:messageタグでエラーメッセージが表示される。
										//
										// h:formタグ内の入力コンポーネントの場合、
										// （h:formタグのid）:（入力コンポーネントのid）となる。
										// 
										// nullを指定した場合は、h:messagesタグの出現箇所に
										// エラーメッセージが表示される
					new FacesMessage(							// 登録するエラーメッセージ
							FacesMessage.SEVERITY_WARN, 		// エラーの重大度
							"Wrong isbn",						// エラーのサマリ
							"You should enter an ISBN number"	// エラーの詳細
					)
			);
		}

		// 書籍のタイトルが未入力の場合は、エラーメッセージを表示する
		if (book.getTitle() == null || "".equals(book.getTitle())) {
			// FacesContext.addMessage()によりエラーメッセージを登録する
			ctx.addMessage(
					"bookForm:title", 
					new FacesMessage(
							FacesMessage.SEVERITY_WARN, 
							"Wrong title",
							"You should enter a title for the book"
					)
			);
		}

		// エラーメッセージが追加されていればnullを返すことによって、
		// 遷移元のページ（書籍登録画面：newBook.xhtml）を再度表示する。
		if (ctx.getMessageList().size() != 0)
			return null;

		try {
			// Bookエンティティを永続化
			book = bookEJB.createBook(book);
			
			// 永続化されている全てのBookエンティティのリストを取得
			bookList = bookEJB.findBooks();

		} catch (Exception e) {
			// 例外がスローされた場合はnullを返すことによって、
			// 遷移元のページ（書籍登録画面：newBook.xhtml）を再度表示する。
			ctx.addMessage(
					null, 	// nullを指定したため、h:messagesタグへ
							// エラーメッセージを表示する
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Book hasn't been created", 
							e.getMessage()
					)
			);

			return null;
		}

		// Bookエンティティの永続化が正常終了したら、書籍一覧画面（listBooks.xhtml）へ
		// 遷移する
		return "listBooks.xhtml";
	}

	/**
	 * bookプロパティの取得
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * bookプロパティの設定
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * bookListプロパティの取得
	 */
	public List<Book> getBookList() {
		return bookList;
	}

	/**
	 * bookListプロパティの設定
	 */
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
}
