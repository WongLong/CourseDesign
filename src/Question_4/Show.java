package Question_4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Question_1.newBt;
import Question_4.ArrayListShow.ArrayListShow;
import Question_4.GraphShow.GraphShow;
import Question_4.LinkedListShow.LinkedListShow;
import Question_4.QueueShow.QueueShow;
import Question_4.StackShow.StackShow;
import Question_4.TreeShow.TreeShow;

public class Show{
	public static void start() {
		Stage primaryStage = new Stage();
		
		Label label = new Label("���Ա�����ͼ�Ĳ�������ʾ");
		label.setPrefHeight(100);
		label.setStyle("-fx-font-size: 20px;-fx-font-weight: 700");
		newBt bt1 = new newBt("���Ա�");
		newBt bt2 = new newBt("����");
		newBt bt3 = new newBt("ջ");
		newBt bt4 = new newBt("����");
		newBt bt5 = new newBt("���������");
		newBt bt6 = new newBt("ͼ");
		
		BorderPane bp = new BorderPane();
		bp.setCenter(label);
		
		HBox hb1 = new HBox();
		hb1.getChildren().addAll(bt1, bt2);
		HBox hb2 = new HBox();
		hb2.getChildren().addAll(bt3, bt4);
		HBox hb3 = new HBox();
		hb3.getChildren().addAll(bt5, bt6);
		
		VBox body = new VBox();
		body.getChildren().addAll(bp, hb1, hb2, hb3);
		
		Scene scene = new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("���Ա�����ͼ");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();
		
		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ArrayListShow.Show();
			}
		});
		
		bt2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				LinkedListShow.Show();
			}
		});
		
		bt3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				StackShow.Show();
			}
		});
		
		bt4.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				QueueShow.Show();
			}
		});
		
		bt5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				TreeShow.Show();
			}
		});
		
		bt6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GraphShow.Show();	
			}
		});
	}

}
