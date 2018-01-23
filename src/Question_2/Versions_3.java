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

		ArrayList<char[]> saveOP = new ArrayList<>();// 用于存放所有操作符组合的线性表
		Util.getAllOP(0, new char[3], saveOP);// 获得所有操作符组合
		Util.insertBracket(saveOP, saveOP.size());// 添加所有的括号组合并存放于操作符组合线性表

		for (Number_24 obj : numList) {
			ArrayList<Integer> allNum = new ArrayList<>();
			for (int e : obj.num)
				allNum.add(e);

			NewList saveOpNum = new NewList();// 用于存放所有数字组合的线性表

			Util.getAllNum(allNum, 0, new int[4], saveOpNum);// 获得该4张卡牌所有的数字组合
			saveOpNum.check();// 清除重复的数字组合

			ArrayList<String> expression = Util
					.getExpression(saveOpNum, saveOP);// 获得所有数字组合与操作符组合的中缀表达字符串

			for (String exp : expression) {
				String post = Util.infixToPostfix(exp); // 将中缀表达式转换成后缀
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
		// 利用set判断有多少种重复
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
		// 当4个数的数字全部一样时，只可能有一种花色的组合，比如红桃6，梅花6，方块6，黑桃6
		if (set.size() == 1) {
			weight = 1;
		}
		// 当4个数中，有两个数相同，其余的数都不相同时，就有C4取2乘以C4取1乘以C4取1种可能的花色组合，比如红桃6，梅花6，方块7，黑桃8
		else if (set.size() == 3) {
			weight = 96;
		}
		// 当4个数全部不同时，就有C4取1乘以C4取1乘以C4取1乘以C4取1种（256）情况
		else if (set.size() == 4) {
			weight = 256;
		} else {
			// 当4个数中，两两相同时，同样的，利用排列组合，一共有C4取2乘以C4取2，共36种情况
			if ((i == j && k == l) || (i == k && j == l)) {
				weight = 36;
			}
			// 当4个数中有三个数相同，另外一个数不同时
			else {
				weight = 16;
			}
		}
		
		num.setWeight(weight);
	}
}
