package oving6;

public class CreditAccount implements Account {

	int balance = 0;
	int credit;
	int totalFees = 0;
	
	public CreditAccount(int credit) {
		this.credit = credit;
	}
	
	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public int getCredit() {
		return credit;
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
		if (withdrawalAmount > 0) {
			if (balance - withdrawalAmount >= 0) {
				balance -= withdrawalAmount;
				return withdrawalAmount;
			} else if (balance + credit - 50 - withdrawalAmount >= 0) {
				totalFees += 50;
				balance -= withdrawalAmount + 50;
				return withdrawalAmount;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public int getFees() {
		return totalFees;
	}
}
