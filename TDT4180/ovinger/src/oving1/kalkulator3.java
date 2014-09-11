package oving1;

import acm.program.ConsoleProgram;

public class kalkulator3 extends ConsoleProgram {
	
	double operand1;
	double operand2;
	double result = 0;
	
	private void performOperation(String operator) {
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
	}
	
	public void run() {
		while (result <= 1337) {
			operand1 = readDouble();
			operand2 = readDouble();
			String operator = readLine();
			performOperation(operator);
			print(result);
			print("\n");
		}
		print("The result was over 1337, so I stopped!\n");
	}
}