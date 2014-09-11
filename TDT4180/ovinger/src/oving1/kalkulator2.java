package oving1;

import acm.program.ConsoleProgram;

public class kalkulator2 extends ConsoleProgram {
	
	double operand1;
	double operand2;
	
	private double performOperation(String operator) {
		double result = 0;
		switch (operator) {
		case "+":
			result = operand1 + operand2;
			break;
		case "-":
			result = operand1 - operand2;
			break;
		case "/":
			result = operand1 / operand2;
			break;
		case "*":
			result = operand1 * operand2;
			break;
		}
		
		return result;
	}
	
	public void run() {
		operand1 = readDouble();
		operand2 = readDouble();
		String operator = readLine();
		
		double result = performOperation(operator);
		print(result);
	}
}

