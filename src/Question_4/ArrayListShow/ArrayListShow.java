package Question_4.ArrayListShow;

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

public class ArrayListShow {
	static MyArrayList<Integer> list = new MyArrayList<>();

	public static void Show() {
		Stage primaryStage = new Stage();
		
		final Label label = new Label("array list is empty");
		label.setPrefHeight(40);
		BorderPane head = new BorderPane();
		head.setLeft(label);

		final ScrollPane middle = new ScrollPane();
		middle.setPrefSize(800, 100);
		ArrayListPane<Integer> listPane = new ArrayListPane<>(list);
		middle.setContent(listPane);

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

		Button search = new Button("Search");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefHeight(40);
		bp3.setCenter(search);

		Button insert = new Button("Insert");
		BorderPane bp4 = new BorderPane();
		bp4.setPrefHeight(40);
		bp4.setCenter(insert);

		Button delete = new Button("Delete");
		BorderPane bp5 = new BorderPane();
		bp5.setPrefHeight(40);
		bp5.setCenter(delete);

		Button trimToSize = new Button("TrimToSize");
		BorderPane bp6 = new BorderPane();
		bp6.setPrefHeight(40);
		bp6.setCenter(trimToSize);

		Button add = new Button("Add");
		BorderPane bp7 = new BorderPane();
		bp7.setPrefHeight(40);
		bp7.setCenter(add);

		HBox bottom = new HBox();
		bottom.getChildren().addAll(label1, new SpaceLabel(), bp1,
				new SpaceLabel(), label2, new SpaceLabel(), bp2,
				new SpaceLabel(), bp7, new SpaceLabel(), bp3, new SpaceLabel(),
				bp4, new SpaceLabel(), bp5, new SpaceLabel(), bp6);

		VBox body = new VBox();
		body.getChildren().addAll(head, new SpaceLabel(), middle, bottom);

		Scene scene = new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("线性表");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String str = text1.getText();
				try {
					Integer num = new Integer(str);
					list.add(num);
					
					if(list.isEmpty())
						label.setText("array list is empty");
					else
						label.setText("array list size = " + list.size() + " and capacity is " + list.getCapacity());
					middle.setContent(new ArrayListPane<>(list));
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
				middle.setContent(new ArrayListPane<>(list));
				try {
					Integer num = new Integer(text1.getText());
					@SuppressWarnings("unchecked")
					ArrayListPane<Integer> listPane = (ArrayListPane<Integer>) middle
							.getContent();
					int index = listPane.list.indexOf(num);

					if (index < 0)
						throw new IndexOutOfBoundsException();

					listPane.search(index);
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					try {
						Integer index = new Integer(text2.getText());
						@SuppressWarnings("unchecked")
						ArrayListPane<Integer> listPane = (ArrayListPane<Integer>) middle
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
					middle.setContent(new ArrayListPane<>(list));
					
				} catch (NumberFormatException e) {
					Alert("输入有误！！！");
					return;
				}
				
				if(list.isEmpty())
					label.setText("array list is empty");
				else
					label.setText("array list size = " + list.size() + " and capacity is " + list.getCapacity());
				
				text1.setText("");
				text2.setText("");
			}
		});

		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Integer num = new Integer(text1.getText());
					
					if(!list.remove(num))
						Alert("该数字不存在，删除失败！！！");
					
					middle.setContent(new ArrayListPane<>(list));
				} catch (NumberFormatException e) {
					try {
						Integer index = new Integer(text2.getText());
						
						if(index < 0 || index >= list.size()){
							Alert("下标输入错误！！！");
							return;
						}
						
						list.removeIndex(index);
						middle.setContent(new ArrayListPane<>(list));
					} catch (NumberFormatException e1) {
						Alert("输入有误！！！");
						return;
					}
				}
				
				if(list.isEmpty())
					label.setText("array list is empty");
				else
					label.setText("array list size = " + list.size() + " and capacity is " + list.getCapacity());
				
				text1.setText("");
				text2.setText("");
			}
		});

		trimToSize.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				list.trimToSize();
				middle.setContent(new ArrayListPane<>(list));
				
				if(list.isEmpty())
					label.setText("array list is empty");
				else
					label.setText("array list size = " + list.size() + " and capacity is " + list.getCapacity());
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
