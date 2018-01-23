package Question_2;

import java.util.ArrayList;

public class NewList {
	ArrayList<int[]> list = null;
	
	public NewList() {
		list = new ArrayList<>();
	}

	public void check() {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (compare(list.get(i), list.get(j))) {
					list.remove(j);
				}
			}
		}
	}

	private boolean compare(int[] num1, int[] num2) {
		for (int i = 0; i < num1.length; i++)
			if (num1[i] != num2[i])
				return false;

		return true;

	}
}
