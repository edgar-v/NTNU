/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber extends Thread {
    private CustomerQueue queue;
    private Gui gui;
    private boolean running;
    private int pos;

	/**
	 * Creates a new barber.
	 * @param queue		The customer queue.
	 * @param gui		The GUI.
	 * @param pos		The position of this barber's chair
	 */
	public Barber(CustomerQueue queue, Gui gui, int pos) { 
        this.queue = queue;
        this.gui = gui;
        this.pos = pos;
        this.running = false;
	}

	/**
	 * Starts the barber running as a separate thread.
	 */
	public void startThread() {
        this.running = true;
        this.start();
	}

	/**
	 * Stops the barber thread.
	 */
	public void stopThread() {
        this.running = false;
	}

    public void run() {
        while (running) {
            gui.barberIsSleeping(pos);
            try {
                sleep(Globals.barberSleep + (int)Math.random()*Globals.barberSleep);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            gui.barberIsAwake(pos);
            queue.getCustomer(pos);

            try {
                sleep(Globals.barberWork);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            gui.emptyBarberChair(pos);
        }

    }
}

