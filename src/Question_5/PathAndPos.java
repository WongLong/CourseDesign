package Question_5;

public class PathAndPos {
	int index;
	String south = "";
	String north = "";

	public PathAndPos(int locate, int index) {
		this.index = index;

		if (Model.isFamerNorth(locate))
			north += "Å©·ò ";
		else
			south += "Å©·ò ";

		if (Model.isWolfNorth(locate))
			north += "ÀÇ ";
		else
			south += "ÀÇ ";

		if (Model.isCabbageNorth(locate))
			north += "°×²Ë ";
		else
			south += "°×²Ë ";

		if (Model.isSheepNorth(locate))
			north += "Ñò ";
		else
			south += "Ñò ";

		
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
