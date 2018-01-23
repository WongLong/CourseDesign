package Question_4.LinkedListShow;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class LinkedNodePane<E> extends HBox {

	public LinkedNodePane(E e) {
		Label label1 = new Label(e + "");
		label1.setAlignment(Pos.CENTER);
		label1.setFont(Font.font(30));
		label1.setMinWidth(60);
		label1.setPrefHeight(50);
		
		Label label2 = new Label("");
		label2.setPrefSize(50, 50);
		
		label1.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
		label2.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
		this.getChildren().addAll(label1, label2);
	}

}
