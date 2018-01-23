package Question_2;

import java.io.Serializable;

public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int num;
	String src;

	public Card(int num, String src){
		this.num = num;
		this.src = src;
	}
	
	@Override
	public boolean equals(Object obj){
		Card card = (Card) obj;
		if((card.num == this.num) && (card.src.compareTo(this.src) == 0))
			return true;
		return false;
	}
}
