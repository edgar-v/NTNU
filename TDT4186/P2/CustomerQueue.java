import java.util.*;
/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {
    private int queueLength;
    private int startPointer, endPointer;
    private Customer[] customers;
    private Gui gui;
	/**
	 * Creates a new customer queue.
	 * @param queueLength	The maximum length of the queue.
	 * @param gui			A reference to the GUI interface.
	 */
    public CustomerQueue(int queueLength, Gui gui) {
        this.queueLength = queueLength;
        this.gui = gui;
        this.startPointer = 0;
        this.endPointer = 0;
        this.customers = new Customer[queueLength];
	}

    public synchronized void addCustomer() {
        while (startPointer == endPointer && customers[startPointer] != null) {
            gui.println("Barbershop full, sleeping");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        customers[startPointer] = new Customer();
        gui.println("New customer");
        gui.fillLoungeChair(startPointer, customers[startPointer]);
        startPointer++;
        startPointer = startPointer % queueLength;

        if (startPointer-endPointer == 1)
            notifyAll();
    }

    public synchronized Customer getCustomer(int pos) {
        while (startPointer == endPointer && customers[endPointer] == null) {
            gui.println("Barber " + pos + " is waiting for customer");    
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (endPointer == startPointer && customers[startPointer] != null)
            notifyAll();

        Customer customer = customers[endPointer];
        customers[endPointer] = null;

        gui.emptyLoungeChair(endPointer);
        gui.fillBarberChair(pos, customer);
        gui.println(Integer.toString(endPointer));
        gui.println("Got customer");

        endPointer = (endPointer+1) % queueLength;
        return customer;
    }
}
