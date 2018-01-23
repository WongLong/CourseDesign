package Question_6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraph<V extends Serializable> implements Graph<V>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<V> vertices;
	protected List<List<Integer>> neighbors;

	protected AbstractGraph(int[][] edges, V[] vertices) {
		this.vertices = new ArrayList<V>();
		for (int i = 0; i < vertices.length; i++)
			this.vertices.add(vertices[i]);

		createAdjacencyLists(edges, vertices.length);
	}

	protected AbstractGraph(List<Edge> edges, List<V> vertices) {
		this.vertices = vertices;

		createAdjacencyLists(edges, vertices.size());
	}

	@SuppressWarnings("unchecked")
	protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
		vertices = new ArrayList<V>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V) (new Integer(i)));
		}

		createAdjacencyLists(edges, numberOfVertices);
	}

	@SuppressWarnings("unchecked")
	protected AbstractGraph(int[][] edges, int numberOfVertices) {
		vertices = new ArrayList<V>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V) (new Integer(i)));
		}
		createAdjacencyLists(edges, numberOfVertices);
	}

	private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfVertices; i++) {
			neighbors.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			neighbors.get(u).add(v);
		}
	}

	private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfVertices; i++) {
			neighbors.add(new ArrayList<Integer>());
		}

		for (Edge edge : edges) {
			neighbors.get(edge.u).add(edge.v);
		}
	}

	@Override
	public int getSize() {
		return vertices.size();
	}

	@Override
	public List<V> getVertices() {
		return vertices;
	}

	@Override
	public V getVertex(int index) {
		return vertices.get(index);
	}

	@Override
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}

	@Override
	public List<Integer> getNeighbors(int index) {
		return neighbors.get(index);
	}

	@Override
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}

	@Override
	public int[][] getAdjacencyMatrix() {
		int[][] adjacencyMatrix = new int[getSize()][getSize()];
		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = 0; j < neighbors.get(i).size(); j++) {
				int v = neighbors.get(i).get(j);
				adjacencyMatrix[i][v] = 1;
			}
		}

		return adjacencyMatrix;
	}

	@Override
	public void printAdjacencyMatrix() {
		int[][] adjacencyMatrix = getAdjacencyMatrix();

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[0].length; j++) {
				System.out.print(adjacencyMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	@Override
	public void printEdges() {
		for (int u = 0; u < neighbors.size(); u++) {
			System.out.print("Vertex " + u + ": ");
			for (int j = 0; j < neighbors.get(u).size(); j++) {
				System.out.print("(" + u + ", " + neighbors.get(u).get(j)
						+ ") ");
			}
		}
	}

	@Override
	public AbstractGraph<V>.Tree dfs(int v) {
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1;

		boolean[] isVisited = new boolean[vertices.size()];

		dfs(v, parent, searchOrders, isVisited);

		return new Tree(v, parent, searchOrders);
	}

	private void dfs(int v, int[] parent, List<Integer> searchOrders,
			boolean[] isVisited) {
		searchOrders.add(v);
		isVisited[v] = true;

		for (int i : neighbors.get(v)) {
			if (!isVisited[i]) {
				parent[i] = v;
				dfs(i, parent, searchOrders, isVisited);
			}
		}
	}

	@Override
	public AbstractGraph<V>.Tree bfs(int v) {
		List<Integer> searchOrders = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1;

		java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v);
		isVisited[v] = true;

		while (!queue.isEmpty()) {
			int u = queue.poll();
			searchOrders.add(u);
			for (int w : neighbors.get(u)) {
				if (!isVisited[w]) {
					queue.offer(w);
					parent[w] = u;
					isVisited[w] = true;
				}
			}
		}

		return new Tree(v, parent, searchOrders);
	}

	public static class Edge implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int u;
		public int v;

		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}
	}

	public class Tree implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int root;
		private int[] parent;
		private List<Integer> searchOrders;

		public Tree(int root, int[] parent, List<Integer> searchOrders) {
			this.root = root;
			this.parent = parent;
			this.searchOrders = searchOrders;
		}

		public Tree(int root, int[] parent) {
			this.root = root;
			this.parent = parent;
		}

		public int getRoot() {
			return root;
		}

		public int getParent(int v) {
			return parent[v];
		}

		public List<Integer> getSearchOrders() {
			return searchOrders;
		}

		public int getNumberOfVerticesFound() {
			return searchOrders.size();
		}

		public void printTree() {
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for (int i = 0; i < parent.length; i++) {
				if (parent[i] != -1) {
					System.out.print("(" + vertices.get(parent[i]) + ", "
							+ vertices.get(i) + ") ");
				}
			}
			System.out.println();
		}
	}
}
