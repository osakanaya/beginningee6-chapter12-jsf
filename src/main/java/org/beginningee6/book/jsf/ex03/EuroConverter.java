package org.beginningee6.book.jsf.ex03;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.DecimalFormat;

/**
 * カスタムコンバータのサンプル。
 * 
 * カスタムコンバータを実装するには、以下の3つのルールに従う必要がある。
 * 
 * １．＠FacesConverterアノテーションによりコンバータであることを明示する。
 * ２．＠FacesConverterアノテーションのvalue属性にコンバータID（コンバータ名）を
 * 　　指定して、f:converterタグがコンバータIDを指定することで変換処理を利用
 * 　　できるようにする。
 * ３．javax.faces.convert.Converterインタフェースを実装（implement）する。
 * 
 * Converterインタフェースの実装に当たっては、以下の2つのメソッドを実装する
 * 必要がある。
 * 
 * ・Object getAsObject(FacesContext, UIComponent, String)
 * 
 * フォームをサブミットした時に使用され、ページ内のUIコンポーネントの文字列
 * （入力文字列）を、対応する任意のオブジェクト型で宣言されたマネージドBean内の
 * プロパティの値に変換する処理を実装する必要がある。
 * 
 * ・String getAsString(FacesContext, UIComponent, value)
 * 
 * マネージドBeanのプロパティが持つデータで画面を表示する時に使用され、
 * 任意のオブジェクト型で宣言されたマネージBean内のプロパティの値をページ内の
 * 対応するUIコンポーネントが値として必要とする文字列として変換する処理を
 * 実装する必要がある。
 */
@FacesConverter(value = "euroConverter")	// カスタムコンバータであることを宣言
											// コンバータIDをeuroConverterに設定
public class EuroConverter implements Converter {
										// javax.faces.convert.Converterを実装する

	/**
	 * ページ内のUIコンポーネントが値として持つ文字列を、任意のオブジェクト型として
	 * 宣言されたマネージドBean内のプロパティの値に変換する際に呼び出される。
	 * 
	 * このサンプルでは、入力フォームに入力した文字列をマネージドBean内の
	 * プロパティとして変換する使い方はしないので、このメソッドの実装は
	 * 実質的に不要である。このため、引数valueで受け取った値を変換後の値として
	 * そのまま返している。
	 */
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

	/**
	 * マネージドBean内のプロパティの値を、ページ内の対応するUIコンポーネントの値として
	 * 文字列へ変換する際に呼び出される。
	 */
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
    	
    	// 文字列を浮動小数点型に変換
        float amountInDollars = Float.parseFloat(value.toString());
        
        // ドルからユーロへの返還で0.8を掛ける
        double ammountInEuros = amountInDollars * 0.8;
        
        // 数値をフォーマットして文字列を返す
        DecimalFormat df = new DecimalFormat("###,##0.##");
        return df.format(ammountInEuros);
    }
}