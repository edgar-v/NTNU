package oving2;

import java.util.Stack;

import acm.program.ConsoleProgram;

public class reverseCalculator extends ConsoleProgram {
	
	private Stack<Double> operands = new Stack<Double>();
	
	public void run() {
		runCalculator();
	}
	
	private void runCalculator() {
		String newInput = "";
		while (newInput != "q") {
			newInput = readLine();
			
			switch (newInput) {
			case "+":
				if (operands.size() >= 2) {
					double a = operands.pop();
					double b = operands.pop();
					operands.push(a + b);
				} else {
					println("There are less than two operands in the stack");
				}
				break;
			case "-":
				if (operands.size() >= 2) {
					double a = operands.pop();
					double b = operands.pop();
					operands.push(a - b);
				} else {
					println("There are less than two operands in the stack");
				}
				break;
			case "/":
				if (operands.size() >= 2) {
					double a = operands.pop();
					double b = operands.pop();
					operands.push(b / a);
				} else {
					println("There are less than two operands in the stack");
				}
				break;
			case "*":
				if (operands.size() >= 2) {
					double a = operands.pop();
					double b = operands.pop();
					operands.push(a * b);
				} else {
					println("There are less than two operands in the stack");
				}
				break;
			case ".":
				if (operands.size() >= 1) {
					operands.pop();
				} else {
					println("The stack is already empty");
				}
				break;
			case ",":
				if (operands.size() >= 1) {
					operands.push(operands.lastElement());
				} else {
					println("there is no element to duplicate");
				}
				break;
			default:
				try {
					double a = Double.parseDouble(newInput);
					operands.push(a);
				} catch (NumberFormatException e) {
					println("Invalid input");
				}
			}
			
			String stackElements = "";
			for (int i = 0; i < operands.size(); i++) {
				stackElements += operands.elementAt(i) + " ";
			}
			println(stackElements);
		}
	}
}
