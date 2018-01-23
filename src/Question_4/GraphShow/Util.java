package Question_4.GraphShow;

import java.util.List;

import Question_4.GraphShow.AbstractGraph.Edge;
import Question_4.GraphShow.GraphNode.LineNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Util {
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

	public static int circleIndexOf(List<GraphNode> graphNodes, Circle circle) {
		for (int i = 0; i < graphNodes.size(); i++)
			if (graphNodes.get(i).verticeNode.circle.equals(circle))
				return i;

		return -1;
	}

	public static Line getLine(List<GraphNode> gNodes, Edge visitedEdge) {
		int index = visitedEdge.u;
		GraphNode gNode = gNodes.get(index);

		for (LineNode lNode : gNode.edgeNodes)
			for (Edge edge : lNode.edges)
				if (edge.isSameEdge(visitedEdge))
					return lNode.line;

		return null;
	}

	public static boolean isExistEdge(List<Edge> edges, int u, int v) {
		for (Edge edge : edges)
			if (edge.u == u && edge.v == v)
				return true;

		return false;
	}
}
