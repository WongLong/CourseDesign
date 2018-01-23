package Question_1;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class newBt extends Button {

	public newBt() {
		
	}

	public newBt(String arg0) {
		super(arg0);
		this.setPrefSize(200, 150);
		this.setFont(Font.font("Times New Roman", FontWeight.LIGHT, FontPosture.ITALIC,20));
	}

	public newBt(String arg0, Node arg1) {
		super(arg0, arg1);
		this.setPrefSize(200, 150);
		this.setFont(Font.font("Times New Roman", FontWeight.LIGHT, FontPosture.ITALIC,20));
	}
}
