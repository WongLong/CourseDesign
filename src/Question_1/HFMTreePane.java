package Question_1;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class HFMTreePane extends Pane {
	HFMTree<Character> hfmTree;
	ArrayList<CodeNode<Character>> list = new ArrayList<>();

	public HFMTreePane(HFMTree<Character> hfmTree) {
		this.hfmTree = hfmTree;
		HFMCharCode.getHFMCode(hfmTree.rootNode, list, "");
		drawHFMTree();
	}

	public void drawHFMTree() {
		Circle root = new Circle(10 * 2 * (Math.pow(2, hfmTree.getHeight()) - 1), 50, 20, Color.LIGHTGRAY);
		root.setStroke(Color.BLACK);
		Text text = new Text(10 * 2 * (Math.pow(2, hfmTree.getHeight()) - 1), 50, hfmTree.rootNode.weight + "");
		this.getChildren().addAll(root, text);
		drawHFMNode(null, root, hfmTree.rootNode);
	}

	public void drawHFMNode(Circle rootParents, Circle root,
			HFMTreeNode<Character> rootNode) {
		int parWidth = 0;
		int width = (int) root.getCenterX();
		int height = (int) root.getCenterY();

		if (rootParents != null)
			parWidth = (int) rootParents.getCenterX();

		if (rootNode.isLeaf()) {
			Text text = new Text(root.getCenterX(), root.getCenterY() + 40, ""
					+ rootNode.e);
			root.setFill(Color.LIGHTPINK);
			this.getChildren().add(text);
			
			return;
		}

		if (rootNode.left != null) {
			Circle circle = null;

			if (rootParents == null)
				circle = new Circle(width / 2, height + 40, 20, Color.LIGHTGRAY);
			else
				circle = new Circle(width - Math.abs(width - parWidth) / 2,
						height + 40, 20, Color.LIGHTGRAY);
			circle.setStroke(Color.BLACK);
			Line line = new Line(root.getCenterX(), root.getCenterY(),
					circle.getCenterX(), circle.getCenterY());

			Text text1 = new Text(circle.getCenterX(), circle.getCenterY(),
					rootNode.left.weight + "");
			this.getChildren().addAll(line, circle, text1);
			drawHFMNode(root, circle, rootNode.left);
		}

		if (rootNode.right != null) {
			Circle circle = null;

			if (rootParents == null)
				circle = new Circle(width / 2 + width, height + 40, 20,
						Color.LIGHTGRAY);
			else
				circle = new Circle(Math.abs(width - parWidth) / 2 + width,
						height + 40, 20, Color.LIGHTGRAY);
			circle.setStroke(Color.BLACK);
			Line line = new Line(root.getCenterX(), root.getCenterY(),
					circle.getCenterX(), circle.getCenterY());

			Text text1 = new Text(circle.getCenterX(), circle.getCenterY(),
					rootNode.right.weight + "");
			this.getChildren().addAll(line, circle, text1);
			drawHFMNode(root, circle, rootNode.right);
		}
	}

}
