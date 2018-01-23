package Question_5;

import java.util.ArrayList;
import java.util.List;

import Question_5.AbstractGraph.Edge;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AnimationPane extends HBox {
	List<Integer> path;
	List<Edge> edgePath = new ArrayList<>();
	List<ImageView> img = new ArrayList<>();
	ImageView boatImg = new ImageView("pass_river/boat.jpg");
	VBox northBank;
	VBox southBank;
	Pane middle;

	public AnimationPane(List<Integer> path) {
		this.path = path;
		for (int i = 0; i < path.size() - 1; i++)
			this.edgePath.add(new Edge(path.get(i), path.get(i + 1)));
		
		img.add(new ImageView("pass_river/famer.png"));
		img.add(new ImageView("pass_river/wolf.png"));
		img.add(new ImageView("pass_river/cabbage.jpg"));
		img.add(new ImageView("pass_river/sheep.png"));
		
		createPane();
	}

	private void createPane() {
		southBank = new VBox();
		southBank.setPrefSize(150, 600);

		for (int i = 0; i < img.size(); i++) {
			BorderPane bp = new BorderPane();
			bp.setPrefSize(150, 150);
			bp.setStyle("-fx-background-color: SeaGreen");
			img.get(i).setFitHeight(150);
			img.get(i).setFitWidth(150);
			bp.setCenter(img.get(i));

			southBank.getChildren().add(bp);

		}

		northBank = new VBox();
		northBank.setPrefSize(150, 600);

		for (int i = 0; i < 4; i++) {
			BorderPane bp = new BorderPane();
			bp.setPrefSize(150, 150);
			bp.setStyle("-fx-background-color: SeaGreen");

			northBank.getChildren().add(bp);
		}

		middle = new Pane();
		middle.setPrefSize(900, 600);
		middle.setStyle("-fx-background-color: SkyBlue");

		boatImg.setFitHeight(150);
		boatImg.setFitWidth(300);
		boatImg.setX(0);
		boatImg.setY(150);
		middle.getChildren().add(boatImg);

		this.getChildren().addAll(southBank, middle, northBank);
	}

	public void startAnimation() {
		img.get(0).setX(150);
		img.get(0).setY(150);
		middle.getChildren().add(img.get(0));

		
		Timeline animation = new Timeline(new KeyFrame(Duration
				.millis(1500), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Edge edge = edgePath.remove(0);
				startGo(edge, !Model.isFamerNorth(edge.u));
			}
		}));
		animation.setCycleCount(edgePath.size());
		animation.play();
	}

	// 开始
	private void startGo(Edge edge, boolean toNorth) {
		boarding(edge, toNorth);
	}

	// 上船
	private void boarding(Edge edge, boolean toNorth) {
		int index = getObject(edge);

		if (index != -1) {
			ImageView img = this.img.get(index);
			if (toNorth) {
				img.setX(0);
				img.setY(150);
			} else {
				img.setX(600);
				img.setY(150);
			}
			
			middle.getChildren().add(img);
			createAnimation(img, toNorth, edge);
		} else
			createAnimation(null, toNorth, edge);
	}

	// 下船
	private void disembark(ImageView img, boolean toNorth, Edge edge) {
		if(toNorth){
			ImageView newImg = new ImageView(img.getImage());
			newImg.setFitHeight(150);
			newImg.setFitWidth(150);
			
			int index = this.img.indexOf(img);
			this.img.remove(img);
			this.img.add(index, newImg);
			
			BorderPane bp = (BorderPane)northBank.getChildren().get(index);
			bp.setCenter(newImg);
			middle.getChildren().remove(img);
				
			if(edge.v == 15){
				bp = (BorderPane)northBank.getChildren().get(0);
				bp.setCenter(this.img.get(0));
			}		
		}
		else{
			ImageView newImg = new ImageView(img.getImage());
			newImg.setFitHeight(150);
			newImg.setFitWidth(150);
			
			int index = this.img.indexOf(img);
			this.img.remove(img);
			this.img.add(index, newImg);
			
			BorderPane bp = (BorderPane)southBank.getChildren().get(index);
			bp.setCenter(newImg);
			middle.getChildren().remove(img);
		}
	}

	// 得到上船对象
	private int getObject(Edge edge) {
		String str1 = "000" + Integer.toBinaryString(edge.u);
		String str2 = "000" + Integer.toBinaryString(edge.v);

		String start = str1.substring(str1.length() - 4, str1.length());
		String end = str2.substring(str2.length() - 4, str2.length());

		int index = -1;
		for (int i = 0; i < start.length(); i++)
			if (start.charAt(i) != end.charAt(i) && i != 0)
				index = i;

		return index;
	}

	// 创建动画
	private void createAnimation(final ImageView img, final boolean toNorth, final Edge edge) {
		if (toNorth) {
			Timeline timeline1 = new Timeline();
			timeline1.setCycleCount(1);
			KeyValue kv1 = new KeyValue(boatImg.xProperty(), 600);
			KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
			timeline1.getKeyFrames().add(kf1);

			Timeline timeline2 = new Timeline();
			timeline2.setCycleCount(1);
			KeyValue kv2 = new KeyValue(this.img.get(0).xProperty(), 750);
			KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv2);
			timeline2.getKeyFrames().add(kf2);

			Timeline timeline3 = null;
			if (img != null) {
				timeline3 = new Timeline();
				timeline3.setCycleCount(1);
				KeyValue kv3 = new KeyValue(img.xProperty(), 600);
				KeyFrame kf3 = new KeyFrame(Duration.millis(1000), kv3);
				timeline3.getKeyFrames().add(kf3);
			}

			ParallelTransition parallelTransition = null;
			if (img == null)
				parallelTransition = new ParallelTransition(timeline1, timeline2);
			else
				parallelTransition = new ParallelTransition(timeline1, timeline2, timeline3);
			
			parallelTransition.setCycleCount(1);
			parallelTransition.play();

			parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (img != null)
						disembark(img, toNorth, edge);
				}
			});
		} else {
			Timeline timeline1 = new Timeline();
			timeline1.setCycleCount(1);
			KeyValue kv1 = new KeyValue(boatImg.xProperty(), 0);
			KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
			timeline1.getKeyFrames().add(kf1);

			Timeline timeline2 = new Timeline();
			timeline2.setCycleCount(1);
			KeyValue kv2 = new KeyValue(this.img.get(0).xProperty(), 150);
			KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv2);
			timeline2.getKeyFrames().add(kf2);

			Timeline timeline3 = null;
			if (img != null) {
				timeline3 = new Timeline();
				timeline3.setCycleCount(1);
				KeyValue kv3 = new KeyValue(img.xProperty(), 0);
				KeyFrame kf3 = new KeyFrame(Duration.millis(1000), kv3);
				timeline3.getKeyFrames().add(kf3);
			}

			ParallelTransition parallelTransition = null;
			if (img == null)
				parallelTransition = new ParallelTransition(timeline1, timeline2);
			else
				parallelTransition = new ParallelTransition(timeline1, timeline2, timeline3);
			
			parallelTransition.setCycleCount(1);
			parallelTransition.play();

			parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (img != null)
						disembark(img, toNorth, edge);
				}
			});
		}
	}
}
