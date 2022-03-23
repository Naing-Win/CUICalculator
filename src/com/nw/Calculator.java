package com.nw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	private static final ArrayList<Double> ANS_LIST = new ArrayList<Double>();

	public static void main(String[] args) throws IOException {

		while (true) {
			System.out.print("Input expression: ");
			System.out.flush();
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			String s = scan.nextLine();
			if (s.equals("")) {
				break;
			}
			if (s.matches(".*[a-zA-Z].*")) {
				System.out.println("Invalid expression.");
			} else {
				double result = evaluate(s);
				if (result > 1.0E10) {
					System.out.println("The calculation result is too big.");
				} else if (result < -1.0E10) {
					System.out.println("The calculation result is too small.");
				} else if (result == 0.1) { 
					System.out.println("Invalid expression.");
				} else if (result == 0.2) { 
					System.out.println("This numerical expression is not calculatable.");
				} else {
					ANS_LIST.add(result);
					System.out.println("Result: " + result);
				}
			}
		}
	}

	private static double evaluate(String expression) {
		double counterValue = 0.0;
		char[] tokens = expression.toCharArray();
		Stack<Double> values = new Stack<Double>();
		Stack<Character> ops = new Stack<Character>();
		String operatorSplitValue = expression.replaceAll("[/*^ ]", "");
		if (expression.startsWith("+")) {
			Double index = ANS_LIST.get(ANS_LIST.size() - 1);
			if (expression.contains(".")) {
				counterValue = index + Double.parseDouble(operatorSplitValue);
			} else {
				counterValue = index + Double.parseDouble(operatorSplitValue);
			}
			return counterValue;
		}
		if (expression.startsWith("-")) {
			Double index = ANS_LIST.get(ANS_LIST.size() - 1);
			if (expression.contains(".")) {
				counterValue = index + Double.parseDouble(operatorSplitValue);
			} else {
				counterValue = index + Double.parseDouble(operatorSplitValue);
			}
			return counterValue;
		}
		if (expression.startsWith("*")) {
			Double index = ANS_LIST.get(ANS_LIST.size() - 1);
			if (expression.contains(".")) {
				counterValue = index * Double.parseDouble(operatorSplitValue);
			} else {
				counterValue = index * Double.parseDouble(operatorSplitValue);
			}
			return counterValue;
		}
		if (expression.startsWith("/")) {
			Double index = ANS_LIST.get(ANS_LIST.size() - 1);
			if (expression.contains(".")) {
				counterValue = index / Double.parseDouble(operatorSplitValue);
			} else {
				counterValue = index / Double.parseDouble(operatorSplitValue);
			}
			return counterValue;
		}
		if (expression.startsWith("^")) {
			Double index = ANS_LIST.get(ANS_LIST.size() - 1);
			if (expression.contains(".")) {
				counterValue = Math.pow(index, Double.parseDouble(operatorSplitValue));
			} else {
				counterValue = Math.pow(index, Double.parseDouble(operatorSplitValue));
			}
			return counterValue;
		}
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] == ' ') {
				// Checking space, if space contains between number and operator, return 0.1 is a key to handle output message ( Invalid expression. )
				if ((tokens[i-1] >= '0' && tokens[i-1] <= '9') && (tokens[i+1] >= '0' && tokens[i+1] <= '9')) {
					return 0.1;
				} else {
					continue;
				}
			}
			if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') {
				StringBuffer sb = new StringBuffer();
				while ((i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
						|| (i < tokens.length - 1 && tokens[i] == '.')) {
					sb.append(tokens[i++]);
				}
				values.push(Double.parseDouble(sb.toString()));
				i--;
			} else if (tokens[i] == '(') {
				ops.push(tokens[i]);
			} else if (tokens[i] == ')') {
				while (ops.peek() != '(') {
					values.push(applyCalculate(ops.pop(), values.pop(), values.pop()));
				}
				ops.pop();
			} else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/'
					|| tokens[i] == '^') {
				while (!ops.empty() && selectPrecedence(tokens[i], ops.peek())) {
					values.push(applyCalculate(ops.pop(), values.pop(), values.pop()));
				}
				ops.push(tokens[i]);
			}
		}
		while (!ops.empty()) {
			Character one = ops.pop();
			// Checking parentheses, if the input numerical expression is not complete, return 0.1 is a key to handle output message ( Invalid expression. )
			if (one.equals('(')) {
				return 0.1;
			} else {
				Double two = values.pop();
				Double three = values.pop();
				values.push(applyCalculate(one, two, three));
			}
		}
		return values.pop();
	}

	private static boolean selectPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')') {
			return false;
		}
		if ((op1 == '^' || op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
			return false;
		} else {
			return true;
		}
	}

	private static double applyCalculate(char op, double b, double a) {
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '^':
			return Math.pow(a, b);
		case '/':
			if (b == 0) {
				// To handle divide by 0, return 0.2 is a key to handle output message ( This numerical expression is not calculatable. )
				return 0.2;
			} else {
				return a / b;
			}
		}
		return 0;
	}

}
