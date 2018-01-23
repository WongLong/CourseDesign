package Question_6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Question_6.AbstractGraph.Edge;

/**
 * ���Թ���������ͼ�����⣬���Թ�ת����m*n���ڵ㣬�Թ�ÿ��λ�õ����궼����ת���� һ��Integer���͵����֣��ȸ������
 * ������*���������+�����ꡣ���Integer���͵��� �־���ͼ�����һ���ڵ㣬�����Թ��Ĺ��̾����ҵ���0�ڵ� �� m*n-1�ڵ��·��
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
	
	// ����ͼ
	public void creatGraph(){
		List<AbstractGraph.Edge> edges = getEdges();// �õ���ͼ�����б�
		graph = new UnweightedGraph<>(edges, NUMBER_OF_VERTICES);// ͨ�����붨�㴴����
	}

	// Ϊͼ����edges
	private List<AbstractGraph.Edge> getEdges() {
		List<AbstractGraph.Edge> edges = new ArrayList<>();

		for (int u = 0; u < NUMBER_OF_VERTICES; u++) {
			ArrayList<Integer> list = getNeighbor(u);

			for (int v : list)
				edges.add(new Edge(u, v));
		}

		return edges;
	}

	// �õ��ýڵ��ܹ�����������ڵ�
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
	 * �ҵ��Թ����һ��·��
	 * 
	 * @return
	 */
	public ArrayList<Node> getPath() {
		mazeStack<Node> nodeStack = toNodeStack(getPath(0));// �ҵ��Թ�·����ת������Node��Ԫ���Թ�ջ

		if (nodeStack.isEmpty())
			return null; // ���Թ��޽�

		ArrayList<Node> list = new ArrayList<>();
		while (!nodeStack.isEmpty()) {
			Node node = nodeStack.pop();

			if (nodeStack.Size() > 0)
				node.setNextPos(nodeStack.peek()); // ���øýڵ���һ����Ҫ�ߵ�λ��

			list.add(node);
		}

		return list;
	}

	// ��Integer���͵�����ת����Node���͵�����
	private mazeStack<Node> toNodeStack(mazeStack<Integer> stack) {
		mazeStack<Node> nodeStack = new mazeStack<Node>();

		while (!stack.isEmpty()) {
			Node node = new Node(stack.pop());
			nodeStack.push(node);
		}

		return nodeStack;
	}

	// ����Թ��ķǵݹ��㷨
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
	 * �õ��Թ�������·��
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

	// ��ͼ�ڵ�ת�����Թ��ڵ�
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

	// ����Թ�������·������Ȳ��ҵݹ��㷨
	private void getAllPath(List<List<Integer>> allPath, int index,
			int[] visited, List<Integer> path) {
		visited[index] = index;
		path.add(index);

		if (visited[NUMBER_OF_VERTICES - 1] != -1)
			allPath.add(path);
		else {
			for (Integer i : graph.getNeighbors(index)) {
				if (visited[i] == -1)
					getAllPath(allPath, i, visited, new ArrayList<>(path)); // ���ñ��������⣬����newһ���µ�path��Ϊ�����·��Ӱ������·��
			}
		}

		visited[index] = -1; // ����
	}

	// �жϸýڵ��Ƿ������ϡ�������ϵ�����Ϊ���ýڵ�����б߽ڵ㶼�Լ�������
	private boolean isEnd(ArrayList<Integer> neighobor, int[] visited) {
		int count = 0;
		for (int num : neighobor)
			if (visited[num] != -1)
				count++;

		return count == neighobor.size();
	}

	// ��������Թ�
	public void randomCreat() {
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[i].length; j++)
				if (i == 0 && j == 0 || i == maze.length - 1
						&& j == maze[i].length - 1)
					maze[i][j] = 0;
				else
					maze[i][j] = (int) (Math.random() * 2);
	}

	// Ĭ�ϴ����Թ�
	public void defaultCreat() {
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[i].length; j++)
				maze[i][j] = 0;
	}

	// ��ӡ�Թ�
	public void print() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j] + " ");
			System.out.println();
		}
	}
	
	// �õ��Թ�����
	public int[][] getMaze(){
		return maze;
	}

	// �Թ��ڵ�
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
