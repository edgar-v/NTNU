public class IO {
    private Queue queue;
    private Statistics statistics;
    private EventQueue eventQueue;
    private Gui gui;

    private Process activeProcess;

    public IO(Queue queue, Statistics statistics, Gui gui, EventQueue eventQueue)
    {
        this.queue = queue;
        this.statistics = statistics;
        this.gui = gui;
        this.eventQueue = eventQueue;
    }


    public void addToQueue(Process p, long clock)
    {
        this.queue.insert(p);

        if (this.activeProcess == null)
        {
            if (this.queue.getNext() instanceof Process)
            {
                this.activeProcess = (Process) this.queue.removeNext();
                this.gui.setIoActive(this.activeProcess);
                this.performIO(clock);
            }
        }

        this.statistics.ioQueueInserts++;
    }

    public void performIO(long clock)
    {
        if (this.activeProcess != null)
        {
            this.activeProcess.leftIOQueue(clock);
            this.eventQueue.insertEvent(new Event(Constants.END_IO, clock + this.activeProcess.calcIOTime()));
        }
    }

    public Process endIOOperation()
    {
        Process finishedIoProcess = this.activeProcess;
        this.activeProcess = null;

        if (this.queue.getQueueLength() > 0)
        {
            if (this.queue.getNext() instanceof Process)
            {
                this.activeProcess = (Process)this.queue.removeNext();
            }
        }
        this.gui.setIoActive(activeProcess);
        this.statistics.nofProcessIO++;
        return finishedIoProcess;
    }

    public void timePassed(long timePassed)
    {
        this.statistics.ioQueueLengthTime += this.queue.getQueueLength() * timePassed;

        if (this.queue.getQueueLength() > this.statistics.ioQueueLargestSize)
        {
            this.statistics.ioQueueLargestSize = this.queue.getQueueLength();
        }
    }

    public Process getActiveProcess()
    {
        return this.activeProcess;
    }
}
