package Question_6;

import java.io.Serializable;
import java.util.List;

public class UnweightedGraph<V extends Serializable> extends AbstractGraph<V> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
