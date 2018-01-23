package Question_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

public class TestHFMTree {
	public static void start() {
		Stage primaryStage = new Stage();
		final String source = getFileSource();
		newBt bt1 = new newBt("1、初始化");
		newBt bt2 = new newBt("2、编码");
		newBt bt3 = new newBt("3、解码");
		newBt bt4 = new newBt("4、打印代码");
		newBt bt5 = new newBt("5、打印树");
		newBt bt6 = new newBt("6、自定义哈夫曼树");

		bt1.setOnAction(e -> {
			Initialization(source);
		});
		bt2.setOnAction(e -> {
			Coding(null, source);
		});
		bt3.setOnAction(e -> {
				Decoding();
		});
		bt4.setOnAction(e ->{
				Print();
		});
		bt5.setOnAction(e->{
				HFMTree<Character> tree = readTreeFromFile();
				if (tree == null) {
					errorAlert("磁盘中，未存在以保存的hfm树，请先初始化！！！");
					return;
				}

				ScrollPane pane = new ScrollPane();
				pane.setPrefSize(800, 800);
				pane.setContent(new HFMTreePane(tree));
				Scene scene = new Scene(pane);
				Stage stage = new Stage();
				stage.setTitle("哈夫曼树");
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();
		});
		bt6.setOnAction(e->{
				drawHFMTree();
		});

		HBox hbox1 = new HBox();
		hbox1.getChildren().addAll(bt1, bt2);
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(bt3, bt4);
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(bt5, bt6);

		VBox vbox = new VBox();

		BorderPane bPane1 = new BorderPane();
		Label label = new Label("HFM编码");
		label.setPrefHeight(100);
		label.setStyle("-fx-font-size: 20px;");
		bPane1.setCenter(label);

		vbox.getChildren().addAll(bPane1, hbox1, hbox2, hbox3);

		Scene scene = new Scene(vbox);

		primaryStage.setScene(scene);
		primaryStage.setTitle("HFM编码程序");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

	}

	// 获得文件内容
	public static String getFileSource() {
		String str = "";

		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File("hfmCode/tobetrans.txt")));
			BufferedReader bf = new BufferedReader(read);

			String lineText = null;
			while ((lineText = bf.readLine()) != null) {
				str += lineText;
				str += "\n";
			}

			bf.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	// 创建hfm树
	public static HFMTree<Character> Initialization(String str) {
		// 将字符集初始化获得字符以及对应的频度存放在线性表中
		ArrayList<HFMTreeNode<Character>> list = HFMCharCode.Initialization(str);

		// 通过线性表创建对应的hfm树
		HFMTree<Character> hfmTree = new HFMTree<>(list);

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("hfmCode/hfmtree.dat")));
			out.writeObject(hfmTree);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		succedAlert("已成功初始化！！！\n\n存放hfm树的文件为hfmCode/hfmtree.dat");
		return hfmTree;
	}

	// 读取文件中的hfm树
	@SuppressWarnings("unchecked")
	public static HFMTree<Character> readTreeFromFile() {
		HFMTree<Character> hfmTree = null;
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File("hfmCode/hfmtree.dat")));
			hfmTree = (HFMTree<Character>) input.readObject();
			input.close();

		} catch (FileNotFoundException e) {

			return null;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			return null;
		}

		return hfmTree;
	}

	// 获得字符串编码
	public static String Coding(HFMTree<Character> hfmTree, String codeStr) {
		ArrayList<CodeNode<Character>> codeCollections = new ArrayList<>();// 存放字符以及对应的hfm编码的hfm编码集
		String str = "";// 存放字符对应hfm编码

		if (hfmTree == null) {

			HFMTree<Character> newTree = readTreeFromFile();

			if (newTree == null) {
				errorAlert("磁盘中，未存在以保存的hfm树，请先初始化！！！");
				return null;
			}
			HFMCharCode.getHFMCode(newTree.rootNode, codeCollections, str);

		} else
			HFMCharCode.getHFMCode(hfmTree.rootNode, codeCollections, str);

		str = HFMCharCode.getStringCode(codeCollections, codeStr);
		str = CondenseCode(str);

		TextToFile(str, "hfmCode/codefile.txt");

		succedAlert("编码成功！\n\n编码后的文件为hfmCode/codefile.txt！");
		try {
			java.awt.Desktop.getDesktop().open(new File("hfmCode/codefile.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 将hfm编码的字符串进行解码
	public static String Decoding() {
		String decodeStr = ""; // 需要解码的hfm字符串
		HFMTree<Character> hfmTree = readTreeFromFile(); // 磁盘中的hfm树

		try {
			Scanner input = new Scanner(new File("hfmCode/codefile.txt"));

			while (input.hasNextLine())
				decodeStr += input.nextLine();
			input.close();
		} catch (FileNotFoundException e) {
			errorAlert("未找到已编码的hfm文件，请先进行编码操作！！！");

			return null;
		}

		String[] array = decodeStr.split(" ");
		decodeStr = toBinaryStr(array);

		String codeStr = "";// 存放字符对应hfm编码
		ArrayList<CodeNode<Character>> codeCollections = new ArrayList<>();// 存放字符以及对应的hfm编码的hfm编码集
		HFMCharCode.getHFMCode(hfmTree.rootNode, codeCollections, codeStr);// 获取hfm编码的字符集

		String textStr = HFMCharCode.Decoding(codeCollections, decodeStr);

		TextToFile(textStr, "hfmCode/textfile.txt");

		succedAlert("解码成功！\n\n解码后的文件为hfmCode/textfile.txt！");
		try {
			java.awt.Desktop.getDesktop().open(new File("hfmCode/textfile.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textStr;
	}

	// 将文件压缩
	public static String CondenseCode(String str) {
		String temp = "";
		List<String> list = new ArrayList<>();

		for (int i = 0; i < str.length(); i++) {
			temp += str.charAt(i);

			if (temp.length() == 8) {
				list.add(temp);
				temp = "";
			} else if (i == str.length() - 1) {
				int index = 8 - temp.length();
				for (int j = 0; j < index; j++)
					temp += "0";

				list.add(temp);
			}
		}

		temp = "";
		for (int i = 0; i < list.size(); i++)
			temp += toDecimalism(list.get(i));

		return temp;
	}

	// 将0、1的字符串转成十进制整数
	public static String toDecimalism(String binaryStr) {
		int num = 0;
		for (int i = 0; i < binaryStr.length(); i++) {
			num *= 2;
			num += new Integer(binaryStr.charAt(i) + "");
		}

		return num + " ";
	}

	// 将十进制整数转换成二进制字符串
	public static String toBinaryStr(String[] array) {
		String str = "";

		for (int i = 0; i < array.length; i++) {
			String temp = "00000000";
			temp += Integer.toBinaryString(new Integer(array[i]));
			str += temp.substring(temp.length() - 8, temp.length());
		}

		return str;
	}

	// 将字符串存入文件
	public static void TextToFile(String source, String filePath) {
		try {
			FileWriter fw = new FileWriter(new File(filePath));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(source);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	// 打印代码
	public static void Print() {
		String decodeStr = ""; // 需要解码的hfm字符串

		try {
			Scanner input = new Scanner(new File("hfmCode/codefile.txt"));

			while (input.hasNextLine())
				decodeStr += input.nextLine();
			input.close();
		} catch (FileNotFoundException e) {
			errorAlert("未找到已编码的hfm文件，请先进行编码操作！！！");

			return;
		}

		String codePrint = "";

		for (int i = 0; i < decodeStr.length(); i++) {
			System.out.print(decodeStr.charAt(i));
			codePrint += decodeStr.charAt(i);
			if ((i + 1) % 50 == 0) {
				codePrint += "\n";
				System.out.println();
			}
		}
		System.out.println();
		TextToFile(codePrint, "hfmCode/codePrint.txt");
		succedAlert("输出成功！\n\n已在控制台输出！并保存在文件codePrint.txt中！");
		try {
			java.awt.Desktop.getDesktop().open(new File("hfmCode/codePrint.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Stage errorAlert(String alertStr) {
		final Stage stage = new Stage();

		Label label = new Label(alertStr);
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
		stage.setTitle("Error!");
		stage.setScene(scene);
		stage.show();

		return stage;
	}

	public static Stage succedAlert(String alertStr) {
		final Stage stage = new Stage();

		Label label = new Label(alertStr);
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
		stage.setTitle("Succed!");
		stage.setScene(scene);
		stage.show();

		return stage;
	}

	public static void delFile(String filePath) {
		File file = new File("filePath");
		if (file.exists())
			file.delete();
		else
			errorAlert("文件：" + filePath + "不存在！");
	}

	// 绘制hfm树图像
	public static void drawHFMTree() {
		BorderPane bPane1 = new BorderPane();
		Label label1 = new Label("输入编码字符串：");
		label1.setPrefSize(150, 40);
		label1.setAlignment(Pos.CENTER);
		final TextField text1 = new TextField();
		text1.setPrefSize(500, 40);
		Button button1 = new Button("展示哈夫曼树");
		button1.setPrefSize(200, 40);
		bPane1.setLeft(label1);
		bPane1.setCenter(text1);
		bPane1.setRight(button1);

		final ScrollPane drawPane = new ScrollPane();
		drawPane.setPrefSize(800, 400);

		BorderPane bPane2 = new BorderPane();
		Label label2 = new Label("输入解码字符串");
		label2.setPrefSize(150, 40);
		final TextField text2 = new TextField();
		text2.setPrefSize(500, 40);
		Button button2 = new Button("解码");
		button2.setPrefSize(150, 40);
		bPane2.setLeft(label2);
		bPane2.setCenter(text2);
		bPane2.setRight(button2);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(bPane1, drawPane, bPane2);

		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String str = text1.getText();

				if (str.compareTo("") == 0) {
					errorAlert("请输入编码字段！！！");
					return;
				}

				ArrayList<HFMTreeNode<Character>> list = HFMCharCode.Initialization(str);
				HFMTree<Character> hfmTree = new HFMTree<>(list);
				HFMTreePane hfmPane = new HFMTreePane(hfmTree);

				ArrayList<CodeNode<Character>> codeList = new ArrayList<>();
				HFMCharCode.getHFMCode(hfmTree.rootNode, codeList, "");
				String codedStr = HFMCharCode.getStringCode(codeList, str);
				ShowCode(str + " 解码为 \n" + codedStr);
				drawPane.setContent(hfmPane);
			}
		});

		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String str = text2.getText();
				HFMTreePane hfmPane = (HFMTreePane) drawPane.getContent();

				if (hfmPane == null) {
					errorAlert("请现在上方的输入框输入编码字段,\n并点击‘Show Huffman Tree’后再进行解码！！！");
					return;
				} else if (str.compareTo("") == 0) {
					errorAlert("请输入解码字段！！！");
					return;
				}

				ArrayList<CodeNode<Character>> codeList = hfmPane.list;

				String decodeStr = HFMCharCode.Decoding(codeList, str);
				ShowCode(str + " is encoded to " + decodeStr);
			}
		});

		Scene scene = new Scene(vbox);
		Stage stage = new Stage();
		stage.setTitle("Draw HFMTree");
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

	}

	// hfm编码解码弹出框
	public static void ShowCode(String str) {
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
		stage.setTitle("Encode Text to Bits");
		stage.setScene(scene);
		stage.show();

	}

}