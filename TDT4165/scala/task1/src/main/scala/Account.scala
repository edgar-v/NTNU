import exceptions._

class Account(initialBalance: Double, val uid: Int = Bank getUniqueId) {

  var balance = initialBalance;

  def withdraw(amount: Double): Unit = {
    this.synchronized {
      if (amount > balance) throw new NoSufficientFundsException 
      if (amount < 0) throw new IllegalAmountException
      balance -= amount;
    }
  }

  def deposit(amount: Double): Unit = {
    this.synchronized {
      if (amount < 0) throw new IllegalAmountException
      balance += amount;
    }
  }
  def getBalanceAmount: Double = {
    return balance;
  }
}
