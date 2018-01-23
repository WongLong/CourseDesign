package main;

import Question_1.newBt;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author 10527
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Label label = new Label("�γ����");
		label.setPrefHeight(100);
		label.setStyle("-fx-font-size: 20px;-fx-font-weight: 700");
		newBt bt1 = new newBt("����ѹ�����ѹ��");
		newBt bt2 = new newBt("24���˿�����Ϸ");
		newBt bt3 = new newBt("16öӲ��");
		newBt bt4 = new newBt("���Ա�����ͼ");
		newBt bt5 = new newBt("ũ�����");
		newBt bt6 = new newBt("�Թ�����");

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
		primaryStage.setTitle("�γ����");
		primaryStage.show();

		bt1.setOnAction(e -> {
			Question_1.TestHFMTree.start();
		});

		bt2.setOnAction(e -> {
			Question_2.Versions_2.start();
		});

		bt3.setOnAction(e -> {
				Question_3.Versions_2.start();
		});

		bt4.setOnAction(e -> {
				Question_4.Show.start();
		});

		bt5.setOnAction(e -> {
				Question_5.Test.start();
		});

		bt6.setOnAction(e ->{
				Question_6.Test.start();
		});
	}

	public static void main(String args[]) {
		Application.launch(args);
	}

}
