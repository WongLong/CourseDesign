package Question_3;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CoinPane extends GridPane{
	char[] coin;
	int orderNum;
	
	CoinPane(char[] coin, int orderNum, ArrayList<Integer> different){
		this.coin = coin;
		this.orderNum = orderNum;
		creatCoinPane(different);
		this.setGridLinesVisible(true);	
	}
		
	
	private void creatCoinPane(ArrayList<Integer> different){
		Label[] label = new Label[coin.length];
		this.setPrefSize(20 * orderNum, 50 * orderNum);
		
		for(int i = 0; i < label.length; i++){
			label[i] = new Label(coin[i] + "");
			label[i].setPrefSize(20, 50);
			label[i].setAlignment(Pos.CENTER);
			label[i].setFont(Font.font(20));
			this.add(label[i], i / orderNum, i % orderNum);
		}
		
		if(different != null)
			for(int i = 0; i < label.length; i++)
				if(different.contains(i))
					label[i].setTextFill(Color.RED);
	}
}
