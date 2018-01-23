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
	final static List<MazePane> allPathPane = new ArrayList<>(); // ������е��Թ�·�����

	public static void start(){
		final Stage primaryStage = new Stage();
		
		final BorderPane bp = new BorderPane();
		bp.setPrefSize(750, 750);

		HBox hbox = new HBox();
		final RadioButton radioBt = new RadioButton("�������"); // ��������Թ���ѡ��
		Label label1 = new Label("�У�");
		final TextField text1 = new TextField(); // �����Թ�����
		text1.setPrefWidth(50);

		Label label2 = new Label("�У�");
		final TextField text2 = new TextField(); // �����Թ�����
		text2.setPrefWidth(50);
		hbox.getChildren().addAll(radioBt, new SpaceLabel(), label1, text1,
				new SpaceLabel(), label2, text2);

		BorderPane head = new BorderPane();
		head.setRight(hbox);

		BorderPane left = new BorderPane();
		left.setCenter(bp);

		VBox right = new VBox();
		right.setPrefWidth(150);

		Button bt1 = new Button("ȷ��"); // ȷ����ť����������������д����Թ�
		bt1.setPrefSize(100, 50);
		BorderPane bp1 = new BorderPane();
		bp1.setPrefSize(150, 50);
		bp1.setCenter(bt1);

		Button bt2 = new Button("ȡ��"); // ȡ����ť���ر�����̨
		bt2.setPrefSize(100, 50);
		BorderPane bp2 = new BorderPane();
		bp2.setPrefSize(150, 50);
		bp2.setCenter(bt2);

		Button bt3 = new Button("�ҳ�·��"); // �ҳ�һ���Թ�·��
		bt3.setPrefSize(100, 50);
		BorderPane bp3 = new BorderPane();
		bp3.setPrefSize(150, 50);
		bp3.setCenter(bt3);

		Button bt4 = new Button("����·��"); // �ҳ������Թ�·��
		bt4.setPrefSize(100, 50);
		BorderPane bp4 = new BorderPane();
		bp4.setPrefSize(150, 50);
		bp4.setCenter(bt4);

		Button bt5 = new Button("��������"); // �����Թ�����
		bt5.setPrefSize(100, 50);
		BorderPane bp5 = new BorderPane();
		bp5.setPrefSize(150, 50);
		bp5.setCenter(bt5);

		Button bt6 = new Button("ȡ������"); // ��ȡ�Թ�·��
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
		primaryStage.setTitle("�Թ�");
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();

		// ��������尴ť���ʱ��
		bt1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				allPathPane.clear(); // ���֮ǰ�����·��

				try {
					int m = new Integer(text1.getText());
					int n = new Integer(text2.getText());
					MazePane maze = new MazePane(m, n, radioBt.isSelected());
					bp.setCenter(maze);
				} catch (NumberFormatException e) {
					Alert("�������󣡣���"); // ������Թ������д��ڴ���
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
					Alert("���ȵ��ȷ���������Թ�������"); // δ�����Թ����޷��ҳ�·��
					return;
				}

				final MazePane maze = (MazePane) bp.getChildren().get(0);
				maze.setMouseTransparent(true);
				maze.model.creatGraph();
				
				final List<Model.Node> path = maze.model.getPath();

				if (path == null)
					Alert("·�������ڣ�����"); // �Թ�·��������
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
				allPathPane.clear(); // ���֮ǰ·��

				if (bp.getChildren().size() == 0) {
					Alert("���ȵ��ȷ���������Թ�������"); // δ�����Թ����޷��ҳ�·��
					return;
				}

				final MazePane maze = (MazePane) bp.getChildren().get(0);
				maze.setMouseTransparent(true);
				maze.model.creatGraph();
				
				final List<List<Model.Node>> allPath = maze.model.getAllPath();

				if (allPath.isEmpty()) {
					Alert("·�������ڣ�����"); // �Թ�·��������
					return;
				}

				final int[] index = { 0 };
				final Pane mazeContent = new Pane();
				mazeContent.setMouseTransparent(true);
				
				final MazePane pathPane = new MazePane(maze.maze);// ������ԭ���ϰ���ͬ���Թ�Pane
				mazeContent.getChildren().add(pathPane); // ����Pane��ӵ��Թ�������
				final List<Model.Node> path = new ArrayList<Model.Node>(allPath
						.get(index[0])); // ��ȡһ��·��

				// �Թ����·������
				Timeline animation = new Timeline(new KeyFrame(Duration
						.millis(1000), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						pathPane.drawMazePath(path.remove(0));
					}
				}));
				animation.setCycleCount(path.size()); // ���ö������д���
				animation.play(); // ��ʼ����
				// ���ö����Ľ����¼�
				animation.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						allPathPane.add(pathPane);
						Alert("��·����ʾ��ϡ�");
					}
				});

				BorderPane top = new BorderPane(); // չʾ����·������Ŀ
				Label total = new Label("�� " + allPath.size() + " ��·��");
				top.setCenter(total);

				BorderPane head = new BorderPane(); // ͷ�����
				final Button last = new Button("<"); // ��һ��·��
				last.setPrefSize(40, 50);
				final Button next = new Button(">"); // ��һ��·��
				next.setPrefSize(40, 50);
				final Label label = new Label("·��: " + (index[0] + 1)); // ��ǰ·��
				head.setLeft(last);
				head.setCenter(label);
				head.setRight(next); // ���ò�����λ��

				last.setDisable(true);
				next.setDisable(allPath.size() == 1); // ���ð�ť�Ƿ����

				VBox body = new VBox();
				body.getChildren().addAll(top, head, mazeContent); // ������нڵ�

				Scene scene = new Scene(body); // ��������
				Stage stage = new Stage(); // ������̨
				stage.setScene(scene); // ���ó���
				stage.setTitle("�Թ�����·��");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();

				// ��һ��·����ť����¼�
				last.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						index[0] = index[0] - 1; // ·���±� - 1
						label.setText("·����" + (index[0] + 1)); // ���ĵ�ǰ·��
						mazeContent.getChildren().clear(); // ����Թ������ڵĽڵ㣬������µ�maze

						if (index[0] < allPathPane.size()) {
							MazePane pathPane = allPathPane.get(index[0]);
							mazeContent.getChildren().add(pathPane);
						} else {
							final MazePane pathPane = new MazePane(maze.maze); // �����µ�mazePane
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

				// ��һ��·����ť����¼�
				next.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						index[0] = index[0] + 1; // ·���±� + 1
						label.setText("·����" + (index[0] + 1)); // ���ĵ�ǰ·��
						mazeContent.getChildren().clear();

						if (index[0] < allPathPane.size()) {
							MazePane pathPane = allPathPane.get(index[0]);
							mazeContent.getChildren().add(pathPane);
						} else {
							final MazePane pathPane = new MazePane(maze.maze); // �����µ�mazePane
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
							// ���ö��������¼�
							animation
									.setOnFinished(new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent arg0) {
											if (index[0] == allPathPane.size())
												allPathPane.add(pathPane);

											Alert("��·����ʾ��ϡ�");
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
					Alert("���ȵ��ȷ���������Թ�������"); // δ�����Թ����޷��ҳ�·��
					return;
				}
				try {
					FileOutputStream fs = new FileOutputStream(new File(
							"maze.dat"));
					ObjectOutputStream out = new ObjectOutputStream(fs);
					MazePane maze = (MazePane) bp.getChildren().get(0);
					out.writeObject(maze);
					out.close();
					Alert("����ɹ�������\n�ļ���Ϊ��maze.dat");
				} catch (IOException e) {
					e.printStackTrace();
					Alert("����ʧ�ܣ�����");
					return;
				}

			}
		});

		bt6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (allPathPane.size() == 0) {
					Alert("δ�ҵ�·������������·������·��������");
					return;
				}

				final Stage stage = Alert("���ڴ�ӡ·��������");

				Calendar calendar = Calendar.getInstance(); // ��ȡ��ǰʱ��
				StringBuilder sb = new StringBuilder(); // �����ַ���
				sb.append("mazePathImg/Path");
				sb.append(calendar.get(GregorianCalendar.YEAR));
				sb.append(calendar.get(GregorianCalendar.MONTH));
				sb.append(calendar.get(GregorianCalendar.DAY_OF_MONTH));
				sb.append(calendar.get(GregorianCalendar.HOUR));
				sb.append(calendar.get(GregorianCalendar.MINUTE));
				sb.append(calendar.get(GregorianCalendar.SECOND));
				final String packerPath = sb.toString(); // ����ļ���·��
				final File filePacket = new File(packerPath);
				filePacket.mkdirs(); // �����ļ���

				Timeline animation = new Timeline(new KeyFrame(Duration
						.millis(2000), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						MazePane mazePane = allPathPane.remove(0);
						Calendar calendar = Calendar.getInstance();
						String path = packerPath + "/�Թ�·��"
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
							Alert("ȡ������ʧ�ܣ�����");
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
						Alert("ȡ�����ݳɹ�����������·��ͼƬ�����ڣ�\n" + packerPath + "�ļ�����");
					}
				});
			}
		});
	}
	
	// ��Ϣ���ѿ�
	public static Stage Alert(String str) {
		final Stage stage = new Stage();

		Label label = new Label(str);
		label.setPrefWidth(400);
		BorderPane bPane1 = new BorderPane();
		bPane1.setPrefSize(400, 200);
		bPane1.setCenter(label);

		Button bt = new Button("ȷ��");
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
		stage.setTitle("��Ϣ");
		stage.setScene(scene);
		stage.show();

		return stage;
	}
}
