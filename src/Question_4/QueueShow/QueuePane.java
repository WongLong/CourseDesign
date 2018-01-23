package Question_4.QueueShow;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class QueuePane<E> extends GridPane{
	MyQueue<E> queue = new MyQueue<>();
	
	public QueuePane(MyQueue<E> queue) {
		this.queue = queue;

		creatQueuePane();
	}

	private void creatQueuePane(){
		MyQueue<E> temp = this.queue.clone();
		int index = 0;
		while(!temp.isEmpty()){
			Label label = new Label(temp.dequeue() + "");
			label.setAlignment(Pos.CENTER);
			label.setFont(Font.font(30));
			label.setPrefHeight(50);
			label.setMinWidth(80);
			label.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
			this.add(label, index, 1);
			index++;
		}
		
		if(queue.getSize() == 1){
			Label label = new Label("head(tail)");
			label.setAlignment(Pos.CENTER);
			label.setPrefSize(80, 50);
			this.add(label, 0, 0);
		}else if(queue.getSize() > 1){
			Label head = new Label("head");
			head.setAlignment(Pos.CENTER);
			Label tail = new Label("tail");
			tail.setAlignment(Pos.CENTER);
			
			head.setPrefSize(80, 50);
			tail.setPrefSize(80, 50);
			
			this.add(head, 0, 0);
			this.add(tail, queue.getSize() - 1, 0);
		}
	}
}
