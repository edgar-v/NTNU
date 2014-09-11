package oving6;

public class SavingsAccount implements Account {

	int balance = 0;
	
	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public int getCredit() {
		return 0;
	}

	@Override
	public int deposit(int depositAmount) {
		if (depositAmount > 0) {
			balance += depositAmount;
		}
		
		return balance;
	}

	@Override
	public int withdraw(int withdrawalAmount) {
		if (withdrawalAmount >= 0 && balance - withdrawalAmount >= 0 ) {
			balance -= withdrawalAmount;
			return withdrawalAmount;
		} else {
			return 0;
		}
	}
	

}
