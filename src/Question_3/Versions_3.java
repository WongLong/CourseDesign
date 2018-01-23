package Question_3;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Question_2.SpaceLabel;

public class Versions_3 extends Application {
	@Override
	public void start(Stage primaryStage) {
		final TextField text = new TextField();
		text.setPromptText("请输入硬币的阶数");
		text.setFont(Font.font(20));
		text.setPrefSize(200, 40);

		Button bt = new Button("确定");
		bt.setPrefSize(50, 40);

		final RadioButton radioBt = new RadioButton("随机生成");
		radioBt.setPrefSize(150, 40);

		HBox hb = new HBox();
		hb.setPrefSize(440, 40);
		hb.getChildren().addAll(radioBt, new SpaceLabel(" "), text,
				new SpaceLabel(" "), bt);

		BorderPane bp1 = new BorderPane();
		bp1.setPrefSize(440, 60);
		bp1.setCenter(hb);
		BorderPane bp2 = new BorderPane();
		bp2.setPrefSize(800, 60);
		bp2.setRight(bp1);

		final HBox showPane = new HBox();
		showPane.setPrefSize(800, 300);

		Button bt1 = new Button("找解");
		Button bt2 = new Button("重新开始");
		BorderPane bp3 = new BorderPane();
		bp3.setPrefSize(390, 50);
		bp3.setRight(bt1);

		BorderPane bp4 = new BorderPane();
		bp4.setPrefSize(390, 40);
		bp4.setLeft(bt2);

		BorderPane bottom = new BorderPane();
		bottom.setPrefSize(800, 40);
		bottom.setLeft(bp3);
		bottom.setRight(bp4);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(bp2, showPane, bottom);

		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tail Problem");
		primaryStage.show();

		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showPane.getChildren().clear();
				String str = text.getText();
				boolean flag = radioBt.isSelected();
				try {
					int orderNum = new Integer(str);
					char[] coin = null;
					if (flag)
						coin = randomCreatCoin(orderNum);
					else
						coin = defaultCreatCoin(orderNum);
					CoinPane cp = new CoinPane(coin, orderNum, null);
					showPane.getChildren().addAll(new SpaceLabel(" "), cp);
				} catch (NumberFormatException e) {
					text.setText("");
					Alert("输入的数据有误！！！");
				}
			}
		});

		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ObservableList<Node> list = showPane.getChildren();
				CoinPane cp = (CoinPane) list.get(1);
				TailModel model = new TailModel(cp.orderNum, true);
				List<Integer> path = model.getShortestPath(TailModel
						.getIndex(cp.coin));

				if (path.size() == 1) {
					Alert("该硬币组合无解！！！");
				} else {
					for (int i = 1; i < path.size(); i++) {
						char[] coin = TailModel.getNode(path.get(i));
						char[] last = TailModel.getNode(path.get(i - 1));
						ArrayList<Integer> different = getDifferentIndex(coin,
								last);
						CoinPane newCP = new CoinPane(coin, cp.orderNum,
								different);
						showPane.getChildren().addAll(new SpaceLabel(" "),
								newCP);
					}
				}
			}
		});

		bt2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				text.setText("");
				showPane.getChildren().clear();
			}
		});
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public static ArrayList<Integer> getDifferentIndex(char[] coin, char[] last) {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < coin.length; i++) {
			if (coin[i] != last[i])
				list.add(i);
		}

		return list;
	}

	public static char[] randomCreatCoin(int order) {
		char[] ch = new char[order * order];
		for (int i = 0; i < ch.length; i++) {
			int num = (int) (Math.random() * 2);
			if (num == 0)
				ch[i] = 'H';
			else
				ch[i] = 'T';
		}

		return ch;
	}

	public static char[] defaultCreatCoin(int order) {
		char[] ch = new char[order * order];
		for (int i = 0; i < ch.length; i++)
			ch[i] = 'H';
		return ch;
	}

	// 消息提醒框
	public static void Alert(String str) {
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
	}

}
