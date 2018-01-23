package Question_2;

import java.util.ArrayList;

import Question_2.Card;
import Question_2.Util;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ImagePane extends HBox {
	ImageView[] img = new ImageView[4];
	ArrayList<Card> list = null;
	
	public ImagePane() {
		getRandomCard();
	}

	public ImagePane(double arg0) {
		super(arg0);
		getRandomCard();
	}

	public ImagePane(Node... arg0) {
		super(arg0);
		getRandomCard();
	}

	public ImagePane(double arg0, Node... arg1) {
		super(arg0, arg1);
		getRandomCard();
		
	}
	
	public void getRandomCard(){
		ArrayList<Card> list = Util.getCardFromFile();
		ArrayList<Card> randomList = new ArrayList<>();
		
		while(randomList.size() != 4){
			int num = (int)(Math.random() * 52);
			
			boolean flag = true;
			for(int i = 0; i < randomList.size(); i++)
				if(randomList.get(i).equals(list.get(num))){
					flag = false;
					break;
				}
			if(flag)	
				randomList.add(list.get(num));
		}
		this.list = randomList;
		creatImgPane(randomList);
	}

	private void creatImgPane(ArrayList<Card> randomList){
		this.getChildren().clear();
		
		for(int i = 0; i < img.length; i++){
			img[i] = new ImageView(randomList.get(i).src);
			img[i].setFitWidth(100);
			img[i].setFitHeight(140);
		}
		
		this.setPrefSize(500, 140);
		this.getChildren().addAll(new SpaceLabel(" "), img[0], new SpaceLabel(" "), img[1], new SpaceLabel(" "), img[2], new SpaceLabel(" "), img[3], new SpaceLabel(" "));
	}
}
