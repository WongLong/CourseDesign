package Question_5;

import java.util.ArrayList;
import java.util.List;

public class Model {
	private static final int NUMBER_OF_NODES = 16;
	private static final int[] noMove = { 3, 5, 7, 8, 10, 12 };
	UnweightedGraph<Integer> graph;
	protected AbstractGraph<Integer>.Tree tree;

	public Model() {
		List<AbstractGraph.Edge> edges = getEdges();
		graph = new UnweightedGraph<>(edges, NUMBER_OF_NODES);

		tree = graph.bfs(NUMBER_OF_NODES - 1);
	}

	private List<AbstractGraph.Edge> getEdges() {
		List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();

		for (int u = 0; u < NUMBER_OF_NODES; u++) {
			if (isSafety(u)) {
				ArrayList<Integer> moveTo = getNeighbor(u);
				for (int v : moveTo) {
					edges.add(new AbstractGraph.Edge(u, v));
				}
			}
		}

		return edges;
	}

	private ArrayList<Integer> getNeighbor(int locate) {
		ArrayList<Integer> list = new ArrayList<>();
		int sheep = getSheepPos(locate);
		int cabbage = getCabbagePos(locate);
		int wolf = getWolfPos(locate);
		int farmer = getFarmerPos(locate);

		if (farmer == sheep) {
			if (farmer == 0)
				farmer = 1;
			else
				farmer = 0;
			int newNode = farmer * 2 * 2 * 2 + wolf * 2 * 2 + cabbage * 2
					+ farmer;

			if (isSafety(newNode))
				list.add(newNode);

			farmer = sheep;
		}

		if (farmer == cabbage) {
			if (farmer == 0)
				farmer = 1;
			else
				farmer = 0;
			int newNode = farmer * 2 * 2 * 2 + wolf * 2 * 2 + farmer * 2
					+ sheep;

			if (isSafety(newNode))
				list.add(newNode);

			farmer = cabbage;
		}

		if (farmer == wolf) {
			if (farmer == 0)
				farmer = 1;
			else
				farmer = 0;

			int newNode = farmer * 2 * 2 * 2 + farmer * 2 * 2 + cabbage * 2
					+ sheep;

			if (isSafety(newNode))
				list.add(newNode);

			farmer = wolf;
		}

		if (farmer == 0)
			farmer = 1;
		else
			farmer = 0;

		int newNode = farmer * 2 * 2 * 2 + wolf * 2 * 2 + cabbage * 2 + sheep;

		if (isSafety(newNode))
			list.add(newNode);

		return list;
	}

	private static int getFarmerPos(int locate) {
		return locate / 2 / 2 / 2;
	}

	private static int getWolfPos(int locate) {
		return locate / 2 / 2 % 2;
	}

	private static int getCabbagePos(int locate) {
		return locate / 2 % 2;
	}

	private static int getSheepPos(int locate) {
		return locate % 2;
	}

	public static boolean isFamerNorth(int locate) {
		return getFarmerPos(locate) == 1;
	}

	public static boolean isWolfNorth(int locate) {
		return getWolfPos(locate) == 1;
	}

	public static boolean isCabbageNorth(int locate) {
		return getCabbagePos(locate) == 1;
	}

	public static boolean isSheepNorth(int locate) {
		return getSheepPos(locate) == 1;
	}

	public String getNode(int index) {
		return Integer.toBinaryString(index);
	}

	public int getIndex(String binaryStr) {
		return new Integer(binaryStr);
	}

	public List<Integer> getShortesPath(int nodeIndex) {
		return tree.getPath(nodeIndex);
	}

	public List<List<Integer>> getAllPath() {
		List<List<Integer>> allPath = new ArrayList<>();

		int[] route = new int[16];
		for (int i = 0; i < route.length; i++)
			route[i] = -1;

		getAllPath(allPath, 0, route, new ArrayList<Integer>());

		return allPath;
	}

	/**
	 * 利用回溯来找到所有路径。
	 * 也可以不使用回溯，而将递归调用里面的route变成route.clone()，因为直接用route传参，是引用变量的传参。
	 * 后面改变route里面的数值会影响到后面的遍历，所以需要new一个新的数组
	 * 
	 * @param allPath
	 * @param index
	 * @param route
	 * @param path
	 */
	private void getAllPath(List<List<Integer>> allPath, int index, int[] route,
			List<Integer> path) {
		route[index] = index;
		path.add(index);

		if (route[15] != -1)
			allPath.add(path);
		else {
			for (Integer i : graph.getNeighbors(index)) {
				if (route[i] == -1)
					getAllPath(allPath, i, route, new ArrayList<Integer>(path));
			}
		}

		route[index] = -1; // 回溯
	}

	private boolean isSafety(int locate) {
		for (int i : noMove)
			if (i == locate)
				return false;
		return true;
	}
}
