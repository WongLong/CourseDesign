package Question_6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Question_6.AbstractGraph.Edge;

/**
 * 将迷宫问题抽象成图的问题，将迷宫转换成m*n个节点，迷宫每个位置的坐标都可以转换成 一个Integer类型的数字，既该坐标的
 * 纵坐标*数组的行数+横坐标。这个Integer类型的数 字就是图里面的一个节点，而解迷宫的过程就是找到从0节点 到 m*n-1节点的路径
 * 
 * @author 10527
 * 
 */
public class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][] maze = null;
	static int NUMBER_OF_VERTICES;
	protected UnweightedGraph<Integer> graph;

	public Model(int m, int n, boolean flag) {
		maze = new int[m][n];
		
		if(flag)
			randomCreat();
		else
			defaultCreat();
		
		NUMBER_OF_VERTICES = m * n;
	}
	
	// 创建图
	public void creatGraph(){
		List<AbstractGraph.Edge> edges = getEdges();// 得到该图的所有边
		graph = new UnweightedGraph<>(edges, NUMBER_OF_VERTICES);// 通过边与定点创建边
	}

	// 为图创建edges
	private List<AbstractGraph.Edge> getEdges() {
		List<AbstractGraph.Edge> edges = new ArrayList<>();

		for (int u = 0; u < NUMBER_OF_VERTICES; u++) {
			ArrayList<Integer> list = getNeighbor(u);

			for (int v : list)
				edges.add(new Edge(u, v));
		}

		return edges;
	}

	// 得到该节点能够到达的其他节点
	private ArrayList<Integer> getNeighbor(int index) {
		int m = index / maze[0].length;
		int n = index % maze[0].length;

		ArrayList<Integer> edges = new ArrayList<>();

		if (maze[m][n] == 0) {
			if (n + 1 >= 0 && n + 1 < maze[0].length)
				if (maze[m][n + 1] == 0)
					edges.add(m * maze[0].length + n + 1);

			if (m + 1 >= 0 && m + 1 < maze.length)
				if (maze[m + 1][n] == 0)
					edges.add((m + 1) * maze[0].length + n);
			
			if (m - 1 >= 0 && m - 1 < maze.length)
				if (maze[m - 1][n] == 0)
					edges.add((m - 1) * maze[0].length + n);

			if (n - 1 >= 0 && n - 1 < maze[0].length)
				if (maze[m][n - 1] == 0)
					edges.add(m * maze[0].length + n - 1);
		}

		return edges;
	}

	/**
	 * 找到迷宫解的一条路径
	 * 
	 * @return
	 */
	public ArrayList<Node> getPath() {
		mazeStack<Node> nodeStack = toNodeStack(getPath(0));// 找到迷宫路径并转换成以Node单元的迷宫栈

		if (nodeStack.isEmpty())
			return null; // 该迷宫无解

		ArrayList<Node> list = new ArrayList<>();
		while (!nodeStack.isEmpty()) {
			Node node = nodeStack.pop();

			if (nodeStack.Size() > 0)
				node.setNextPos(nodeStack.peek()); // 设置该节点下一步需要走的位置

			list.add(node);
		}

		return list;
	}

	// 将Integer类型的数据转换成Node类型的数据
	private mazeStack<Node> toNodeStack(mazeStack<Integer> stack) {
		mazeStack<Node> nodeStack = new mazeStack<Node>();

		while (!stack.isEmpty()) {
			Node node = new Node(stack.pop());
			nodeStack.push(node);
		}

		return nodeStack;
	}

	// 求解迷宫的非递归算法
	private mazeStack<Integer> getPath(int root) {
		mazeStack<Integer> stack = new mazeStack<>();

		int[] visited = new int[NUMBER_OF_VERTICES];
		for (int i = 0; i < visited.length; i++)
			visited[i] = -1;

		stack.push(root);

		while (!stack.isEmpty()) {
			int index = stack.peek();
			visited[index] = index;

			if (index == NUMBER_OF_VERTICES - 1)
				break;
			else {
				ArrayList<Integer> neighobor = getNeighbor(index);
				if (isEnd(neighobor, visited))
					stack.pop();
				else
					for (int num : neighobor)
						if (visited[num] == -1) {
							stack.push(num);
							break;
						}
			}

		}

		return stack;
	}

	/**
	 * 得到迷宫的所有路径
	 * 
	 * @return
	 */
	public List<List<Node>> getAllPath() {
		List<List<Integer>> allPath = new ArrayList<List<Integer>>();
		List<List<Node>> nodePath = new ArrayList<List<Node>>();

		int[] visited = new int[NUMBER_OF_VERTICES];
		for (int i = 0; i < visited.length; i++)
			visited[i] = -1;

		getAllPath(allPath, 0, visited, new ArrayList<Integer>());

		for (List<Integer> list : allPath)
			nodePath.add(toMazeNode(list));

		return nodePath;
	}

	// 将图节点转换成迷宫节点
	private List<Node> toMazeNode(List<Integer> list) {
		List<Node> nodeList = new ArrayList<>();

		while (!list.isEmpty()) {
			int index = list.remove(0);

			Node node = new Node(index);
			if (list.size() > 0)
				node.setNdextPos(list.get(0));

			nodeList.add(node);
		}

		return nodeList;
	}

	// 求解迷宫的所有路径，深度查找递归算法
	private void getAllPath(List<List<Integer>> allPath, int index,
			int[] visited, List<Integer> path) {
		visited[index] = index;
		path.add(index);

		if (visited[NUMBER_OF_VERTICES - 1] != -1)
			allPath.add(path);
		else {
			for (Integer i : graph.getNeighbors(index)) {
				if (visited[i] == -1)
					getAllPath(allPath, i, visited, new ArrayList<>(path)); // 引用变量的问题，这里new一个新的path是为了添加路径影响后面的路径
			}
		}

		visited[index] = -1; // 回溯
	}

	// 判断该节点是否访问完毕。访问完毕的条件为：该节点的所有边节点都以及被访问
	private boolean isEnd(ArrayList<Integer> neighobor, int[] visited) {
		int count = 0;
		for (int num : neighobor)
			if (visited[num] != -1)
				count++;

		return count == neighobor.size();
	}

	// 随机创建迷宫
	public void randomCreat() {
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[i].length; j++)
				if (i == 0 && j == 0 || i == maze.length - 1
						&& j == maze[i].length - 1)
					maze[i][j] = 0;
				else
					maze[i][j] = (int) (Math.random() * 2);
	}

	// 默认创建迷宫
	public void defaultCreat() {
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[i].length; j++)
				maze[i][j] = 0;
	}

	// 打印迷宫
	public void print() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j] + " ");
			System.out.println();
		}
	}
	
	// 得到迷宫数组
	public int[][] getMaze(){
		return maze;
	}

	// 迷宫节点
	class Node implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int index;
		int m = 0;
		int n = 0;
		int d = 0;

		public Node(int m, int n) {
			this.m = m;
			this.n = n;
			this.index = m * maze.length + n;
		}

		public Node(int index) {
			this.index = index;
			this.m = index / maze[0].length;
			this.n = index % maze[0].length;
		}

		public void setD(int d) {
			this.d = d;
		}

		public int getD() {
			return d;
		}

		public int setNextPos(Node node) {
			if (node.m == this.m - 1 && this.n == node.n)
				this.setD(1);

			else if (node.m == this.m && this.n + 1 == node.n)
				this.setD(2);

			else if (node.n == this.n && node.m == this.m + 1)
				this.setD(3);

			else if (this.m == node.m && this.n - 1 == node.n)
				this.setD(4);

			return getD();
		}

		public int setNdextPos(int index) {
			int m = index / maze[0].length;
			int n = index % maze[0].length;

			if (m == this.m - 1 && this.n == n)
				this.setD(1);

			else if (m == this.m && this.n + 1 == n)
				this.setD(2);

			else if (n == this.n && m == this.m + 1)
				this.setD(3);

			else if (this.m == m && this.n - 1 == n)
				this.setD(4);

			return getD();
		}

		public boolean hasNext() {
			return d != 0;
		}
	}
}
