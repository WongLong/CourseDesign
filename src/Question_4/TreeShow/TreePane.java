package Question_4.TreeShow;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TreePane<E extends Comparable<E>> extends Pane {
	BinarySearchTree<E> tree = new BinarySearchTree<>();
	private int radius = 20;
	private int vGap = 50;

	public TreePane(BinarySearchTree<E> tree) {
		this.tree = tree;
	}

	public void drawTreePane(E e) {
		this.getChildren().clear();
		drawTreePane(tree.getRoot(), 1000 / 2, 30, 1000 / 4, e);
	}

	private void drawTreePane(BinarySearchTree.TreeNode<E> root, double x,
			double y, double hGap, E e) {
		if (root != null) {
			Circle circle = new Circle(x, y, radius, Color.WHITE);
			circle.setOpacity(0.5);
			circle.setStroke(Color.BLACK);
			Text text = new Text(x - 4, y + 4, root.element + "");
			
			if(e != null)
				if (root.element.compareTo(e) == 0) {
					circle.setStroke(Color.RED);
					text.setFill(Color.RED);
				}
						
			this.getChildren().addAll(circle, text);

			if (root.left != null) {
				connectLeft(x, y, x - hGap, y + vGap);
				drawTreePane(root.left, x - hGap, y + vGap, hGap / 2, e);
			}

			if (root.right != null) {
				connectRight(x, y, x + hGap, y + vGap);
				drawTreePane(root.right, x + hGap, y + vGap, hGap / 2, e);
			}
		}
	}

	private void connectLeft(double x1, double y1, double x2, double y2) {
		double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
		double x11 = (x1 - radius * (x1 - x2) / d);
		double y11 = (y1 + radius * vGap / d);
		double x21 = (x2 + radius * (x1 - x2) / d);
		double y21 = (y2 - radius * vGap / d);
		Line line = new Line(x11, y11, x21, y21);
		this.getChildren().add(line);
	}

	private void connectRight(double x1, double y1, double x2, double y2) {
		double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
		double x11 = (x1 + radius * (x2 - x1) / d);
		double y11 = (y1 + radius * vGap / d);
		double x21 = (x2 - radius * (x2 - x1) / d);
		double y21 = (y2 - radius * vGap / d);
		Line line = new Line(x11, y11, x21, y21);
		this.getChildren().add(line);
	}

	public void search(E e) {
		drawTreePane(e);
	}

	public void insert(E e) {
		tree.insert(e);
		drawTreePane(null);
	}

	public void remove(E e) {
		tree.delete(e);
		drawTreePane(null);
	}

}
