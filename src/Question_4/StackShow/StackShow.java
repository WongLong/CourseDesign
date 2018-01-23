package Question_4.StackShow;

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

public class StackShow{
	public static void Show(){
		Stage primaryStage = new Stage();
		
		final MyStack<Integer> stack = new MyStack<>();
		
		final ScrollPane left = new ScrollPane();
		left.setPrefSize(200, 600);

		Label label = new Label("Enter a valule: ");
		label.setPrefHeight(40);

		final TextField text = new TextField();
		text.setPrefWidth(50);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefHeight(40);
		bp1.setCenter(text);

		HBox head = new HBox();
		head.setPrefHeight(40);

		head.getChildren().addAll(new SpaceLabel(), label, new SpaceLabel(),
				bp1, new SpaceLabel());

		BorderPane buttonPane = new BorderPane();
		Button push = new Button("Push");
		BorderPane bp2 = new BorderPane();
		bp2.setPrefSize(70, 40);
		bp2.setCenter(push);

		Button pop = new Button("Pop");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefSize(70, 40);
		bp3.setCenter(pop);

		buttonPane.setLeft(bp2);
		buttonPane.setRight(bp3);

		VBox right = new VBox();
		right.getChildren().addAll(head, buttonPane);

		HBox body = new HBox();
		body.getChildren().addAll(left, right);

		Scene scene = new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("栈");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		push.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Integer num = new Integer(text.getText());
					stack.push(num);
					
					left.setContent(new StackPane<Integer>(stack));
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}

				text.setText("");
			}
		});
		
		pop.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				if(stack.isEmpty()){
					Alert("该栈已空！！！");
					return;
				}

				stack.pop();
				left.setContent(new StackPane<Integer>(stack));
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
