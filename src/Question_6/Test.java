package Question_6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Test{
	final static List<MazePane> allPathPane = new ArrayList<>(); // 存放所有的迷宫路径面板

	public static void start(){
		final Stage primaryStage = new Stage();
		
		final BorderPane bp = new BorderPane();
		bp.setPrefSize(750, 750);

		HBox hbox = new HBox();
		final RadioButton radioBt = new RadioButton("随机生成"); // 随机生成迷宫单选框
		Label label1 = new Label("行：");
		final TextField text1 = new TextField(); // 输入迷宫的行
		text1.setPrefWidth(50);

		Label label2 = new Label("列：");
		final TextField text2 = new TextField(); // 输入迷宫的列
		text2.setPrefWidth(50);
		hbox.getChildren().addAll(radioBt, new SpaceLabel(), label1, text1,
				new SpaceLabel(), label2, text2);

		BorderPane head = new BorderPane();
		head.setRight(hbox);

		BorderPane left = new BorderPane();
		left.setCenter(bp);

		VBox right = new VBox();
		right.setPrefWidth(150);

		Button bt1 = new Button("确定"); // 确定按钮，按照输入的行与列创建迷宫
		bt1.setPrefSize(100, 50);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefSize(150, 50);
		bp1.setCenter(bt1);

		Button bt2 = new Button("取消"); // 取消按钮，关闭主舞台
		bt2.setPrefSize(100, 50);
		BorderPane bp2 = new BorderPane();
		bp2.setPrefSize(150, 50);
		bp2.setCenter(bt2);

		Button bt3 = new Button("找出路径"); // 找出一条迷宫路径
		bt3.setPrefSize(100, 50);
		BorderPane bp3 = new BorderPane();
		bp3.setPrefSize(150, 50);
		bp3.setCenter(bt3);

		Button bt4 = new Button("所有路径"); // 找出所有迷宫路径
		bt4.setPrefSize(100, 50);
		BorderPane bp4 = new BorderPane();
		bp4.setPrefSize(150, 50);
		bp4.setCenter(bt4);

		Button bt5 = new Button("保存数据"); // 保存迷宫数据
		bt5.setPrefSize(100, 50);
		BorderPane bp5 = new BorderPane();
		bp5.setPrefSize(150, 50);
		bp5.setCenter(bt5);

		Button bt6 = new Button("取出数据"); // 提取迷宫路径
		bt6.setPrefSize(100, 50);
		BorderPane bp6 = new BorderPane();
		bp6.setPrefSize(150, 50);
		bp6.setCenter(bt6);

		right.getChildren().addAll(new SpaceLabel(), bp1, new SpaceLabel(),
				bp2, new SpaceLabel(), bp3, new SpaceLabel(), bp4,
				new SpaceLabel(), bp5, new SpaceLabel(), bp6, new SpaceLabel());

		HBox middle = new HBox();
		middle.getChildren().addAll(left, right);

		VBox body = new VBox();
		body.getChildren().addAll(new SpaceLabel(), head, new SpaceLabel(),
				middle);

		Scene scene = new Scene(body);
		primaryStage.setTitle("迷宫");
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		// 设置主面板按钮点击时间
		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				allPathPane.clear(); // 清空之前保存的路径

				try {
					int m = new Integer(text1.getText());
					int n = new Integer(text2.getText());
					MazePane maze = new MazePane(m, n, radioBt.isSelected());
					bp.setCenter(maze);
				} catch (NumberFormatException e) {
					Alert("输入有误！！！"); // 输入的迷宫行与列存在错误
				}
			}
		});

		bt2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.close();
			}
		});

		bt3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (bp.getChildren().size() == 0) {
					Alert("请先点击确定，创建迷宫！！！"); // 未生成迷宫，无法找出路径
					return;
				}

				final MazePane maze = (MazePane) bp.getChildren().get(0);
				maze.setMouseTransparent(true);
				maze.model.creatGraph();
				
				final List<Model.Node> path = maze.model.getPath();

				if (path == null)
					Alert("路径不存在！！！"); // 迷宫路径不存在
				else {
					Timeline animation = new Timeline(new KeyFrame(Duration
							.millis(1000), new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							maze.drawMazePath(path.remove(0));
						}
					}));
					animation.setCycleCount(path.size());
					animation.play();
				}
			}
		});

		bt4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				allPathPane.clear(); // 清空之前路径

				if (bp.getChildren().size() == 0) {
					Alert("请先点击确定，创建迷宫！！！"); // 未生成迷宫，无法找出路径
					return;
				}

				final MazePane maze = (MazePane) bp.getChildren().get(0);
				maze.setMouseTransparent(true);
				maze.model.creatGraph();
				
				final List<List<Model.Node>> allPath = maze.model.getAllPath();

				if (allPath.isEmpty()) {
					Alert("路径不存在！！！"); // 迷宫路径不存在
					return;
				}

				final int[] index = { 0 };
				final Pane mazeContent = new Pane();
				mazeContent.setMouseTransparent(true);
				
				final MazePane pathPane = new MazePane(maze.maze);// 创建与原来障碍相同的迷宫Pane
				mazeContent.getChildren().add(pathPane); // 将该Pane添加到迷宫容器内
				final List<Model.Node> path = new ArrayList<Model.Node>(allPath
						.get(index[0])); // 获取一条路径

				// 迷宫添加路径动画
				Timeline animation = new Timeline(new KeyFrame(Duration
						.millis(1000), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						pathPane.drawMazePath(path.remove(0));
					}
				}));
				animation.setCycleCount(path.size()); // 设置动画运行次数
				animation.play(); // 开始动画
				// 设置动画的结束事件
				animation.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						allPathPane.add(pathPane);
						Alert("该路径演示完毕。");
					}
				});

				BorderPane top = new BorderPane(); // 展示所有路径的数目
				Label total = new Label("共 " + allPath.size() + " 条路径");
				top.setCenter(total);

				BorderPane head = new BorderPane(); // 头部面板
				final Button last = new Button("<"); // 上一条路径
				last.setPrefSize(40, 50);
				final Button next = new Button(">"); // 下一条路径
				next.setPrefSize(40, 50);
				final Label label = new Label("路径: " + (index[0] + 1)); // 当前路径
				head.setLeft(last);
				head.setCenter(label);
				head.setRight(next); // 设置部件的位置

				last.setDisable(true);
				next.setDisable(allPath.size() == 1); // 设置按钮是否可用

				VBox body = new VBox();
				body.getChildren().addAll(top, head, mazeContent); // 添加所有节点

				Scene scene = new Scene(body); // 创建场景
				Stage stage = new Stage(); // 创建舞台
				stage.setScene(scene); // 设置场景
				stage.setTitle("迷宫所有路径");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();

				// 上一条路径按钮点击事件
				last.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						index[0] = index[0] - 1; // 路径下标 - 1
						label.setText("路径：" + (index[0] + 1)); // 更改当前路径
						mazeContent.getChildren().clear(); // 清空迷宫容器内的节点，好添加新的maze

						if (index[0] < allPathPane.size()) {
							MazePane pathPane = allPathPane.get(index[0]);
							mazeContent.getChildren().add(pathPane);
						} else {
							final MazePane pathPane = new MazePane(maze.maze); // 创建新的mazePane
							mazeContent.getChildren().add(pathPane);
							final List<Model.Node> path = new ArrayList<Model.Node>(
									allPath.get(index[0]));
							Timeline animation = new Timeline(new KeyFrame(
									Duration.millis(1000),
									new EventHandler<ActionEvent>() {
										public void handle(ActionEvent e) {
											pathPane.drawMazePath(path
													.remove(0));
										}
									}));
							animation.setCycleCount(path.size());
							animation.play();
						}

						next.setDisable(false);

						if (index[0] == 0)
							last.setDisable(true);
					}
				});

				// 下一条路径按钮点击事件
				next.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						index[0] = index[0] + 1; // 路径下标 + 1
						label.setText("路径：" + (index[0] + 1)); // 更改当前路径
						mazeContent.getChildren().clear();

						if (index[0] < allPathPane.size()) {
							MazePane pathPane = allPathPane.get(index[0]);
							mazeContent.getChildren().add(pathPane);
						} else {
							final MazePane pathPane = new MazePane(maze.maze); // 创建新的mazePane
							mazeContent.getChildren().add(pathPane);
							final List<Model.Node> path = new ArrayList<Model.Node>(
									allPath.get(index[0]));
							Timeline animation = new Timeline(new KeyFrame(
									Duration.millis(1000),
									new EventHandler<ActionEvent>() {
										public void handle(ActionEvent e) {
											pathPane.drawMazePath(path
													.remove(0));
										}
									}));
							animation.setCycleCount(path.size());
							animation.play();
							// 设置动画结束事件
							animation
									.setOnFinished(new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent arg0) {
											if (index[0] == allPathPane.size())
												allPathPane.add(pathPane);

											Alert("该路径演示完毕。");
										}
									});
						}

						last.setDisable(false);

						if (index[0] == allPath.size() - 1)
							next.setDisable(true);
					}
				});

			}
		});

		bt5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (bp.getChildren().size() == 0) {
					Alert("请先点击确定，创建迷宫！！！"); // 未生成迷宫，无法找出路径
					return;
				}
				try {
					FileOutputStream fs = new FileOutputStream(new File(
							"maze.dat"));
					ObjectOutputStream out = new ObjectOutputStream(fs);
					MazePane maze = (MazePane) bp.getChildren().get(0);
					out.writeObject(maze);
					out.close();
					Alert("保存成功！！！\n文件名为：maze.dat");
				} catch (IOException e) {
					e.printStackTrace();
					Alert("保存失败！！！");
					return;
				}

			}
		});

		bt6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (allPathPane.size() == 0) {
					Alert("未找到路径，请点击所有路径查找路径！！！");
					return;
				}

				final Stage stage = Alert("正在打印路径。。。");

				Calendar calendar = Calendar.getInstance(); // 获取当前时间
				StringBuilder sb = new StringBuilder(); // 创建字符串
				sb.append("mazePathImg/Path");
				sb.append(calendar.get(GregorianCalendar.YEAR));
				sb.append(calendar.get(GregorianCalendar.MONTH));
				sb.append(calendar.get(GregorianCalendar.DAY_OF_MONTH));
				sb.append(calendar.get(GregorianCalendar.HOUR));
				sb.append(calendar.get(GregorianCalendar.MINUTE));
				sb.append(calendar.get(GregorianCalendar.SECOND));
				final String packerPath = sb.toString(); // 获得文件夹路径
				final File filePacket = new File(packerPath);
				filePacket.mkdirs(); // 创建文件夹

				Timeline animation = new Timeline(new KeyFrame(Duration
						.millis(2000), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						MazePane mazePane = allPathPane.remove(0);
						Calendar calendar = Calendar.getInstance();
						String path = packerPath + "/迷宫路径"
								+ calendar.get(GregorianCalendar.YEAR)
								+ calendar.get(GregorianCalendar.MONTH)
								+ calendar.get(GregorianCalendar.DAY_OF_MONTH)
								+ calendar.get(GregorianCalendar.HOUR)
								+ calendar.get(GregorianCalendar.MINUTE)
								+ calendar.get(GregorianCalendar.SECOND)
								+ ".png";
						File file = new File(path);
						WritableImage image = mazePane.snapshot(
								new SnapshotParameters(), null);

						try {
							ImageIO.write(
									SwingFXUtils.fromFXImage(image, null),
									"png", file);
						} catch (IOException e1) {
							Alert("取出数据失败！！！");
							e1.printStackTrace();
							return;
						}
					}
				}));
				animation.setCycleCount(allPathPane.size());
				animation.play();
				animation.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						stage.close();
						try {
							java.awt.Desktop.getDesktop().open(filePacket);
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
						Alert("取出数据成功！！！所有路径图片保存在：\n" + packerPath + "文件夹下");
					}
				});
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
