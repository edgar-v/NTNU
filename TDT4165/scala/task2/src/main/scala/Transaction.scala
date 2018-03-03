import exceptions._
import scala.collection.mutable
import java.util.concurrent.LinkedBlockingQueue

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

  private val transactions = new mutable.Queue[Transaction]()

  // Remove and return the first element from the queue
  def pop: Transaction = this.synchronized {
    transactions.dequeue
  }

  // Return whether the queue is empty
  def isEmpty: Boolean = this.synchronized {
    transactions.isEmpty
  }

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = this.synchronized {
    transactions.enqueue(t)
  }
  

  // Return the first element from the queue without removing it
  def peek: Transaction = this.synchronized {
    transactions.front
  }

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = this.synchronized {
    transactions.iterator
  }
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttemps: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempts : Int = 0

  override def run: Unit = {

    def doTransaction() = {
      from withdraw amount
      to deposit amount
    }


    try {
      if (from.uid < to.uid) from synchronized {
        to synchronized {
          doTransaction
        }
      } else to synchronized {
        from synchronized {
          doTransaction
        }
      }

      this.status = TransactionStatus.SUCCESS
      processedTransactions.push(this)
    } catch {
      case iae : IllegalAmountException => {
        this.status = TransactionStatus.FAILED;
        processedTransactions.push(this)
      }
      case nsfe : NoSufficientFundsException => {
        attempts += 1
        if (attempts < allowedAttemps) {
          transactionsQueue.push(this)
        } else {
          this.status = TransactionStatus.FAILED;
          processedTransactions.push(this)
        }
      }
    }
  }
}
