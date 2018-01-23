package Question_4.GraphShow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import Question_4.GraphShow.AbstractGraph.Edge;
import Question_4.GraphShow.AbstractGraph.WeightedEdge;
import Question_4.GraphShow.GraphNode.LineNode;
import Question_4.GraphShow.GraphNode.VerticeNode;

public class GraphPane<E extends Comparable<E>> extends Pane {
	UnweightedGraph<E> unweightedGraph; // ��Ȩͼ
	protected List<Edge> edges = new ArrayList<>(); // ��
	WeightedGraph<E> weightedGraph; // ��Ȩͼ
	protected List<E> vertices = new ArrayList<>(); // ����
	private List<GraphNode> graphNodes = new ArrayList<GraphNode>(); // ͼ�ڵ�
	private Stack<Line> lineStack = new Stack<>(); // �϶��߲��������ջ
	private static final int RADIUS = 5; // ͼ�ڵ�뾶
	private boolean isWeightGraph;

	public GraphPane(boolean isWeightGraph) {
		this.isWeightGraph = isWeightGraph;

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent e) {
				if (e.isAltDown()) {
					Label label = new Label("Enter a key: ");
					label.setPrefHeight(40);
					final TextField text = new TextField();
					BorderPane bp1 = new BorderPane();
					bp1.setPrefHeight(40);
					bp1.setCenter(text);

					Button ensure = new Button("ȷ��");
					Button cancel = new Button("ȡ��");

					BorderPane bp2 = new BorderPane();
					bp2.setLeft(cancel);
					bp2.setRight(ensure);

					HBox head = new HBox();
					head.getChildren().addAll(label, bp1);

					VBox body = new VBox();
					body.getChildren().addAll(head, bp2);

					Scene scene = new Scene(body);

					final Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("���붥��");
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.show();

					cancel.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							stage.close();
						}
					});

					ensure.setOnAction(new EventHandler<ActionEvent>() {
						@SuppressWarnings("unchecked")
						@Override
						public void handle(ActionEvent arg0) {
							String str = text.getText();
							if (str == null) {
								Util.Alert("�������󣡣���");
								return;
							}
							addVertices(e.getX(), e.getY(), (E) str);
							stage.close();
						}
					});
				}
			}
		});

	}

	// ���ͼ�ڵ�
	public void addVertices(double x, double y, E e) {
		final Circle circle = new Circle(x, y, RADIUS); // ����ͼ�ڵ�
		final Text text = new Text(x - 8, y - 8, e.toString()); // ���Ʒ�������

		vertices.add(e); // ��Ӷ���
		GraphNode gNode = new GraphNode(new VerticeNode(circle, text));
		graphNodes.add(gNode); // ����ͼ�ڵ�
		this.getChildren().addAll(circle, text); // �����չ��

		/**
		 * ͨ�����İ��¡��϶����ſ��¼���ʵ���ֶ����Ʊ�
		 */
		// ��갴���¼�
		circle.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				recoverDefault();

				if (e.isShiftDown()) {
					// ����϶��¼�
					circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e1) {
							final double x = e1.getX(); // ��ȡ��ǰ�������
							final double y = e1.getY();

							circle.setCenterX(x); // ���ĵ�ǰ���������
							circle.setCenterY(y);

							int index = Util.circleIndexOf(graphNodes, circle);
							GraphNode gNode = graphNodes.get(index);
							Text text = gNode.verticeNode.text;
							text.setX(x - 8); // ����text������
							text.setY(y - 8);

							for (final LineNode lNode : gNode.edgeNodes) {
								Edge edge = lNode.edges[0];
								GraphNode node = null;
								if (edge.u == index)
									node = graphNodes.get(edge.v);
								else
									node = graphNodes.get(edge.u);

								getChildren().remove(lNode.line);
								lNode.line = new Line(x, y,
										node.verticeNode.circle.getCenterX(),
										node.verticeNode.circle.getCenterY());					
								getChildren().add(lNode.line);
								// ����CTRL+��������Ƴ������¼�
								lNode.line.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (e.isControlDown()) { // CTRL����
											MouseButton mbt = e.getButton();
											if (mbt == MouseButton.PRIMARY) { // �������������
												getChildren().remove(lNode.line); // �Ƴ�����
												removeLineNode(lNode.edges[0].u, lNode.edges[0].v, lNode.line);// �Ƴ��߽ڵ�

												if (isWeightGraph)
													getChildren().remove(lNode.weightText); // �Ƴ��ñߵ�Ȩ
											}
										}
									}
								});
								
								// ����Ȩֵ��λ��
								if (isWeightGraph) {
									Text weightText = lNode.weightText;
									weightText
											.setX((x + node.verticeNode.circle
													.getCenterX()) / 2);
									weightText
											.setY((y + node.verticeNode.circle
													.getCenterY()) / 2);
								}
							}
							
							// ���ſ��¼�
							circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent arg0) {
									
								}
							});
							
						}
					});
				} else {
					// ����϶��¼�
					circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(final MouseEvent e1) {
							if (!lineStack.isEmpty())
								getChildren().remove(lineStack.pop()); // ���ϴ��϶�ʱ�������ߴ�������Ƴ�

							final double x = e1.getX(); // ����������
							final double y = e1.getY();

							Line line = new Line(x, y, circle.getCenterX(),
									circle.getCenterY()); // ������
							lineStack.add(line); // ����ջ
							getChildren().add(lineStack.peek()); // ��ʾ����

							// ���ſ��¼�
							circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e2) {
									if (!lineStack.isEmpty())
										getChildren().remove(lineStack.pop()); // �Ƴ��϶�ʱ��������

									int index = getEndNodeIndex(circle, x, y); // �ж����ſ��ǵ������Ƿ��ڴ�����ͼ�ڵ���
									if (index != -1)
										if (!graphNodes.get(index).verticeNode.circle
												.equals(circle))
											addEdge(Util.circleIndexOf(
													graphNodes, circle), index);
								}
							});
						}
					});
				}
			}
		});
	}

	// �ж����ſ�ʱ����Ƿ���ͼ�ڵ��ϣ����򷵻ظ�ͼ�ڵ���±꣬���򷵻�-1
	private int getEndNodeIndex(Circle circle, double x, double y) {
		for (int i = 0; i < graphNodes.size(); i++) {
			double distance = Math.sqrt(Math.pow(
					(x - graphNodes.get(i).verticeNode.circle.getCenterX()), 2)
					+ Math.pow((y - graphNodes.get(i).verticeNode.circle
							.getCenterY()), 2));
			if (distance <= RADIUS * 1.5)
				return i;
		}

		return -1;
	}

	// ����2��ͼ�ڵ�
	private void connectNode(final int index1, final int index2) {
		Circle circle1 = graphNodes.get(index1).verticeNode.circle; // ��ø��±��Ӧ��ͼ�ڵ��Բ����
		Circle circle2 = graphNodes.get(index2).verticeNode.circle; // ��ø��±��Ӧ��ͼ�ڵ��Բ����

		final Line line = new Line(circle1.getCenterX(), circle1.getCenterY(),
				circle2.getCenterX(), circle2.getCenterY()); // ��2���ڵ�֮�����
		this.getChildren().add(line);

		if (isWeightGraph) {
			Label label = new Label("Enter Weight: ");
			label.setPrefHeight(40);
			final TextField text = new TextField();
			BorderPane bp1 = new BorderPane();
			bp1.setPrefHeight(40);
			bp1.setCenter(text);

			Button ensure = new Button("ȷ��");
			Button cancel = new Button("ȡ��");

			BorderPane bp2 = new BorderPane();
			bp2.setLeft(cancel);
			bp2.setRight(ensure);

			HBox head = new HBox();
			head.getChildren().addAll(label, bp1);

			VBox body = new VBox();
			body.getChildren().addAll(head, bp2);

			Scene scene = new Scene(body);

			final Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("����Ȩֵ");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();

			cancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					getChildren().remove(line);
					stage.close();
				}
			});

			ensure.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					try {
						Double weight = new Double(text.getText());
						if (weight <= 0)
							throw new NumberFormatException();

						addData(index1, index2, line, weight);
						stage.close();
					} catch (NumberFormatException e) {
						Util.Alert("�������󣡣���");
						stage.close();
						getChildren().remove(line);
						return;
					}
				}
			});
		} else
			addData(index1, index2, line, null);

	}

	// ��ӱ�֮�������Ӧ������
	private void addData(final int index1, final int index2, final Line line,
			final Double weight) {
		Circle circle1 = graphNodes.get(index1).verticeNode.circle; // ��ø��±��Ӧ��ͼ�ڵ��Բ����
		Circle circle2 = graphNodes.get(index2).verticeNode.circle; // ��ø��±��Ӧ��ͼ�ڵ��Բ����

		Edge[] edge = new Edge[2];
		final Text weightText = new Text(
				(circle1.getCenterX() + circle2.getCenterX()) / 2,
				(circle1.getCenterY() + circle2.getCenterY()) / 2, weight + "");
		// ����Ҽ��������Ȩֵ
		weightText.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.SECONDARY){
					final TextField input = new TextField();
					input.setPrefSize(50, 30);
					input.setLayoutX(weightText.getX() - 5);
					input.setLayoutY(weightText.getY() - 20);
					getChildren().add(input);
					
					input.setOnKeyPressed(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent e) {
							if(KeyCode.ESCAPE == e.getCode())
								getChildren().remove(input);
							else if(KeyCode.ENTER == e.getCode()){
								try{
									Double newWeight = new Double(input.getText());
									weightText.setText(newWeight + "");
									changeWeight(index1, index2, newWeight);
									getChildren().remove(input);
								}catch(NumberFormatException e1){
									Util.Alert("�������󣡣���");
									getChildren().remove(input);
									return;
								}
							}
						}
					});
				}
			}
		});
		
		
		if (weight != null) {
			edge[0] = new WeightedEdge(index1, index2, weight);
			edge[1] = new WeightedEdge(index2, index1, weight);
			getChildren().add(weightText);
		} else {
			edge[0] = new Edge(index1, index2);
			edge[1] = new Edge(index2, index1);
		}

		edges.add(edge[0]);
		edges.add(edge[1]); // ��ӱ�
		LineNode lineNode = new LineNode(line, edge, weightText); // �����߽ڵ�

		graphNodes.get(index1).addEdgeNode(lineNode); // Ϊ���±��Ӧ��ͼ�ڵ�����߽ڵ�
		graphNodes.get(index2).addEdgeNode(lineNode); // Ϊ���±��Ӧ��ͼ�ڵ�����߽ڵ�

		// ����CTRL+��������Ƴ������¼�
		line.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.isControlDown()) { // CTRL����
					MouseButton mbt = e.getButton();
					if (mbt == MouseButton.PRIMARY) { // �������������
						getChildren().remove(line); // �Ƴ�����
						removeLineNode(index1, index2, line);// �Ƴ��߽ڵ�

						if (weight != null)
							getChildren().remove(weightText); // �Ƴ��ñߵ�Ȩ
					}
				}
			}
		});
	}

	// ��ӱ�
	private void addEdge(int index1, int index2) {
		if (!Util.isExistEdge(edges, index1, index2)) { // �Ƿ���ڸñ�
			connectNode(index1, index2); // ����2���ڵ㣬������
		}
	}

	// �Ƴ��߽ڵ�
	private void removeLineNode(int index1, int index2, Line line) {
		for(LineNode lNode : graphNodes.get(index1).edgeNodes){
			if(lNode.line.equals(line)){
				graphNodes.get(index1).edgeNodes.remove(lNode);
				break;
			}
		}
		
		for(LineNode lNode : graphNodes.get(index2).edgeNodes){
			if(lNode.line.equals(line)){
				graphNodes.get(index2).edgeNodes.remove(lNode);
				break;
			}
		}
		
		removeEdge(index1, index2); // ɾ����
	}

	// �Ƴ���
	private void removeEdge(int u, int v) {
		for (int i = 0; i < edges.size(); i++)
			if (edges.get(i).u == u && edges.get(i).v == v
					|| edges.get(i).u == v && edges.get(i).v == u) {
				edges.remove(i);
				i--;
			}
	}

	// �ж��Ƿ���ڸö���
	public boolean isExistVertice(E e) {
		for (E element : vertices)
			if (element.compareTo(e) == 0)
				return true;

		return false;
	}

	// ����Ȩֵ
	private void changeWeight(int index1, int index2, double newWeight){
		for(Edge edge : edges)
			if(edge.u == index1 && edge.v == index2 || edge.u == index2 && edge.v == index1)
				((WeightedEdge)edge).weight = newWeight;
	}
	
	// ������Ȩͼ
	private void creatUnweightedGraph() {
		unweightedGraph = new UnweightedGraph<>(edges, vertices);
	}

	// ��ô�Ȩͼ�����·��
	public void getShortestPath(E start, E end) {
		creatWeightedGraph();

		int startIndex = weightedGraph.getIndex(start);
		int endIndex = weightedGraph.getIndex(end);
		List<E> shortestPath = weightedGraph.getShortestPath(endIndex).getPath(
				startIndex);

		List<Integer> path = new ArrayList<>();
		List<Edge> visitedEdge = new ArrayList<>();
		for (int i = 0; i < shortestPath.size(); i++) {
			int u = weightedGraph.getIndex(shortestPath.get(i));
			path.add(u);

			if (i != shortestPath.size() - 1) {
				int v = weightedGraph.getIndex(shortestPath.get(i + 1));
				visitedEdge.add(new Edge(u, v));
			}
		}

		drawPath(path, visitedEdge);
	}

	// չʾ��С������
	public void displayMST(E start) {
		creatWeightedGraph();

		int startIndex = weightedGraph.getIndex(start);

		List<Edge> visitedEdge = new ArrayList<>();

		WeightedGraph<E>.MST mst = weightedGraph
				.getMinimumSpanningTree(startIndex);
		List<Integer> path = mst.getSearchOrders();

		for (int i = 1; i < path.size(); i++)
			visitedEdge.add(new Edge(mst.getParent(path.get(i)), path.get(i)));

		drawPath(path, visitedEdge);
	}

	// ������Ȩͼ
	private void creatWeightedGraph() {
		List<WeightedEdge> weightedEdges = new ArrayList<>();

		for (int i = 0; i < edges.size(); i++)
			weightedEdges.add((WeightedEdge) edges.get(i));

		weightedGraph = new WeightedGraph<E>(weightedEdges, vertices);
	}

	// ������ȱ���
	public void dfs(E e) {
		creatUnweightedGraph();

		int index = unweightedGraph.getIndex(e); // ��ø����ݵĶ����±�
		List<Integer> path = new ArrayList<>(); // ���·��
		int[] visited = new int[unweightedGraph.vertices.size()]; // �����������
		List<Edge> visitedEdge = new ArrayList<>(); // ��ŷ��ʵı�
		for (int i = 0; i < visited.length; i++)
			visited[i] = -1; // ��ʼ����������

		dfs(index, visited, path, visitedEdge);
		drawPath(path, visitedEdge);
	}

	private void dfs(int index, int[] visited, List<Integer> path,
			List<Edge> visitedEdge) {
		visited[index] = index; // ���øö����ѱ�����
		path.add(index); // ���·��

		List<Integer> neighbors = unweightedGraph.getNeighbors(index); // ��ȡ�ö�����ھ�
		for (Integer neighbor : neighbors)
			// �����ھ�
			if (visited[neighbor] == -1) { // ���ھӶ���û�����ʣ�����ʸýڵ�
				visitedEdge.add(new Edge(index, neighbor)); // ��ӷ��ʵı�
				dfs(neighbor, visited, path, visitedEdge);
			}

	}

	// ������ȱ���
	public void bfs(E e) {
		creatUnweightedGraph();

		int index = unweightedGraph.getIndex(e);
		List<Integer> path = new ArrayList<>();
		int[] visited = new int[unweightedGraph.vertices.size()];
		for (int i = 0; i < visited.length; i++)
			visited[i] = -1;

		visited[index] = index;
		path.add(index);

		Queue<Integer> queue = new LinkedList<>(); // �ö���ѭ��ʵ�ֹ�����ȱ���
		queue.offer(index);

		List<Edge> visitedEdge = new ArrayList<>(); // ��ŷ��ʵı�
		while (!queue.isEmpty()) {
			int vertice = queue.poll();

			List<Integer> neighbors = unweightedGraph.getNeighbors(vertice);
			for (Integer neighbor : neighbors)
				if (visited[neighbor] == -1) {
					visited[neighbor] = neighbor;
					visitedEdge.add(new Edge(vertice, neighbor)); // ��ӷ��ʵı�
					path.add(neighbor);
					queue.offer(neighbor);
				}
		}

		drawPath(path, visitedEdge);
	}

	// ͨ������·�������ʵıߣ�����·��
	private void drawPath(final List<Integer> path, final List<Edge> visitedEdge) {
		recoverDefault();

		drawPath(path.remove(0), null);

		final Timeline animation = new Timeline(new KeyFrame(
				Duration.millis(1500), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						if (!visitedEdge.isEmpty())
							drawPath(path.remove(0), visitedEdge.remove(0));
					}
				}));
		animation.setCycleCount(path.size());
		animation.play();
	}

	// ͨ������·�����ݶ�������
	private void drawPath(final int index, final Edge visitedEdge) {
		final GraphNode node = graphNodes.get(index);
		final Circle circle = node.verticeNode.circle;
		circle.setFill(Color.RED);

		Timeline animation = new Timeline(new KeyFrame(Duration.millis(500),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						getChildren().remove(circle);

						Timeline animation = new Timeline(new KeyFrame(
								Duration.millis(250),
								new EventHandler<ActionEvent>() {
									public void handle(ActionEvent e) {
										getChildren().add(circle);
									}
								}));
						animation.setCycleCount(1);
						animation.play();
					}
				}));
		animation.setCycleCount(2);
		animation.play();
		animation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (visitedEdge == null)
					return;
				Line line = Util.getLine(graphNodes, visitedEdge);
				line.setStroke(Color.RED);
			}
		});
	}

	// �ָ�Ĭ����ɫ
	private void recoverDefault() {
		for (GraphNode node : graphNodes) {
			node.verticeNode.circle.setFill(Color.BLACK);
			for (LineNode line : node.edgeNodes)
				line.line.setStroke(Color.BLACK);
		}
	}

}
