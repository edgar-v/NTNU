import java.util.NoSuchElementException

import akka.actor._
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import akka.util.Timeout

case class GetAccountRequest(accountId: String)

case class CreateAccountRequest(initialBalance: Double)

case class IdentifyActor()

class Bank(val bankId: String) extends Actor {

  val accountCounter = new AtomicInteger(1000)

  def createAccount(initialBalance: Double): ActorRef = {
    BankManager.createAccount(accountCounter.incrementAndGet.toString, bankId, initialBalance)
  }

  def findAccount(accountId: String): Option[ActorRef] = {
    // Use BankManager to look up an account with ID accountId
	Some(BankManager.findAccount(bankId, accountId))
  }

  def findOtherBank(bankId: String): Option[ActorRef] = {
    // Use BankManager to look up a different bank with ID bankId
    Some(BankManager.findBank(bankId))
  }

  override def receive = {
    case CreateAccountRequest(initialBalance) => sender ! createAccount(initialBalance)
    case GetAccountRequest(id) => sender ! findAccount(id)
    case IdentifyActor => sender ! this
    case t: Transaction => processTransaction(t)
    case t: TransactionRequestReceipt => {
      // Forward receipt
	  var isInternal = t.toAccountNumber.length <= 4
	  val toBankId = if (isInternal) bankId else t.toAccountNumber.substring(0, 4)
      val toAccountId = if (isInternal) t.toAccountNumber else t.toAccountNumber.substring(4)
	  if (toBankId == bankId) {
	    isInternal = true
      }

	  if (isInternal) {
		val account = findAccount(toAccountId)
		if (account.isEmpty) {
		  t.transaction.status = TransactionStatus.FAILED
		} else {
		  account.get ! t
		}
	  } else {
		val bank = findOtherBank(toBankId)
		if (bank.isEmpty) {
		  t.transaction.status = TransactionStatus.FAILED
		  this.self ! t
		} else {
		  bank.get ! t
		}
	  }
    }

    case msg => ???
  }

  def processTransaction(t: Transaction): Unit = {
    implicit val timeout = new Timeout(5 seconds)
    var isInternal = t.to.length <= 4
    val toBankId = if (isInternal) bankId else t.to.substring(0, 4)
    val toAccountId = if (isInternal) t.to else t.to.substring(4)
    val transactionStatus = t.status
    
    // for an external bank to see that it is an internal account 
	if (toBankId == bankId)
	  isInternal = true

    if (isInternal) {
		try {
		  val account = findAccount(toAccountId)
		  if (account.isEmpty) {
		    t.status = TransactionStatus.FAILED;
			sender ! new TransactionRequestReceipt(toAccountId, t.id, t)
		  }
		  account.get ! t 
		} catch {
		  case nsee: NoSuchElementException =>
		    t.status = TransactionStatus.FAILED
			sender ! new TransactionRequestReceipt(toAccountId, t.id, t)
	    }
    } else {
		try {
		  val bank = findOtherBank(toBankId)
		  if (bank.isEmpty) {
		    t.status = TransactionStatus.FAILED;
			sender ! new TransactionRequestReceipt(toAccountId, t.id, t)
		  } else {
			bank.get ! t 
		  }
		} catch {
		  case nsee: NoSuchElementException =>
		    t.status = TransactionStatus.FAILED
			sender ! new TransactionRequestReceipt(toAccountId, t.id, t)
	    }
		
    }
  }
}
