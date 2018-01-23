package Question_2;

import java.util.ArrayList;

import Question_2.Util;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Versions_1 extends Application {
	public static void main(String[] args) {
		Util.creatCardToFile();
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Button bt1 = new Button("刷新");
		bt1.setPrefSize(100, 40);
		BorderPane bPane1 = new BorderPane();
		bPane1.setPrefSize(500, 50);
		BorderPane bPane2 = new BorderPane();
		bPane2.setCenter(bt1);
		bPane1.setRight(bPane2);

		final ImagePane img = new ImagePane();

		Label label = new Label("输入表达式:");
		label.setPrefSize(150, 40);
		BorderPane bP1 = new BorderPane();
		bP1.setBottom(label);

		final TextField text = new TextField();
		text.setPrefSize(250, 40);
		BorderPane bP2 = new BorderPane();
		bP2.setBottom(text);

		Button bt2 = new Button("验证");
		bt2.setPrefSize(100, 40);
		BorderPane bP3 = new BorderPane();
		bP3.setBottom(bt2);

		HBox hbox2 = new HBox();
		hbox2.setPrefSize(500, 50);
		hbox2.getChildren().addAll(bP1, bP2, bP3);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(bPane1, img, hbox2);

		Scene scene = new Scene(vbox);
		primaryStage.setTitle("24-Point Card Game");
		primaryStage.setScene(scene);
		primaryStage.show();

		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				img.getRandomCard();
			}
		});

		bt2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String str = text.getText();

				boolean flag = Util.checkExpression(str);			
				if(!flag)
					Util.Alert("错误!");
				else{
					ArrayList<Integer> num = new ArrayList<>();
					int result = Util.toPostfixExpression(str, num);
	
					ArrayList<Integer> cardNum = new ArrayList<>();
					for (int i = 0; i < img.list.size(); i++)
						cardNum.add(img.list.get(i).num);
	
					flag = Util.isMatchNumber(num, cardNum);
	
					if (flag)
						if (result == 24)
							Util.Alert("正确");
						else
							Util.Alert("错误");
					else
						Util.Alert("数字不存在");
				}
			}
		});
	}

}
