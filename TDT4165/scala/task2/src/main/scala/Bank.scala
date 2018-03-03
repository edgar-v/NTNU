import scala.concurrent.forkjoin.ForkJoinPool
import scala.concurrent.ExecutionContext

class Bank(val allowedAttempts: Integer = 3) {

  private val uid = 0
  private val transactionsQueue: TransactionQueue = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()
  private val executorContext = ExecutionContext.global

  private var idCounter : Int = 0

  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    transactionsQueue push new Transaction(
      transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
  }

  def generateAccountId: Int = this.synchronized {
    idCounter += 1
    idCounter
  }

  val worker = new Thread {
    override def run = {
      processTransactions
    }
  }
  worker.start

  private def processTransactions: Unit = {
    while (true) {
      if (!transactionsQueue.isEmpty) {
        executorContext.execute(transactionsQueue.pop);
      }

      Thread.sleep(10)
    }
  }

  def addAccount(initialBalance: Double): Account = {
    new Account(this, initialBalance)
  }

  def getProcessedTransactionsAsList: List[Transaction] = {
    processedTransactions.iterator.toList
  }

}
