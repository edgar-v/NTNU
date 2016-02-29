/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman extends Thread{
    private Gui gui;
    private CustomerQueue queue;
    private boolean running;

	/**
	 * Creates a new doorman.
	 * @param queue		The customer queue.
	 * @param gui		A reference to the GUI interface.
	 */
	public Doorman(CustomerQueue queue, Gui gui) { 
        this.queue = queue;
        this.gui = gui;
        this.running = false;
	}

	/**
	 * Starts the doorman running as a separate thread.
	 */
	public void startThread() {
        running = true;
        this.start();
	}

	/**
	 * Stops the doorman thread.
	 */
	public void stopThread() {
        running = false;
	}

    public void run() {
        while (running) {
            queue.addCustomer();
            try {
                this.sleep(Globals.doormanSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
