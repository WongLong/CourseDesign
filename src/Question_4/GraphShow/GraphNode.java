package Question_4.GraphShow;

import java.util.ArrayList;
import java.util.List;

import Question_4.GraphShow.AbstractGraph.Edge;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphNode {
	VerticeNode verticeNode;
	List<LineNode> edgeNodes = new ArrayList<>();

	public GraphNode(VerticeNode verticeNode){
		this.verticeNode = verticeNode;
	}
	
	public GraphNode(VerticeNode verticeNode, List<LineNode> edgeNodes){
		this.verticeNode = verticeNode;
		this.edgeNodes = edgeNodes;
	}
	
	public void addEdgeNode(LineNode node){
		this.edgeNodes.add(node);
	}
	
	static class LineNode{
		Line line;
		Text weightText;
		Edge[] edges;
		
		public LineNode(Line line, Edge[] edge, Text weightText){
			this.line = line;
			this.edges = edge;
			this.weightText = weightText;
		}
	}
	
	static class VerticeNode{
		Circle circle;
		Text text;
		
		public VerticeNode(Circle circle, Text text){
			this.circle = circle;
			this.text = text;
		}
	}
}
