package Question_1;

import java.io.Serializable;
import java.util.ArrayList;

public class HFMTree<E extends Comparable<E>> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HFMTreeNode<E> rootNode;

	// ͨ�����Ա���hfm��
	public HFMTree(ArrayList<HFMTreeNode<E>> list) {
		creatHFMTree(list);
	}

	// ͨ�����鹹��hfm��
	public HFMTree(HFMTreeNode<E>[] array) {
		creatHFMTree(array);
	}

	// ���鴴��hfm��
	private void creatHFMTree(HFMTreeNode<E>[] array) {
		ArrayList<HFMTreeNode<E>> list = new ArrayList<>();

		for (HFMTreeNode<E> e : array)
			// ������ת�������Ա�
			list.add(e);

		creatHFMTree(list); // �������Ա���hfm������
	}

	// ���Ա���hfm��
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

	// ��ø�hfm���ĸ߶�
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
