package Question_4.LinkedListShow;

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

public class LinkedListShow{
	static MyLinkedList<Integer> list = new MyLinkedList<>();

	public static void Show() {
		Stage primaryStage = new Stage();
		
		final ScrollPane content = new ScrollPane();
		content.setPrefSize(800, 200);

		Label label1 = new Label("Enter a value:");
		label1.setPrefHeight(40);
		final TextField text1 = new TextField();
		text1.setPrefWidth(50);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefHeight(40);
		bp1.setCenter(text1);

		Label label2 = new Label("Enter an index:");
		label2.setPrefHeight(40);
		final TextField text2 = new TextField();
		text2.setPrefWidth(50);
		BorderPane bp2 = new BorderPane();
		bp2.setPrefHeight(40);
		bp2.setCenter(text2);

		Button add = new Button("Add");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefHeight(40);
		bp3.setCenter(add);

		Button search = new Button("Search");
		BorderPane bp4 = new BorderPane();
		bp4.setPrefHeight(40);
		bp4.setCenter(search);

		Button insert = new Button("Insert");
		BorderPane bp5 = new BorderPane();
		bp5.setPrefHeight(40);
		bp5.setCenter(insert);

		Button delete = new Button("Delete");
		BorderPane bp6 = new BorderPane();
		bp6.setPrefHeight(40);
		bp6.setCenter(delete);

		HBox bottom = new HBox();
		bottom.getChildren().addAll(label1, new SpaceLabel(), bp1,
				new SpaceLabel(), label2, new SpaceLabel(), bp2,
				new SpaceLabel(), bp3, new SpaceLabel(), bp4, new SpaceLabel(),
				bp5, new SpaceLabel(), bp6);

		VBox body = new VBox();
		body.getChildren().addAll(content, bottom);

		Scene scene = new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("链表");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Integer num = new Integer(text1.getText());
					list.add(num);
					content.setContent(new LinkedListPane<>(list));
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}

				text1.setText("");
				text2.setText("");
			}
		});

		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				content.setContent(new LinkedListPane<>(list));
				try {
					Integer num = new Integer(text1.getText());
					@SuppressWarnings("unchecked")
					LinkedListPane<Integer> listPane = (LinkedListPane<Integer>) content
							.getContent();
					int index = list.indexOf(num);

					if (index < 0)
						throw new IndexOutOfBoundsException();

					listPane.search(index);
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					try {
						Integer index = new Integer(text2.getText());
						@SuppressWarnings("unchecked")
						LinkedListPane<Integer> listPane = (LinkedListPane<Integer>) content
								.getContent();

						if (index >= listPane.list.size() || index < 0) {
							Alert("下标输入错误！！！");
							return;
						}

						listPane.search(index);
					} catch (NumberFormatException e1) {
						Alert("输入有误！！！");
						return;
					}
				}
				
				text1.setText("");
				text2.setText("");
			}
		});

		insert.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Integer num = new Integer(text1.getText());
					Integer index = new Integer(text2.getText());
					
					list.add(index, num);
					content.setContent(new LinkedListPane<>(list));
					
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}
				
				text1.setText("");
				text2.setText("");
			}
		});

		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Integer num = new Integer(text1.getText());
					int index = list.indexOf(num);

					if (index < 0)
						throw new IndexOutOfBoundsException();

					list.remove(index);
					content.setContent(new LinkedListPane<>(list));
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					try {
						int index = new Integer(text2.getText());

						if (index >= list.size() || index < 0) {
							Alert("下标输入错误！！！");
							return;
						}

						list.remove(index);
						content.setContent(new LinkedListPane<>(list));
					} catch (NumberFormatException e1) {
						Alert("输入有误！！！");
						return;
					}
				}
				
				text1.setText("");
				text2.setText("");
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
