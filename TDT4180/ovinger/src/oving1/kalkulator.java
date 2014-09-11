package oving1;

import acm.program.ConsoleProgram;

public class kalkulator extends ConsoleProgram {
	
	private double performOperation(double a, double b, String operator) {
		double result = 0;
		switch (operator) {
		case "+":
			result = a + b;
			break;
		case "-":
			result = a - b;
			break;
		case "/":
			result = a / b;
			break;
		case "*":
			result = a * b;
			break;
		}
		
		return result;
	}
	
	public void run() {
		double firstOperand = readDouble();
		double secondOperand = readDouble();
		String operator = readLine();
		
		double result = performOperation(firstOperand, secondOperand, operator);
		print(result);
	}
}
