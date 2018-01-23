package Question_1;

import java.util.ArrayList;

public class HFMCharCode {
	// hfm�ַ������ʼ��
	public static ArrayList<HFMTreeNode<Character>> Initialization(String str) {
		ArrayList<HFMTreeNode<Character>> list = new ArrayList<>();

		for (int i = 0; i < str.length(); i++) {
			boolean flag = true;

			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).e.compareTo(str.charAt(i)) == 0) {
					flag = false;
					list.get(j).addWeight(1);
					break;
				}
			}
			if (flag)
				list.add(new HFMTreeNode<Character>(str.charAt(i)));
		}
		
		return list;
	}

	// ͨ��hfm����ñ���
	public static void getHFMCode(HFMTreeNode<Character> rootNode,
			ArrayList<CodeNode<Character>> list, String str) {

		// ����hfm���ڵ���Ҷ�ӽڵ㣬�򴴽�����ڵ���󣬷������Ա�ֹͣ�ݹ�
		if (rootNode.isLeaf()) {
			CodeNode<Character> node = new CodeNode<>(rootNode.e, str);
			list.add(node);
		}
		// ���������Ѱ��Ҷ�ӽڵ�
		else {
			if (rootNode.left != null)
				getHFMCode(rootNode.left, list, str + "0"); // �ݹ����
			if (rootNode.right != null)
				getHFMCode(rootNode.right, list, str + "1"); // �ݹ����
		}
	}

	// ���ַ�������hfm����
	public static String getStringCode(
			ArrayList<CodeNode<Character>> codeCollections, String codeStr) {
		String str = ""; // ���ڱ��������hfm�ַ���

		for (int i = 0; i < codeStr.length(); i++) {
			for (CodeNode<Character> e : codeCollections)
				if (e.e == codeStr.charAt(i)) {
					str += e.codeStr;
					break;
				}
		}

		return str;
	}
	
	//��hfm�����ֶν��н���
	public static String Decoding(ArrayList<CodeNode<Character>> codeCollections, String decodeStr){
		String textStr = "";
		
		for(int i = 0; i < decodeStr.length(); i ++){
			for(int j = 0; j < codeCollections.size(); j++){
				int k = 0;
				boolean flag = true;
				for(k = 0; k < codeCollections.get(j).codeStr.length(); k++){
					if((i + codeCollections.get(j).codeStr.length()) > decodeStr.length()){
						flag = false;
						break;
					}else
						if(decodeStr.charAt(i + k) != codeCollections.get(j).codeStr.charAt(k))
							flag = false;
				}
				if(flag){
					textStr += codeCollections.get(j).e;
					i = i + k - 1;
					break;
				}
			}
		}
		
		return textStr;
	}
}
