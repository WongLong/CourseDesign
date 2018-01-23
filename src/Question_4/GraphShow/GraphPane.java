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
	UnweightedGraph<E> unweightedGraph; // 无权图
	protected List<Edge> edges = new ArrayList<>(); // 边
	WeightedGraph<E> weightedGraph; // 带权图
	protected List<E> vertices = new ArrayList<>(); // 顶点
	private List<GraphNode> graphNodes = new ArrayList<GraphNode>(); // 图节点
	private Stack<Line> lineStack = new Stack<>(); // 拖动线操作保存的栈
	private static final int RADIUS = 5; // 图节点半径
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

					Button ensure = new Button("确定");
					Button cancel = new Button("取消");

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
					stage.setTitle("输入顶点");
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
								Util.Alert("输入有误！！！");
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

	// 添加图节点
	public void addVertices(double x, double y, E e) {
		final Circle circle = new Circle(x, y, RADIUS); // 绘制图节点
		final Text text = new Text(x - 8, y - 8, e.toString()); // 绘制泛型数据

		vertices.add(e); // 添加顶点
		GraphNode gNode = new GraphNode(new VerticeNode(circle, text));
		graphNodes.add(gNode); // 存入图节点
		this.getChildren().addAll(circle, text); // 面板上展现

		/**
		 * 通过鼠标的按下、拖动、放开事件，实现手动绘制边
		 */
		// 鼠标按下事件
		circle.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				recoverDefault();

				if (e.isShiftDown()) {
					// 鼠标拖动事件
					circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e1) {
							final double x = e1.getX(); // 获取当前鼠标坐标
							final double y = e1.getY();

							circle.setCenterX(x); // 更改当前顶点的坐标
							circle.setCenterY(y);

							int index = Util.circleIndexOf(graphNodes, circle);
							GraphNode gNode = graphNodes.get(index);
							Text text = gNode.verticeNode.text;
							text.setX(x - 8); // 更改text的坐标
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
								// 设置CTRL+鼠标主键移除该线事件
								lNode.line.setOnMouseClicked(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										if (e.isControlDown()) { // CTRL按下
											MouseButton mbt = e.getButton();
											if (mbt == MouseButton.PRIMARY) { // 并按下鼠标主键
												getChildren().remove(lNode.line); // 移除该线
												removeLineNode(lNode.edges[0].u, lNode.edges[0].v, lNode.line);// 移除线节点

												if (isWeightGraph)
													getChildren().remove(lNode.weightText); // 移除该边的权
											}
										}
									}
								});
								
								// 调整权值框位置
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
							
							// 鼠标放开事件
							circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent arg0) {
									
								}
							});
							
						}
					});
				} else {
					// 鼠标拖动事件
					circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(final MouseEvent e1) {
							if (!lineStack.isEmpty())
								getChildren().remove(lineStack.pop()); // 将上次拖动时创建的线从面板中移除

							final double x = e1.getX(); // 获得鼠标坐标
							final double y = e1.getY();

							Line line = new Line(x, y, circle.getCenterX(),
									circle.getCenterY()); // 创建线
							lineStack.add(line); // 存入栈
							getChildren().add(lineStack.peek()); // 显示该线

							// 鼠标放开事件
							circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e2) {
									if (!lineStack.isEmpty())
										getChildren().remove(lineStack.pop()); // 移除拖动时创建的线

									int index = getEndNodeIndex(circle, x, y); // 判断鼠标放开是的坐标是否在创建的图节点上
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

	// 判断鼠标放开时鼠标是否在图节点上，是则返回该图节点的下标，否则返回-1
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

	// 连接2个图节点
	private void connectNode(final int index1, final int index2) {
		Circle circle1 = graphNodes.get(index1).verticeNode.circle; // 获得该下标对应的图节点的圆属性
		Circle circle2 = graphNodes.get(index2).verticeNode.circle; // 获得该下标对应的图节点的圆属性

		final Line line = new Line(circle1.getCenterX(), circle1.getCenterY(),
				circle2.getCenterX(), circle2.getCenterY()); // 画2个节点之间的线
		this.getChildren().add(line);

		if (isWeightGraph) {
			Label label = new Label("Enter Weight: ");
			label.setPrefHeight(40);
			final TextField text = new TextField();
			BorderPane bp1 = new BorderPane();
			bp1.setPrefHeight(40);
			bp1.setCenter(text);

			Button ensure = new Button("确定");
			Button cancel = new Button("取消");

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
			stage.setTitle("输入权值");
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
						Util.Alert("输入有误！！！");
						stage.close();
						getChildren().remove(line);
						return;
					}
				}
			});
		} else
			addData(index1, index2, line, null);

	}

	// 添加边之后添加相应的数据
	private void addData(final int index1, final int index2, final Line line,
			final Double weight) {
		Circle circle1 = graphNodes.get(index1).verticeNode.circle; // 获得该下标对应的图节点的圆属性
		Circle circle2 = graphNodes.get(index2).verticeNode.circle; // 获得该下标对应的图节点的圆属性

		Edge[] edge = new Edge[2];
		final Text weightText = new Text(
				(circle1.getCenterX() + circle2.getCenterX()) / 2,
				(circle1.getCenterY() + circle2.getCenterY()) / 2, weight + "");
		// 鼠标右键点击更改权值
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
									Util.Alert("输入有误！！！");
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
		edges.add(edge[1]); // 添加边
		LineNode lineNode = new LineNode(line, edge, weightText); // 创建线节点

		graphNodes.get(index1).addEdgeNode(lineNode); // 为该下标对应的图节点添加线节点
		graphNodes.get(index2).addEdgeNode(lineNode); // 为该下标对应的图节点添加线节点

		// 设置CTRL+鼠标主键移除该线事件
		line.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.isControlDown()) { // CTRL按下
					MouseButton mbt = e.getButton();
					if (mbt == MouseButton.PRIMARY) { // 并按下鼠标主键
						getChildren().remove(line); // 移除该线
						removeLineNode(index1, index2, line);// 移除线节点

						if (weight != null)
							getChildren().remove(weightText); // 移除该边的权
					}
				}
			}
		});
	}

	// 添加边
	private void addEdge(int index1, int index2) {
		if (!Util.isExistEdge(edges, index1, index2)) { // 是否存在该边
			connectNode(index1, index2); // 连接2个节点，创建线
		}
	}

	// 移除线节点
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
		
		removeEdge(index1, index2); // 删除边
	}

	// 移除边
	private void removeEdge(int u, int v) {
		for (int i = 0; i < edges.size(); i++)
			if (edges.get(i).u == u && edges.get(i).v == v
					|| edges.get(i).u == v && edges.get(i).v == u) {
				edges.remove(i);
				i--;
			}
	}

	// 判断是否存在该顶点
	public boolean isExistVertice(E e) {
		for (E element : vertices)
			if (element.compareTo(e) == 0)
				return true;

		return false;
	}

	// 更改权值
	private void changeWeight(int index1, int index2, double newWeight){
		for(Edge edge : edges)
			if(edge.u == index1 && edge.v == index2 || edge.u == index2 && edge.v == index1)
				((WeightedEdge)edge).weight = newWeight;
	}
	
	// 创建无权图
	private void creatUnweightedGraph() {
		unweightedGraph = new UnweightedGraph<>(edges, vertices);
	}

	// 获得带权图的最短路径
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

	// 展示最小生成树
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

	// 创建有权图
	private void creatWeightedGraph() {
		List<WeightedEdge> weightedEdges = new ArrayList<>();

		for (int i = 0; i < edges.size(); i++)
			weightedEdges.add((WeightedEdge) edges.get(i));

		weightedGraph = new WeightedGraph<E>(weightedEdges, vertices);
	}

	// 深度优先遍历
	public void dfs(E e) {
		creatUnweightedGraph();

		int index = unweightedGraph.getIndex(e); // 获得该数据的顶点下标
		List<Integer> path = new ArrayList<>(); // 存放路径
		int[] visited = new int[unweightedGraph.vertices.size()]; // 顶点访问数组
		List<Edge> visitedEdge = new ArrayList<>(); // 存放访问的边
		for (int i = 0; i < visited.length; i++)
			visited[i] = -1; // 初始化访问数组

		dfs(index, visited, path, visitedEdge);
		drawPath(path, visitedEdge);
	}

	private void dfs(int index, int[] visited, List<Integer> path,
			List<Edge> visitedEdge) {
		visited[index] = index; // 设置该顶点已被访问
		path.add(index); // 添加路径

		List<Integer> neighbors = unweightedGraph.getNeighbors(index); // 获取该顶点的邻居
		for (Integer neighbor : neighbors)
			// 遍历邻居
			if (visited[neighbor] == -1) { // 该邻居顶点没被访问，则访问该节点
				visitedEdge.add(new Edge(index, neighbor)); // 添加访问的边
				dfs(neighbor, visited, path, visitedEdge);
			}

	}

	// 广度优先遍历
	public void bfs(E e) {
		creatUnweightedGraph();

		int index = unweightedGraph.getIndex(e);
		List<Integer> path = new ArrayList<>();
		int[] visited = new int[unweightedGraph.vertices.size()];
		for (int i = 0; i < visited.length; i++)
			visited[i] = -1;

		visited[index] = index;
		path.add(index);

		Queue<Integer> queue = new LinkedList<>(); // 用队列循环实现广度优先遍历
		queue.offer(index);

		List<Edge> visitedEdge = new ArrayList<>(); // 存放访问的边
		while (!queue.isEmpty()) {
			int vertice = queue.poll();

			List<Integer> neighbors = unweightedGraph.getNeighbors(vertice);
			for (Integer neighbor : neighbors)
				if (visited[neighbor] == -1) {
					visited[neighbor] = neighbor;
					visitedEdge.add(new Edge(vertice, neighbor)); // 添加访问的边
					path.add(neighbor);
					queue.offer(neighbor);
				}
		}

		drawPath(path, visitedEdge);
	}

	// 通过访问路径及访问的边，绘制路径
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

	// 通过单个路径数据动画绘制
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

	// 恢复默认颜色
	private void recoverDefault() {
		for (GraphNode node : graphNodes) {
			node.verticeNode.circle.setFill(Color.BLACK);
			for (LineNode line : node.edgeNodes)
				line.line.setStroke(Color.BLACK);
		}
	}

}
