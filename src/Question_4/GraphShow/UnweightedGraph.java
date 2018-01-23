package Question_4.GraphShow;

import java.util.List;

public class UnweightedGraph<V> extends AbstractGraph<V> {

	protected UnweightedGraph(int[][] edges, V[] vertices) {
		super(edges, vertices);
	}

	protected UnweightedGraph(List<Edge> edges, List<V> vertices) {
		super(edges, vertices);
	}

	protected UnweightedGraph(List<Edge> edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}

	protected UnweightedGraph(int[][] edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}

}
