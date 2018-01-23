package Question_5;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Test{
	public static void start(){
		Stage primaryStage = new Stage();
		
		Model model = new Model();
		final List<List<Integer>> allPath = model.getAllPath();
		final int[] index = { 0 };

		final Button start = new Button("开始");
		start.setPrefHeight(40);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefSize(600, 40);
		bp1.setCenter(start);

		Button checkTable = new Button("查看表格");
		checkTable.setPrefHeight(40);
		BorderPane bp2 = new BorderPane();
		bp2.setPrefSize(600, 40);
		bp2.setCenter(checkTable);

		HBox top = new HBox();
		top.setPrefHeight(50);
		top.getChildren().addAll(bp1, bp2);

		BorderPane head = new BorderPane();
		final Button last = new Button("<");
		last.setPrefSize(40, 50);
		final Button next = new Button(">");
		next.setPrefSize(40, 50);
		head.setLeft(last);
		head.setRight(next);
		
		last.setDisable(true);
		if(allPath.size() == 1)
			next.setDisable(true);

		final Pane pane = new Pane();
		pane.getChildren().add(new AnimationPane(allPath.get(index[0])));
		VBox body = new VBox();
		body.getChildren().addAll(head, top, pane);

		Scene scene = new Scene(body);
		primaryStage.setTitle("农夫过河");
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				start.setDisable(true);
				AnimationPane animationPane = (AnimationPane) pane
						.getChildren().get(0);
				animationPane.startAnimation();
			}
		});

		checkTable.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				AnimationPane animationPane = (AnimationPane) pane
						.getChildren().get(0);
				createTable(animationPane.path);
			}
		});

		last.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				start.setDisable(false);
				index[0]--;
				pane.getChildren().clear();
				pane.getChildren().add(new AnimationPane(allPath.get(index[0])));
				
				if(index[0] == 0)
					last.setDisable(true);
				next.setDisable(false);
			}
		});

		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				start.setDisable(false);
				index[0]++;
				pane.getChildren().clear();
				pane.getChildren().add(new AnimationPane(allPath.get(index[0])));
				
				if(index[0] == allPath.size() - 1)
					next.setDisable(true);
				last.setDisable(false);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static void createTable(List<Integer> path) {
		Stage stage = new Stage();

		TableView<PathAndPos> table = new TableView<>();
		TableColumn<PathAndPos, String> column1 = new TableColumn<>("步骤");
		column1.setMinWidth(100);
		TableColumn<PathAndPos, String> column2 = new TableColumn<>("状态");
		column2.setMinWidth(400);
		TableColumn<PathAndPos, String> column3 = new TableColumn<>("南岸");
		column3.setMinWidth(200);
		TableColumn<PathAndPos, String> column4 = new TableColumn<>("北岸");
		column4.setMinWidth(200);

		table.getColumns().addAll(column1, column2);
		column2.getColumns().addAll(column3, column4);

		ObservableList<PathAndPos> data = FXCollections.observableArrayList();

		for (int i = 0; i < path.size(); i++)
			data.add(new PathAndPos(path.get(i), i));

		column1.setCellValueFactory(new PropertyValueFactory<PathAndPos, String>(
				"index"));
		column3.setCellValueFactory(new PropertyValueFactory<PathAndPos, String>(
				"south"));
		column4.setCellValueFactory(new PropertyValueFactory<PathAndPos, String>(
				"north"));

		table.setItems(data);

		Scene scene = new Scene(table);
		stage.setScene(scene);
		stage.setTitle("农夫过河");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}
