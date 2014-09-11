package oving2;

import java.util.Stack;

public class RPN {
	
	private Stack<Double> operands = new Stack<Double>();
	
	public int getOperandCount() {
		return operands.size();
	}
	
	public double peek(int n) {
		return operands.get(operands.size() - n - 1);
	}
	
	public void push(double value) {
		operands.push(value);
	}
	
	public double pop(double defaultValue) {
		if (operands.size() >= 1) {
			return operands.pop();
		} else {
			return defaultValue;
		}
	}
	
	public void performOperation(char operator) {
		switch (operator) {
		case '+':
			if (operands.size() >= 2) {
				double a = operands.pop();
				double b = operands.pop();
				operands.push(a + b);
			}
			break;
		case '-':
			if (operands.size() >= 2) {
				double a = operands.pop();
				double b = operands.pop();
				operands.push(a - b);
			} 
			break;
		case '/':
			if (operands.size() >= 2) {
				double a = operands.pop();
				double b = operands.pop();
				operands.push(b / a);
			}
			break;
		case '*':
			if (operands.size() >= 2) {
				double a = operands.pop();
				double b = operands.pop();
				operands.push(a * b);
			}
			break;
		case ',':
			if (operands.size() >= 1) {
				operands.push(operands.lastElement());
			}
			break;
		case '.':
			if (operands.size() >= 1) {
				operands.pop();
				break;
			}
		}
	}
}
