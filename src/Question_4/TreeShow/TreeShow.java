package Question_4.TreeShow;

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

public class TreeShow{
	public static void Show(){
		Stage primaryStage = new Stage();
		
		TreePane<Double> tPane = new TreePane<>(
				new BinarySearchTree<Double>());
		final ScrollPane head = new ScrollPane();
		head.setMinWidth(1000);
		head.setMinHeight(500);
		head.setContent(tPane);

		Label label = new Label("Enter a key:");
		label.setPrefHeight(40);
		final TextField text = new TextField();
		text.setPrefWidth(50);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefHeight(40);
		bp1.setCenter(text);

		Button search = new Button("Search");
		BorderPane bp2 = new BorderPane();
		bp2.setPrefHeight(40);
		bp2.setCenter(search);

		Button insert = new Button("Insert");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefHeight(40);
		bp3.setCenter(insert);

		Button remove = new Button("Remove");
		BorderPane bp4 = new BorderPane();
		bp4.setPrefHeight(40);
		bp4.setCenter(remove);

		HBox bottom = new HBox();
		bottom.getChildren().addAll(new SpaceLabel(), label, new SpaceLabel(),
				bp1, new SpaceLabel(), bp2, new SpaceLabel(), bp3,
				new SpaceLabel(), bp4);

		VBox body = new VBox();
		body.getChildren().addAll(head, bottom);

		Scene scene = new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BST");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Double num = new Double(text.getText());
					
					@SuppressWarnings("unchecked")
					TreePane<Double> tPane = (TreePane<Double>)head.getContent();
					tPane.search(num);
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}
				
				text.setText("");
			}
		});

		insert.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Double num = new Double(text.getText());
					
					@SuppressWarnings("unchecked")
					TreePane<Double> tPane = (TreePane<Double>)head.getContent();
					tPane.insert(num);
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}
				
				text.setText("");
			}
		});

		remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Double num = new Double(text.getText());
					
					@SuppressWarnings("unchecked")
					TreePane<Double> tPane = (TreePane<Double>)head.getContent();
					tPane.remove(num);
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}
				
				text.setText("");
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
