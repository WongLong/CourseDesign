package Question_4.StackShow;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class StackPane<E> extends GridPane {
	MyStack<E> stack = new MyStack<>();
	
	public StackPane(MyStack<E> stack){
		this.stack = stack;
		
		creatStackPane();
	}
	
	private void creatStackPane(){
		if(!stack.isEmpty()){
			ArrayList<E> temp = new ArrayList<>();
			MyStack<E> newStack = stack.clone();
			
			while(!newStack.isEmpty())
				temp.add(newStack.pop());
			
			for(int i = 0; i < temp.size(); i++){
				Label label = new Label(temp.get(i) + "");
				label.setMinWidth(100);
				label.setPrefHeight(50);
				
				label.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
				label.setAlignment(Pos.CENTER);
				label.setFont(Font.font(30));
				this.add(label, 1, i);
			}
			
			Label top = new Label("top");
			top.setAlignment(Pos.CENTER);
			top.setPrefSize(70, 50);
			top.setFont(Font.font(20));
			
			this.add(top, 0, 0);
		}
	}
	
}
