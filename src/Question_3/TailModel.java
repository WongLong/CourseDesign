package Question_3;

import java.util.ArrayList;
import java.util.List;

public class TailModel {
	static int NUMBER_OF_NODES = 0;
	static int orderNum = 0;
	protected AbstractGraph<Integer>.Tree tree;
	 
	public TailModel(int orderNum, boolean change){
		TailModel.orderNum = orderNum;
		NUMBER_OF_NODES = (int) Math.pow(2, orderNum * orderNum);
		List<AbstractGraph.Edge> edges = getEdges(change);
		
		UnweightedGraph<Integer> graph = new UnweightedGraph<>(edges, NUMBER_OF_NODES);
		
		tree = graph.bfs(NUMBER_OF_NODES - 1);
	}
	
	private List<AbstractGraph.Edge> getEdges(boolean change){
		List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();
		
		for(int u = 0; u < NUMBER_OF_NODES; u++){
			for(int k = 0; k < orderNum * orderNum; k++){
				char[] node = getNode(u);
				if(node[k] == 'H'){
					int v = getFlippedNode(node, k, change);
					edges.add(new AbstractGraph.Edge(v, u));
				}
			}
		}
		
		return edges;
	}

	public int getFlippedNode(char[] node, int position, boolean change){
		int row = position / orderNum;
		int column = position % orderNum;

		if(change){
			filpACell(node, row, column);
			filpACell(node, row - 1, column - 1);
			filpACell(node, row + 1, column - 1);
			filpACell(node, row - 1, column + 1);
			filpACell(node, row + 1, column + 1);
		}else{
			filpACell(node, row, column);
			filpACell(node, row - 1, column);
			filpACell(node, row + 1, column);
			filpACell(node, row, column - 1);
			filpACell(node, row, column + 1);
		}
	
		return getIndex(node);
	}
	
	public void filpACell(char[] node, int row, int column){
		if(row >= 0 && row <= orderNum - 1 && column >= 0 && column <= orderNum - 1){
			if(node[row * orderNum + column] == 'H')
				node[row * orderNum + column] = 'T';
			else
				node[row * orderNum + column] = 'H';
		}
	}
	
	public static int getIndex(char[] node){
		int result = 0;
		
		for(int i = 0; i < orderNum * orderNum; i++){
			if(node[i] == 'T')
				result = result * 2 + 1;
			else
				result = result * 2 + 0;
		
		}
		return result;
	}
	
	public static char[] getNode(int index){
		int size = orderNum * orderNum;
		char[] result = new char[size];
		
		for(int i = 0; i < size; i++){
			int digit = index % 2;
			if(digit == 0)
				result[size - 1 - i] = 'H';
			else
				result[size - 1 - i] = 'T';
			index = index / 2;
		}
		return result;
	}
	
	public List<Integer> getShortestPath(int nodeIndex){
		return tree.getPath(nodeIndex);
	}
	
	public static void printNode(char[] node){
		for(int i = 0; i < orderNum * orderNum; i++){
			if(i % orderNum == 0)
				System.out.println();
			System.out.print(node[i]);
		}
		
		System.out.println();
	}
	
}
