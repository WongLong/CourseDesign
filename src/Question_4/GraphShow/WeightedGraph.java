package Question_4.GraphShow;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class WeightedGraph<E> extends AbstractGraph<E> {
	private List<PriorityQueue<WeightedEdge>> queues;

	public WeightedGraph(int[][] edges, E[] vertices) {
		super(edges, vertices);
		createQueues(edges, vertices.length);
	}

	public WeightedGraph(int[][] edges, int numberOfVertices) {
		super(edges, numberOfVertices);
		createQueues(edges, numberOfVertices);
	}

	public WeightedGraph(List<WeightedEdge> edges, List<E> vertices) {
		super((List) edges, vertices);
		createQueues(edges, vertices.size());
	}

	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
		super((List) edges, numberOfVertices);
		createQueues(edges, numberOfVertices);
	}

	private void createQueues(int[][] edges, int numberOfVertices) {
		queues = new ArrayList<PriorityQueue<WeightedEdge>>();
		for(int i = 0; i < numberOfVertices; i++)
			queues.add(new PriorityQueue<WeightedEdge>());
		
		
		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			int weight = edges[i][2];

			queues.get(u).offer(new WeightedEdge(u, v, weight));
		}
	}

	private void createQueues(List<WeightedEdge> edges, int numberOfVertices) {
		queues = new ArrayList<PriorityQueue<WeightedEdge>>();

		for (int i = 0; i < numberOfVertices; i++)
			queues.add(new PriorityQueue<WeightedEdge>());

		for (WeightedEdge edge : edges)
			queues.get(edge.u).offer(edge);

	}

	public void printWeightedEdges() {
		for (int i = 0; i < queues.size(); i++) {

			System.out.print("Vertex " + i + ": ");
			for (WeightedEdge edge : queues.get(i))
				System.out.print("(" + edge.u + ", " + edge.v + ", "
						+ edge.weight + ") ");

			System.out.println();
		}
	}

	public MST getMinimumSpanningTree() {
		return getMinimumSpanningTree(0);
	}

	public MST getMinimumSpanningTree(int startingIndex) {
		List<Integer> T = new ArrayList<>();
		T.add(startingIndex);

		int numberOfVertices = vertices.size();
		int[] parent = new int[numberOfVertices];

		for (int i = 0; i < parent.length; i++)
			parent[i] = -1;
		int totalWeight = 0;

		List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

		while (T.size() < numberOfVertices) {
			int v = -1;
			double smallestWeight = Double.MAX_VALUE;
			for (int u : T) {
				while (!queues.get(u).isEmpty()
						&& T.contains(queues.get(u).peek().v)) {
					queues.get(u).remove();
				}

				if (queues.get(u).isEmpty())
					continue;

				WeightedEdge edge = queues.get(u).peek();

				if (edge.weight < smallestWeight) {
					v = edge.v;
					smallestWeight = edge.weight;
					parent[v] = u;
				}
			}

			T.add(v);
			totalWeight += smallestWeight;
		}

		return new MST(startingIndex, parent, T, totalWeight);
	}

	private List<PriorityQueue<WeightedEdge>> deepClone(
			List<PriorityQueue<WeightedEdge>> queues) {
		List<PriorityQueue<WeightedEdge>> copiedQueues = new ArrayList<PriorityQueue<WeightedEdge>>();

		for (int i = 0; i < queues.size(); i++) {
			copiedQueues.add(new PriorityQueue<WeightedEdge>());
			for (WeightedEdge e : queues.get(i)) {
				copiedQueues.get(i).add(e);
			}
		}

		return copiedQueues;
	}

	public class MST extends Tree {
		private int totalWeight;

		public MST(int root, int[] parent, List<Integer> searchOrder,
				int totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
		}

		public int getTotalWeight() {
			return totalWeight;
		}
	}

	public ShortestPathTree getShortestPath(int sourceIndex) {
		List<Integer> T = new ArrayList<>();
		T.add(sourceIndex);

		int numberOfVertices = vertices.size();

		int[] parent = new int[numberOfVertices];
		parent[sourceIndex] = -1;

		double[] costs = new double[numberOfVertices];
		for (int i = 0; i < costs.length; i++)
			costs[i] = Double.MAX_VALUE;
		costs[sourceIndex] = 0;

		List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

		while (T.size() < numberOfVertices) {
			int v = -1;
			double smallestCost = Double.MAX_VALUE;
			for (int u : T) {
				while (!queues.get(u).isEmpty()
						&& T.contains(queues.get(u).peek().v))
					queues.get(u).remove();

				if (queues.get(u).isEmpty())
					continue;

				WeightedEdge e = queues.get(u).peek();
				if (costs[u] + e.weight < smallestCost) {
					v = e.v;
					smallestCost = costs[u] + e.weight;
					parent[v] = u;
				}
			}

			T.add(v);
			costs[v] = smallestCost;
		}

		return new ShortestPathTree(sourceIndex, parent, T, costs);
	}

	public class ShortestPathTree extends Tree {
		private double[] costs;

		public ShortestPathTree(int source, int[] parent,
				List<Integer> searchOrder, double[] costs) {
			super(source, parent, searchOrder);
			this.costs = costs;
		}
		
		public void printAllPaths(){
			System.out.print("All shortest paths from " +
					vertices.get(getRoot()) + " are:");
			for(int i = 0; i < costs.length; i++){
				printPath(i);
				System.out.println("(cost: " + costs[i] + ")");
			}
		}
	}
	
	public List<PriorityQueue<WeightedEdge>> getWeightedEdges(){
		return queues;
	}
}
