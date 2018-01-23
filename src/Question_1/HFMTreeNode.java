package Question_1;

import java.io.Serializable;
import java.util.Comparator;


public class HFMTreeNode<E extends Comparable<E>> implements Comparator<HFMTreeNode<E>>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	E e;
	int weight;
	HFMTreeNode<E> left;
	HFMTreeNode<E> right;
	
	public HFMTreeNode(){
		
	}
	
	public HFMTreeNode(E e){
		this.e = e;
		this.weight = 1;
		this.left = null;
		this.right = null;
	}
	
	public HFMTreeNode(E e, int weight){
		this.e = e;
		this.weight = weight;
		this.left = null;
		this.right = null;
	}
	
	public HFMTreeNode(E e, int weight, HFMTreeNode<E> left, HFMTreeNode<E> right){
		this.weight = weight;
		this.left = left;
		this.right = right;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public void addWeight(int num){
		setWeight(this.weight + num);
	}
	
	//判断该节点是否是叶子节点
	public boolean isLeaf(){
		return left == null && right == null;
	}

	@Override
	public int compare(HFMTreeNode<E> o1, HFMTreeNode<E> o2) {
		return o1.weight - o2.weight;
	}	
}
