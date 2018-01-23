package Question_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Util {
	// 将52张卡牌存入文件
	public static void creatCardToFile() {
		ArrayList<Card> list = new ArrayList<>();

		for (int i = 0; i < 52; i++) {
			int num = (i + 1) % 13;
			if (num == 0)
				num = 13;
			Card card = new Card(num, "card/" + (i + 1) + ".png");
			list.add(card);
		}

		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(new File("bin/card/card.dat")));
			out.writeObject(list);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得卡牌对象的线性表
	@SuppressWarnings("unchecked")
	public static ArrayList<Card> getCardFromFile() {
		Object list = null;
		try {
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream(new File("bin/card/card.dat")));
			list = input.readObject();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (ArrayList<Card>) list;
	}

	/**/
	public static int toPostfixExpression(String nifStr, ArrayList<Integer> num) {
		Stack<Integer> operandStack = new Stack<>();
		Stack<Character> operatorStack = new Stack<>();

		java.util.StringTokenizer tokens = new java.util.StringTokenizer(
				nifStr, "()+-/*", true);

		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken().trim();
			if (token.length() == 0)
				continue;
			else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
				while (!operatorStack.isEmpty()
						&& (operatorStack.peek() == '+'
								|| operatorStack.peek() == '-'
								|| operatorStack.peek() == '*' || operatorStack
								.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}

				operatorStack.push(token.charAt(0));
			} else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
				while (!operatorStack.isEmpty()
						&& (operatorStack.peek() == '*' || operatorStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}

				operatorStack.push(token.charAt(0));
			} else if (token.trim().charAt(0) == '(') {
				operatorStack.push('(');
			} else if (token.trim().charAt(0) == ')') {
				while (operatorStack.peek() != '(') {
					processAnOperator(operandStack, operatorStack);
				}

				operatorStack.pop();
			} else {
				operandStack.push(new Integer(token));
				num.add(new Integer(token));
			}
		}

		while (!operatorStack.isEmpty()) {
			processAnOperator(operandStack, operatorStack);
		}

		return operandStack.pop();
	}

	/**/
	private static void processAnOperator(Stack<Integer> operandStack,
			Stack<Character> operatorStack) {
		char op = operatorStack.pop();
		int op1 = operandStack.pop();
		int op2 = operandStack.pop();
		if (op == '+')
			operandStack.push(op2 + op1);
		else if (op == '-')
			operandStack.push(op2 - op1);
		else if (op == '*')
			operandStack.push(op2 * op1);
		else if (op == '/') {
			if (op1 == 0)
				return;
			operandStack.push(op2 / op1);
		}

	}

	// 判断用户输入的数字是否是与卡牌所符合
	public static boolean isMatchNumber(ArrayList<Integer> num,
			ArrayList<Integer> cardNum) {
		while (cardNum.size() != 0) {
			int cardnum = cardNum.remove(0);

			int length = num.size();
			for (int i = 0; i < length; i++) {
				if (num.get(i) == cardnum) {
					num.remove(i);
					break;
				}
			}

			if (num.size() == 0)
				break;
		}
		return num.size() == 0 && cardNum.size() == 0;
	}

	// 消息提醒框
	public static void Alert(String str) {
		final Stage stage = new Stage();

		Label label = new Label(str);
		label.setPrefWidth(400);
		BorderPane bPane1 = new BorderPane();
		bPane1.setPrefSize(400, 200);
		bPane1.setCenter(label);

		Button bt = new Button("确定");
		bt.setPrefSize(100, 50);
		BorderPane bPane11 = new BorderPane();
		bPane11.setCenter(bt);

		bt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		VBox vbox = new VBox();
		vbox.getChildren().addAll(bPane1, bPane11);

		Scene scene = new Scene(vbox);
		stage.setTitle("消息");
		stage.setScene(scene);
		stage.show();
	}

	// 检测表达式是否正确
	public static boolean checkExpression(String expression) {
		boolean flag = true;
		if (expression.compareTo("") == 0)
			return false;
		else if ((expression.charAt(0) < '0' || expression.charAt(0) > '9')
				&& expression.charAt(0) != '(')
			return false;
		for (int i = 0; i < expression.length(); i++) {
			if ((expression.charAt(i) != '+' && expression.charAt(i) != '-'
					&& expression.charAt(i) != '*'
					&& expression.charAt(i) != '/'
					&& expression.charAt(i) != '(' && expression.charAt(i) != ')')
					&& (expression.charAt(i) < '0' || expression.charAt(i) > '9')) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// 获得4个数字能出现的所有组合
	public static void getAllNum(ArrayList<Integer> allNum, int index,
			int[] num, NewList save) {
		if (index != 4) {
			for (int i = 0; i < allNum.size(); i++) {
				num[index] = allNum.get(i);

				ArrayList<Integer> list = new ArrayList<>(allNum);
				list.remove(i);
				int[] newNum = num.clone();
				getAllNum(list, index + 1, newNum, save);
			}
		} else
			save.list.add(num);

	}

	// 获得操作符能出现的所有组合
	public static void getAllOP(int index, char[] op, ArrayList<char[]> save) {
		ArrayList<Character> allOP = new ArrayList<>();
		allOP.add('+');
		allOP.add('-');
		allOP.add('*');
		allOP.add('/');

		if (index != 3) {
			for (int i = 0; i < allOP.size(); i++) {
				op[index] = allOP.get(i);

				char[] newOP = op.clone();
				getAllOP(index + 1, newOP, save);
			}
		} else
			save.add(op);
	}

	// 为操作符集加入括号
	public static void insertBracket(ArrayList<char[]> saveOP, int size) {
		for (int i = 0; i < size; i++) {
			char[] array = saveOP.get(i);

			char[] bracket = new char[5];
			if ((array[0] == '+' || array[0] == '-')
					&& (array[1] == '*' || array[1] == '/')) {
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 0)
						bracket[j] = '(';
					else if (j == 2)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if ((inContains(0, 1, array, '+') || inContains(0, 1, array, '-'))
					&& (array[2] == '*' || array[2] == '/')) {
				bracket = new char[5];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 0)
						bracket[j] = '(';
					else if (j == 3)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if (array[0] == '-' || array[0] == '/' || ((array[2] == '+' || array[2] == '-') && (outContains(0, 2, array, '*') || outContains(0, 2, array, '/')))) {
				bracket = new char[5];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 1)
						bracket[j] = '(';
					else if (j == 3)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if((array[0] == '-' && (inContains(1, 2, array, '+') || inContains(1, 2, array, '-')))|| array[0] == '/' || (array[0] == '*' && (inContains(1, 2, array, '+') || inContains(1, 2, array, '-')))){
				bracket = new char[5];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 1)
						bracket[j] = '(';
					else if (j == 4)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if((array[1] == '-' && (array[2] == '+' || array[2] == '-')) || array[1] == '/' || (array[1] == '*' && (array[2] == '+' || array[2] == '-'))){
				bracket = new char[5];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 2)
						bracket[j] = '(';
					else if (j == 4)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if((array[1] == '/' && (array[0] == '+' || array[0] == '-')) 
					|| ((array[1] == '*') && (array[0] == '+' || array[0] == '-') && (array[2] == '+' || array[2] == '-'))){
				bracket = new char[7];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 0 || j == 4)
						bracket[j] = '(';
					else if (j == 2 || j == 6)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if(array[0] == '-' && (array[1] == '+' || array[1] == '-') && (array[2] == '*' || array[2] == '/')){
				bracket = new char[7];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 0 || j == 2)
						bracket[j] = '(';
					else if (j == 4 || j == 5)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if(((array[0] == '-' || array[0] == '*' || array[0] == '/') && array[1] == '-' && (array[2] == '+' || array[2] == '-')) ||
					(array[0] == '1' && array[1] == '*' && (array[2] == '+' || array[2] == '-')) || 
					(array[0] == '/' && array[1] == '/')){
				bracket = new char[7];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 1 || j == 3)
						bracket[j] = '(';
					else if (j == 5 || j == 6)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}

			if(array[0] == '/' && (array[1] == '+' || array[1] == '-') && (array[2] == '*' || array[2] == '/')){
				bracket = new char[7];
				for (int j = 0, k = 0; j < bracket.length; j++) {
					if (j == 1 || j == 2)
						bracket[j] = '(';
					else if (j == 4 || j == 6)
						bracket[j] = ')';
					else {
						bracket[j] = array[k];
						k++;
					}
				}
				saveOP.add(bracket);
			}
		}
	}

	// 判断数组下标内是否包含对应的字符
	public static boolean inContains(int start, int end, char[] array, char ch) {
		for (int i = start; i <= end; i++) {
			if (array[i] == ch)
				return true;
		}

		return false;
	}

	// 判断数组下标外是否包含对应的字符
	public static boolean outContains(int start, int end, char[] array, char ch) {
		for (int i = start; i >= 0; i--) {
			if (array[i] == ch)
				return true;
		}

		for (int i = end; i < array.length; i++) {
			if (array[i] == ch)
				return true;
		}

		return false;
	}

	// 获得所有中缀表达式
	public static ArrayList<String> getExpression(NewList saveOpNum,
			ArrayList<char[]> saveOP) {
		ArrayList<String> list = new ArrayList<>();

		for (int i = 0; i < saveOpNum.list.size(); i++) {
			for (int j = 0; j < saveOP.size(); j++) {
				String str = toExpression(saveOpNum.list.get(i), saveOP.get(j));
				list.add(str);
			}
		}

		return list;
	}

	// 将数字组合与操作符组合组成中缀表达式
	private static String toExpression(int[] num, char[] op) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < num.length; i++)
			list.add(num[i]);

		String str = "";
		for (int i = 0; i < op.length; i++) {
			if (op[i] == '(' || op[i] == ')')
				str += op[i];
			else if (!str.contains("+") && !str.contains("-")
					&& !str.contains("*") && !str.contains("/")) {
				if (op[i + 1] != '(') {
					int num1 = list.remove(0);
					int num2 = list.remove(0);
					str += num1 + "" + op[i] + "" + num2;
				} else {
					int num1 = list.remove(0);
					str += num1 + "" + op[i];
				}

			} else {
				if (i == op.length - 1) {
					int num1 = list.remove(0);
					str += op[i] + "" + num1;
				} else {
					if (op[i + 1] == '(' && op[i - 1] == '(') {
						int num1 = list.remove(0);
						str += num1 + "" + op[i];
					} else if (op[i + 1] == '(')
						str += op[i];
					else if (op[i - 1] == '(') {
						int num1 = list.remove(0);
						int num2 = list.remove(0);
						str += num1 + "" + op[i] + "" + num2;
					} else {
						int num1 = list.remove(0);
						str += op[i] + "" + num1;
					}
				}
			}
		}

		return str;
	}

	// 中缀转后缀
	public static String infixToPostfix(String expression) {
		ArrayList<String> list = new ArrayList<>();
		String str = "";
		String printStr = "";
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < expression.length(); i++) {
			try {
				str += Integer.parseInt(expression.charAt(i) + "");
			} catch (NumberFormatException e) {
				if (str != "") {
					list.add(str);
					str = "";
				}
				list.add(expression.charAt(i) + "");
			}
		}
		if (str != "") {
			list.add(str);
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).compareTo(")") == 0) {
				while (stack.peek().compareTo("(") != 0)
					printStr += stack.pop() + " ";

				if (stack.peek().compareTo("(") == 0)
					stack.pop();
			} else if (isNumber(list.get(i)))
				printStr += list.get(i) + " ";
			/**/
			else if (!stack.isEmpty()) {
				if (((list.get(i).compareTo("/") == 0
						|| list.get(i).compareTo("*") == 0 || list.get(i)
						.compareTo("(") == 0) && (stack.peek().compareTo("+") == 0 || stack
						.peek().compareTo("-") == 0))
						|| (list.get(i).compareTo("(") == 0 && (stack.peek()
								.compareTo("*") == 0 || stack.peek().compareTo(
								"/") == 0)))
					if (list.get(i - 1).compareTo(")") == 0
							&& list.get(i + 1).compareTo("(") != 0) {
						i++;
						printStr += list.get(i) + " " + list.get(i - 1) + " ";
					} else
						stack.push(list.get(i));
				/**/
				else if ((list.get(i).compareTo("/") == 0 || list.get(i)
						.compareTo("*") == 0)
						&& stack.peek().compareTo("/") == 0) {

					printStr += stack.pop() + " ";
					stack.push(list.get(i));
					/**/
				} else if ((list.get(i).compareTo("+") == 0 || list.get(i)
						.compareTo("-") == 0)) {
					if (stack.peek().compareTo("*") == 0
							|| stack.peek().compareTo("/") == 0) {
						while (!stack.isEmpty()) {
							if (stack.peek().compareTo("(") == 0)
								break;
							else
								printStr += stack.pop() + " ";
						}

						stack.push(list.get(i));

						/**/
					} else if (stack.peek().compareTo("-") == 0) {
						printStr += stack.pop() + " ";
						stack.push(list.get(i));
					} else {
						if (list.get(i - 1).compareTo(")") == 0
								&& list.get(i + 1).compareTo("(") != 0) {
							i++;
							printStr += list.get(i) + " " + list.get(i - 1)
									+ " ";
						} else
							stack.push(list.get(i));
					}
				} else {
					if (list.get(i - 1).compareTo(")") == 0
							&& list.get(i + 1).compareTo("(") != 0) {
						i++;
						printStr += list.get(i) + " " + list.get(i - 1) + " ";
					} else
						stack.push(list.get(i));
				}
			} else
				stack.push(list.get(i));

		}
		/**/
		while (!stack.isEmpty())
			printStr += stack.pop() + " ";

		return printStr;
	}

	// 判断字符串是否是数字
	private static boolean isNumber(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}

		return true;
	}

	// 通过后缀表达式计算结果
	public static double countPostfix(String postfix) {
		Stack<Double> stack = new Stack<>();
		String[] ch = postfix.split(" ");
		for (String c : ch) {
			try {
				double num = new Integer(c);
				stack.push(num);
			} catch (NumberFormatException e) {
				double num1 = stack.pop();
				double num2 = stack.pop();
				if (c.compareTo("+") == 0)
					stack.push(num2 + num1);
				else if (c.compareTo("-") == 0)
					stack.push(num2 - num1);
				else if (c.compareTo("*") == 0)
					stack.push(num2 * num1);
				else {
					if (num1 == 0)
						return 0;
					stack.push((num2 / num1));
				}
			}
		}

		return stack.pop();
	}

	
}
