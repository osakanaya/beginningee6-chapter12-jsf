package org.beginningee6.book.jsf.ex04;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * カスタムバリデータのサンプル。
 * 
 * カスタムバリデータを実装するには、以下の3つのルールに従う必要がある。
 * 
 * １．＠FacesValidatorアノテーションによりバリデータであることを明示する。
 * ２．＠FacesValidatorアノテーションのvalue属性にバリデータID（バリデータ名）を
 * 　　指定して、f:validatorタグがバリデータIDを指定することで検証処理を利用
 * 　　できるようにする。
 * ３．javax.faces.validator.Validatorインタフェースを実装（implement）する。
 * 
 * Validatorインタフェースの実装に当たっては、以下の1つのメソッドを実装する
 * 必要がある。
 * 
 * ・void validate(FacesContext, UIComponent, Object)
 * 
 * 第3引数のObject型の値には、コンバータにより対応するマネージドBean内の
 * プロパティの型に変換されたUIコンポーネントでの入力値が与えられる。
 * 
 * 実装する検証処理で入力エラーがあると判断した場合は、以下の手順で
 * エラーがあることをJSFフレームワークに通知する。
 * 
 * １．表示するエラーメッセージのFacesMessageオブジェクトを作成する。
 * ２．作成したFacesMessageオブジェクトを引数にしてValidatorException
 * 　　をスローする。
 * 
 * org.beginningee6.book.jsf.ex01.BookControllerWithMessageでは、
 * マネージドBeanの内部で入力チェックの処理、エラーメッセージの生成、
 * エラー発生時の画面遷移先の決定を実装していたが、標準バリデータや
 * ここで作成するようなカスタムバリデータを使用する場合は、このような
 * 実装は必要ない。
 * 
 * 標準バリデータやカスタムバリデータでValidatorExceptionのスローが行われると、
 * フォームをサブミットしたページが再度表示されるようになる。
 */
@FacesValidator(value = "isbnValidator")	// カスタムバリデータであることを宣言
											// バリデータIDをisbnValidatorに設定
public class IsbnValidator implements Validator {
								// javax.faces.validator.Validatorを実装する

	private Pattern pattern;
	private Matcher matcher;

	/**
	 * 画面に入力された値を検証する
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		// String型に変換する
		String componentValue = value.toString();

		// ISBN番号として正しい文字列のパターンを規定する
		// ここでは、半角の-（マイナス）、0～9、x、X
		// で構成された13ケタの文字列を正しいISBNとして
		// 規定している
		pattern = Pattern.compile("(?=[-0-9xX]{13}$)");
		// 入力文字列のパターンマッチを行う
		matcher = pattern.matcher(componentValue);

		if (!matcher.find()) {
			// パターンマッチに失敗したら、エラーメッセージを作成し、
			// メッセージとともにValidatorExceptionをスローする。
			String message = MessageFormat.format(
					"{0} is not a valid isbn format", componentValue);
			FacesMessage facesMessage 
				= new FacesMessage(
					FacesMessage.SEVERITY_ERROR, 	// メッセージの重大度
					message, 						// メッセージ（サマリ）
					message							// メッセージ（詳細）
				);
			
			throw new ValidatorException(facesMessage);
		}
	}
}