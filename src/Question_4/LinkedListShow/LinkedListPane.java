package  Question_4.LinkedListShow;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class LinkedListPane<E> extends GridPane{
	public MyLinkedList<E> list = new MyLinkedList<>();
	
	public LinkedListPane(MyLinkedList<E> list){
		this.list = list;
		
		creatLinkedListPane();
	}
		
	
	public void creatLinkedListPane(){
		for(int i = 0; i < list.size(); i++){
			LinkedNodePane<E> node = new LinkedNodePane<>(list.get(i));
			this.add(node, i * 2, 1);
			
			Label label = new Label("----->");
			label.setAlignment(Pos.CENTER);
			label.setPrefSize(50, 50);
			
			if(i != list.size() - 1)
				this.add(label, i * 2 + 1, 1);
		}
		
		if(list.size() == 1){
			Label label = new Label("head(tail)");
			label.setAlignment(Pos.CENTER);
			label.setPrefSize(110, 50);
			this.add(label, 0, 0);
		}else if(list.size() > 1){
			Label head = new Label("head");
			head.setAlignment(Pos.CENTER);
			Label tail = new Label("tail");
			tail.setAlignment(Pos.CENTER);
			
			head.setPrefSize(110, 50);
			tail.setPrefSize(110, 50);
			
			this.add(head, 0, 0);
			this.add(tail, list.size() * 2 - 2, 0);
		}
			
	}
	
	public void search(int index){
		HBox node = (HBox) this.getChildren().get(index * 2);
		Label label = (Label)node.getChildren().get(0);
		label.setTextFill(Color.RED);
	}
}
