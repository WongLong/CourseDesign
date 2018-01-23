package Question_4.QueueShow;

import Question_4.SpaceLabel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QueueShow {
	public static void Show() {
		Stage primaryStage = new Stage();
		
		final MyQueue<Integer> queue = new MyQueue<>();

		final ScrollPane top = new ScrollPane();
		top.setPrefSize(700, 120);

		Label label = new Label("Enter a value:");
		label.setPrefHeight(40);
		final TextField text = new TextField();
		text.setPrefWidth(50);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefHeight(40);
		bp1.setCenter(text);

		Button enqueue = new Button("Enqueue");
		BorderPane bp2 = new BorderPane();
		bp2.setPrefHeight(40);
		bp2.setCenter(enqueue);

		Button dequeue = new Button("Dequeue");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefHeight(40);
		bp3.setCenter(dequeue);

		HBox bottom = new HBox();
		bottom.getChildren().addAll(new SpaceLabel(), label, new SpaceLabel(),
				bp1, new SpaceLabel(), bp2, new SpaceLabel(), bp3);

		VBox body = new VBox();
		body.getChildren().addAll(top, bottom);

		Scene scene = new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("队列");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		enqueue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Integer num = new Integer(text.getText());

					queue.enqueue(num);
					top.setContent(new QueuePane<Integer>(queue));
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}

				text.setText("");

			}
		});

		dequeue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (queue.isEmpty()) {
					Alert("该队列已空！！！");
					return;
				}

				queue.dequeue();
				top.setContent(new QueuePane<Integer>(queue));
			}
		});
	}

	// 消息提醒框
	public static Stage Alert(String str) {
		final Stage stage = new Stage();

		Label label = new Label(str);
		label.setPrefWidth(400);
		BorderPane bPane1 = new BorderPane();
		bPane1.setPrefSize(400, 200);
		bPane1.setCenter(label);

		Button bt = new Button("确定");
		bt.setPrefSize(100, 50);
		BorderPane bPane11 = new BorderPane();
		bPane11.setCenter(bt);

		bt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		VBox vbox = new VBox();
		vbox.getChildren().addAll(bPane1, bPane11);

		Scene scene = new Scene(vbox);
		stage.setTitle("消息");
		stage.setScene(scene);
		stage.show();

		return stage;
	}

}
