package Question_3;

import java.util.List;

public class Versions_1 {
	public static void main(String[] args) {

		char[] initialNode = { 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H',
				'H', 'H', 'H', 'H', 'H', 'H', 'H' };

		TailModel model = new TailModel(4, false);
		List<Integer> path = model.getShortestPath(TailModel
				.getIndex(initialNode));

		System.out.println("The steps to flip the coins are ");
		for (int i = 0; i < path.size(); i++) {
			TailModel.printNode(TailModel.getNode(path.get(i).intValue()));
		}
	}

}
