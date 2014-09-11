package oving6;

public interface Account {
	
	public int getBalance();
	public int getCredit();
	public int deposit(int depositAmount);
	public int withdraw(int withdrawalAmount);
}
