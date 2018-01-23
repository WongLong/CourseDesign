package Question_2;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Versions_2{
	public static void start() {
		Util.creatCardToFile();
		Stage primaryStage = new Stage();
		
		Button bt1 = new Button("ˢ��");
		bt1.setPrefSize(100, 40);
		Button bt = new Button("�ҳ���");
		bt.setPrefSize(200, 40);
		final TextField text1 = new TextField();
		text1.setPrefSize(200, 40);
		BorderPane bPane1 = new BorderPane();
		bPane1.setPrefSize(500, 50);

		BorderPane bPane2 = new BorderPane();
		bPane2.setCenter(bt1);
		bPane1.setRight(bPane2);

		bPane2 = new BorderPane();
		bPane2.setCenter(text1);
		bPane1.setCenter(bPane2);

		bPane2 = new BorderPane();
		bPane2.setCenter(bt);
		bPane1.setLeft(bPane2);

		final ImagePane img = new ImagePane();

		Label label = new Label("������ʽ:");
		label.setPrefSize(150, 40);
		BorderPane bP1 = new BorderPane();
		bP1.setBottom(label);

		final TextField text2 = new TextField();
		text2.setPrefSize(250, 40);
		BorderPane bP2 = new BorderPane();
		bP2.setBottom(text2);

		Button bt2 = new Button("��֤");
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
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		showStage2();

		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				img.getRandomCard();
				text1.setText("");
				text2.setText("");
			}
		});

		bt2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String str = text2.getText();

				boolean flag = Util.checkExpression(str);
				if (!flag)
					Util.Alert("����!");
				else {
					ArrayList<Integer> num = new ArrayList<>();
					int result = Util.toPostfixExpression(str, num);

					ArrayList<Integer> cardNum = new ArrayList<>();
					for (int i = 0; i < img.list.size(); i++)
						cardNum.add(img.list.get(i).num);

					flag = Util.isMatchNumber(num, cardNum);

					if (flag)
						if (result == 24)
							Util.Alert("��ȷ");
						else
							Util.Alert("�޽�");
					else
						Util.Alert("���ֲ�����");
				}
			}
		});

		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ArrayList<String> expList = new ArrayList<>();
				ArrayList<Card> list = new ArrayList<>(img.list);

				ArrayList<Integer> allNum = new ArrayList<>();// ���ͼƬ��Ӧ�����ּ�
				for (int i = 0; i < list.size(); i++)
					allNum.add(list.get(i).num);

				NewList saveOpNum = new NewList();// ���ڴ������������ϵ����Ա�
				Util.getAllNum(allNum, 0, new int[4], saveOpNum);// ��ø�4�ſ������е��������

				saveOpNum.check();// ����ظ����������

				ArrayList<char[]> saveOP = new ArrayList<>();// ���ڴ�����в�������ϵ����Ա�
				Util.getAllOP(0, new char[3], saveOP);// ������в��������

				Util.insertBracket(saveOP, saveOP.size());// ������е�������ϲ�����ڲ�����������Ա�				

				ArrayList<String> expression = Util.getExpression(saveOpNum,
						saveOP);// �����������������������ϵ���׺����ַ���

				for (String exp : expression) {
					String post = Util.infixToPostfix(exp);
					if (Util.countPostfix(post) == 24.0) {
						expList.add(exp);
					}
				}

				String showExp = "";
				if (expList.isEmpty())
					text1.setText("No solution");
				else {
					for (String str : expList)
						showExp += str + ", ";
					text1.setText(showExp);
				}
			}
		});
	}

	public static void showStage2() {
		Label label = new Label("Input four numbers between 1 to 13");
		BorderPane bp = new BorderPane();
		bp.setPrefSize(500, 40);
		bp.setCenter(label);

		HBox hbox1 = new HBox();
		hbox1.setPrefSize(500, 80);
		final TextField text1 = new TextField();
		text1.setPrefSize(100, 80);
		text1.setFont(Font.font(40));
		final TextField text2 = new TextField();
		text2.setPrefSize(100, 80);
		text2.setFont(Font.font(40));
		final TextField text3 = new TextField();
		text3.setPrefSize(100, 80);
		text3.setFont(Font.font(40));
		final TextField text4 = new TextField();
		text4.setPrefSize(100, 80);
		text4.setFont(Font.font(40));
		hbox1.getChildren().addAll(new SpaceLabel(" "), text1,
				new SpaceLabel(" "), text2, new SpaceLabel(" "), text3,
				new SpaceLabel(" "), text4, new SpaceLabel(" "));

		final Label showLabel = new Label();
		showLabel.setPrefSize(200, 40);
		showLabel.setBorder(new Border(new BorderStroke(null,
				BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
		BorderPane bp1 = new BorderPane();
		bp1.setPrefSize(250, 50);
		bp1.setCenter(showLabel);

		Button bt = new Button("Fing a Solution");
		bt.setPrefSize(200, 40);
		BorderPane bp2 = new BorderPane();
		bp2.setPrefSize(250, 50);
		bp2.setCenter(bt);

		HBox hbox2 = new HBox();
		hbox2.setPrefSize(500, 50);
		hbox2.getChildren().addAll(bp1, bp2);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(bp, hbox1, hbox2);

		Scene scene = new Scene(vbox);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("24-Point Card Game");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String[] str = { text1.getText(), text2.getText(),
						text3.getText(), text4.getText() };
				boolean flag = true;

				ArrayList<Integer> allNum = new ArrayList<>();
				for (int i = 0; i < 4; i++) {
					if (str[i].compareTo("") == 0) {
						Util.Alert("�������󣡣���");
						return;
					} else {
						try {
							allNum.add(new Integer(str[i]));
							if (allNum.get(i) > 13 || allNum.get(i) < 1) {
								Util.Alert("�������󣡣���");
								return;
							}
						} catch (NumberFormatException e) {
							Util.Alert("�������󣡣���");
							return;
						}
					}
				}

				NewList saveOpNum = new NewList();// ���ڴ������������ϵ����Ա�
				Util.getAllNum(allNum, 0, new int[4], saveOpNum);// ��ø�4�ſ������е��������
				saveOpNum.check();// ����ظ����������

				ArrayList<char[]> saveOP = new ArrayList<>();// ���ڴ�����в�������ϵ����Ա�
				Util.getAllOP(0, new char[3], saveOP);// ������в��������
				Util.insertBracket(saveOP, saveOP.size());// ������е�������ϲ�����ڲ�����������Ա�
				
				ArrayList<String> expression = Util.getExpression(saveOpNum,
						saveOP);// �����������������������ϵ���׺����ַ���

				for (String exp : expression) {
					String post = Util.infixToPostfix(exp); // ����׺���ʽת���ɺ�׺
					if (Util.countPostfix(post) == 24.0) { // �����׺���ʽ
						showLabel.setText(exp);
						flag = false;
						break;
					}
				}

				if (flag)
					showLabel.setText("No Solution");
			}
		});
	}
}
