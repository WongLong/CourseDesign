package Question_1;

import java.io.Serializable;
import java.util.ArrayList;

public class HFMTree<E extends Comparable<E>> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HFMTreeNode<E> rootNode;

	// 通过线性表构造hfm树
	public HFMTree(ArrayList<HFMTreeNode<E>> list) {
		creatHFMTree(list);
	}

	// 通过数组构造hfm树
	public HFMTree(HFMTreeNode<E>[] array) {
		creatHFMTree(array);
	}

	// 数组创建hfm树
	private void creatHFMTree(HFMTreeNode<E>[] array) {
		ArrayList<HFMTreeNode<E>> list = new ArrayList<>();

		for (HFMTreeNode<E> e : array)
			// 将数组转换成线性表
			list.add(e);

		creatHFMTree(list); // 调用线性表创建hfm树方法
	}

	// 线性表创建hfm树
	private void creatHFMTree(ArrayList<HFMTreeNode<E>> list) {
		while (list.size() != 1) {
			list.sort(new HFMTreeNode<E>());

			HFMTreeNode<E> left = list.get(0);
			HFMTreeNode<E> right = list.get(1);
			HFMTreeNode<E> newNode = new HFMTreeNode<E>(null, left.weight
					+ right.weight, left, right);
			list.remove(left);
			list.remove(right);
			list.add(newNode);
		}
		rootNode = list.get(0);
	}

	// 获得该hfm树的高度
	public int getHeight(){
		return getHeight(rootNode);
	}
	
	private int getHeight(HFMTreeNode<E> rootNode) {
		if (rootNode.isLeaf())
			return 0;
		else
			return 1 + Math.max(getHeight(rootNode.left),
					getHeight(rootNode.right));
	}

}
