package Question_6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MazePane extends GridPane implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Model model; // �Թ�ģ��
	int[][] maze; // �Թ�����
	List<List<Pane>> mazePane = new ArrayList<>();// �Թ��������
	int m; // �Թ�����
	int n; // �Թ�����

	public MazePane(int m, int n, boolean flag) {
		this.model = new Model(m, n, flag);
		this.m = m;
		this.n = n;

		this.maze = model.getMaze();
		for (int i = 0; i < m; i++)
			mazePane.add(new ArrayList<Pane>());
		creatMazePane();
	}

	public MazePane(int[][] maze) {
		this.m = maze.length;
		this.n = maze[0].length;

		this.maze = maze;
		for (int i = 0; i < m; i++)
			mazePane.add(new ArrayList<Pane>());

		creatMazePane();
	}

	// �����Թ����
	private void creatMazePane() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				creatMazeNode(i, j);

		drawMazePane();
	}

	// �����Թ��ڵ�
	private void creatMazeNode(final int m, final int n) {
		Pane pane = null;

		if (maze[m][n] == 0)
			pane = new emptyMazeNodePane();
		else
			pane = new jamMazeNodePane();

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(maze[m][n] == 0){
					maze[m][n] = 1;
					ImageView img = new ImageView("mazeImg/jam.png");
					img.setFitWidth(750.0 / maze[0].length);
					img.setFitHeight(750.0 / maze.length);
					
					Pane pane = mazePane.get(m).get(n);
					pane.getChildren().add(img);
				}else{
					maze[m][n] = 0;
					
					Pane pane = mazePane.get(m).get(n);
					pane.getChildren().clear();
				}
			}
		});

		mazePane.get(m).add(pane);
	}

	// �����������ӵ�GirdPane
	private void drawMazePane() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				this.add(this.mazePane.get(i).get(j), j, i);
	}

	// ����һ��·����ÿ���ڵ����һ��·��
	public void drawMazePath(Model.Node node) {
		String str = "";
		if (node.d != 0)
			str = "mazeImg/img" + node.d + ".png";
		else
			str = "mazeImg/destination.png";

		ImageView img = new ImageView(str);
		img.setFitWidth(750.0 / n);
		img.setFitHeight(750.0 / m);
		mazePane.get(node.m).get(node.n).getChildren().add(img);
		GridPane.setConstraints(mazePane.get(node.m).get(node.n), node.n,
				node.m);
	}

	class emptyMazeNodePane extends Pane implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		emptyMazeNodePane() {
			super();
			this.setPrefSize(750.0 / n, 750.0 / m);
			this.setStyle("-fx-background-color: #d2d3d7");
		}
	}

	class jamMazeNodePane extends Pane implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		jamMazeNodePane() {
			super();
			this.setPrefSize(750.0 / n, 750.0 / m);
			this.setStyle("-fx-background-color: #d2d3d7");
			ImageView img = new ImageView("mazeImg/jam.png");
			img.setFitWidth(750.0 / n);
			img.setFitHeight(750.0 / m);

			this.getChildren().add(img);
		}
	}
}
