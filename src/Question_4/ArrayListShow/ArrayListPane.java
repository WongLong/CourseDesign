package Question_4.ArrayListShow;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ArrayListPane<E> extends GridPane{
	public MyArrayList<E> list = null;
	
	public ArrayListPane(MyArrayList<E> list) {
		this.list = list;
		
		creatArrayListPane();
	}
	
	public void creatArrayListPane(){
		int capacity = list.getCapacity();
		int size = list.size();
		
		for(int i = 0; i < size; i++){
			Label label = new Label(list.get(i) + "");
			label.setAlignment(Pos.CENTER);
			label.setFont(Font.font(30));
			label.setPrefHeight(50);
			label.setMinWidth(60);
			this.add(label, i, 0);
		}
		
		for(int i = size; i < capacity; i++){
			Label label = new Label("X");
			label.setAlignment(Pos.CENTER);
			label.setFont(Font.font(30));
			label.setPrefHeight(50);
			label.setMinWidth(60);
			this.add(label, i, 0);
		}
		
		this.setGridLinesVisible(true);
	}
	
	public void search(int index){
		Label label = (Label) this.getChildren().get(index);
		label.setTextFill(Color.RED);
	}
}
