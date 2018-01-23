package Question_5;

public class PathAndPos {
	int index;
	String south = "";
	String north = "";

	public PathAndPos(int locate, int index) {
		this.index = index;

		if (Model.isFamerNorth(locate))
			north += "ũ�� ";
		else
			south += "ũ�� ";

		if (Model.isWolfNorth(locate))
			north += "�� ";
		else
			south += "�� ";

		if (Model.isCabbageNorth(locate))
			north += "�ײ� ";
		else
			south += "�ײ� ";

		if (Model.isSheepNorth(locate))
			north += "�� ";
		else
			south += "�� ";

		
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSouth() {
		return south;
	}

	public void setSouth(String south) {
		this.south = south;
	}

	public String getNorth() {
		return north;
	}

	public void setNorth(String north) {
		this.north = north;
	}

}
