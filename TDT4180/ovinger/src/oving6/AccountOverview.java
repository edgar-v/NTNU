package oving6;

import java.util.ArrayList;
import java.util.List;

public class AccountOverview {
	List<Account> accounts;
	
	public AccountOverview() {
		accounts = new ArrayList<Account>();
	}
	
	public int getAccountCount() {
		return accounts.size();
	}
	
	public Account getAccount(int index) {
		return accounts.get(index);
	}
	
	public void addAccount(Account account) {
		if (!accounts.contains(account)) {
			accounts.add(account);
		}
	}
	
	public int getTotalBalance() {
		int balance = 0;
		for (Account i : accounts) {
			balance += i.getBalance();
		}
		return balance;
	}
	
	public int getTotalCredit() {
		int credit = 0;
		for (Account i : accounts) {
			credit += i.getCredit();
		}
		return credit;
	}
	
	public int getTotalFees() {
		if (accounts.size() == 0)
			return 0;
		int fees = 0;
		for (Account i : accounts) {
			if (i instanceof CreditAccount) {
				fees += ((CreditAccount) i).getFees();
			}
		}
		return fees;
	}
	
}
