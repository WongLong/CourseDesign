package Question_4.GraphShow;

import Question_4.SpaceLabel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GraphShow{
	public static void Show(){
		final ScrollPane sp = new ScrollPane();
		sp.setPrefSize(1000, 800);
		
		GraphPane<String> gPane = new GraphPane<>(false);
		gPane.setMinSize(1000, 800);
		sp.setContent(gPane);
		
		Label label = new Label("Starting Vertice:");
		label.setPrefHeight(40);
		final TextField text = new TextField();
		BorderPane bp1 = new BorderPane();
		bp1.setPrefHeight(40);
		bp1.setCenter(text);

		Button dfs = new Button("Display DFS Tree");
		BorderPane bp2 = new BorderPane();
		bp2.setPrefHeight(40);
		bp2.setCenter(dfs);

		Button bfs = new Button("Display BFS Tree");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefHeight(40);
		bp3.setCenter(bfs);
		
		final RadioButton radioBt = new RadioButton("带权图");
		BorderPane bp4 = new BorderPane();
		bp4.setPrefHeight(40);
		bp4.setCenter(radioBt);
		
		HBox bottom = new HBox();
		bottom.getChildren().addAll(label, new SpaceLabel(), bp1,
				new SpaceLabel(), bp2, new SpaceLabel(), bp3, new SpaceLabel(), bp4);
		
		final VBox body = new VBox();
		body.getChildren().addAll(sp, bottom);
		
		// 带权图的部分面板
		Label start = new Label("Starting Vertice:");
		start.setPrefHeight(40);
		final TextField startInput = new TextField();
		BorderPane bp5 = new BorderPane();
		bp5.setPrefHeight(40);
		bp5.setCenter(startInput);
		
		Label end = new Label("Ending Vertice:");
		end.setPrefHeight(40);
		final TextField endInput = new TextField();
		BorderPane bp6 = new BorderPane();
		bp6.setPrefHeight(40);
		bp6.setCenter(endInput);
		
		Button displayPath = new Button("Display Shortest Path");
		BorderPane bp7 = new BorderPane();
		bp7.setPrefHeight(40);
		bp7.setCenter(displayPath);
		
		Button displayMST = new Button("Display MST");
		BorderPane bp8 = new BorderPane();
		bp8.setPrefHeight(40);
		bp8.setCenter(displayMST);
		
		final HBox weightGraphPart = new HBox();
		weightGraphPart.getChildren().addAll(start, new SpaceLabel(), bp5,
				new SpaceLabel(), end, new SpaceLabel(), bp6, new SpaceLabel(), bp7, new SpaceLabel(), bp8, new SpaceLabel());

		body.getChildren().add(weightGraphPart);
		weightGraphPart.setVisible(false);
		
		Scene scene = new Scene(body);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("图");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

		dfs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String vertice = text.getText();
				if(vertice.compareTo("") == 0){
					Util.Alert("输入有误！！！");
					return;
				}
				
				@SuppressWarnings("unchecked")
				GraphPane<String> gPane = (GraphPane<String>)sp.getContent();
				if(!gPane.isExistVertice(vertice)){
					Util.Alert("该顶点不存在！！！");
					return;
				}
				
				gPane.dfs(vertice);
			}
		});
		
		bfs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {	
				String vertice = text.getText();
				if(vertice.compareTo("") == 0){
					Util.Alert("输入有误！！！");
					return;
				}
				
				@SuppressWarnings("unchecked")
				GraphPane<String> gPane = (GraphPane<String>)sp.getContent();
				if(!gPane.isExistVertice(vertice)){
					Util.Alert("该定点不存在！！！");
					return;
				}
	
				gPane.bfs(vertice);
			}
		});
		
		displayPath.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent e) {
				String startVertice = startInput.getText();
				String endVertice = endInput.getText();
				if(startVertice.compareTo("") == 0 || endVertice.compareTo("") == 0){
					Util.Alert("输入有误！！！");
					return;
				}
				
				@SuppressWarnings("unchecked")
				GraphPane<String> gPane = (GraphPane<String>)sp.getContent();
				if(!gPane.isExistVertice(startVertice) || !gPane.isExistVertice(endVertice)){
					Util.Alert("顶点不存在！！！");
					return;
				}
				
				gPane.getShortestPath(startVertice, endVertice);
			}
		});
	
		displayMST.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String startVertice = startInput.getText();
				if(startVertice.compareTo("") == 0){
					Util.Alert("输入有误！！！");
					return;
				}
				
				@SuppressWarnings("unchecked")
				GraphPane<String> gPane = (GraphPane<String>)sp.getContent();
				if(!gPane.isExistVertice(startVertice)){
					Util.Alert("该顶点不存在！！！");
					return;
				}
				
				gPane.displayMST(startVertice);
			}
		});
		
		radioBt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(radioBt.isSelected()){
					GraphPane<String> gPane = new GraphPane<>(true);
					gPane.setMinSize(1000, 800);
					sp.setContent(gPane);
					weightGraphPart.setVisible(true);
				}else{
					GraphPane<String> gPane = new GraphPane<>(false);
					gPane.setMinSize(1000, 800);
					sp.setContent(gPane);
					weightGraphPart.setVisible(false);
				}
				
			}
		});
		
	}
}
