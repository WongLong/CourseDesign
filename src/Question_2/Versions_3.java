package Question_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Versions_3 {
	public static void main(String[] args) {
		Date startDate = new Date();
		System.out.println("startTime: " + startDate.getHours() + ":"
				+ startDate.getMinutes() + ":" + startDate.getSeconds());
		
		ArrayList<Number_24> numList = new ArrayList<>();
		double total = 0;

		int[] saveNum = new int[4];
		for (int i = 1; i < 14; i++) {
			saveNum[0] = i;
			for (int j = i; j < 14; j++) {
				saveNum[1] = j;
				for (int k = j; k < 14; k++) {
					saveNum[2] = k;
					for (int l = k; l < 14; l++) {
						saveNum[3] = l;

						int[] newNum = saveNum.clone();
						Number_24 obj = new Number_24(newNum);
						numList.add(obj);

						setWeight(obj);
						total += obj.weight;
					}
				}
			}
		}

		System.out.println("Total number of combinations is " + total);
		int count = 0;

		ArrayList<char[]> saveOP = new ArrayList<>();// ���ڴ�����в�������ϵ����Ա�
		Util.getAllOP(0, new char[3], saveOP);// ������в��������
		Util.insertBracket(saveOP, saveOP.size());// ������е�������ϲ�����ڲ�����������Ա�

		for (Number_24 obj : numList) {
			ArrayList<Integer> allNum = new ArrayList<>();
			for (int e : obj.num)
				allNum.add(e);

			NewList saveOpNum = new NewList();// ���ڴ������������ϵ����Ա�

			Util.getAllNum(allNum, 0, new int[4], saveOpNum);// ��ø�4�ſ������е��������
			saveOpNum.check();// ����ظ����������

			ArrayList<String> expression = Util
					.getExpression(saveOpNum, saveOP);// �����������������������ϵ���׺����ַ���

			for (String exp : expression) {
				String post = Util.infixToPostfix(exp); // ����׺���ʽת���ɺ�׺
				if (Util.countPostfix(post) == 24.0) {
					count += obj.weight;
					break;
				}
			}
		}
		System.out.println("Total number of combinations with solutions is "
				+ count);
		System.out.println("The solution ratio is " + count / total);
		
		Date endDate = new Date();
		System.out.println("endTime: " + endDate.getHours() + ":"
				+ endDate.getMinutes() + ":" + endDate.getSeconds());
	}

	private static void setWeight(Number_24 num) {
		// ����set�ж��ж������ظ�
		Set<Integer> set = new HashSet<Integer>();
		int i = num.num[0];
		int j = num.num[1];
		int k = num.num[2];
		int l = num.num[3];
		set.add(i);
		set.add(j);
		set.add(k);
		set.add(l);

		int weight = 0;
		// ��4����������ȫ��һ��ʱ��ֻ������һ�ֻ�ɫ����ϣ��������6��÷��6������6������6
		if (set.size() == 1) {
			weight = 1;
		}
		// ��4�����У�����������ͬ���������������ͬʱ������C4ȡ2����C4ȡ1����C4ȡ1�ֿ��ܵĻ�ɫ��ϣ��������6��÷��6������7������8
		else if (set.size() == 3) {
			weight = 96;
		}
		// ��4����ȫ����ͬʱ������C4ȡ1����C4ȡ1����C4ȡ1����C4ȡ1�֣�256�����
		else if (set.size() == 4) {
			weight = 256;
		} else {
			// ��4�����У�������ͬʱ��ͬ���ģ�����������ϣ�һ����C4ȡ2����C4ȡ2����36�����
			if ((i == j && k == l) || (i == k && j == l)) {
				weight = 36;
			}
			// ��4����������������ͬ������һ������ͬʱ
			else {
				weight = 16;
			}
		}
		
		num.setWeight(weight);
	}
}
