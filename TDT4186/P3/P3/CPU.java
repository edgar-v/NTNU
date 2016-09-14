public class CPU
{
    private Queue queue;
    private Statistics statistics;
    private Gui gui;
    private Process activeProcess;
    private EventQueue eventQueue;
    private long maxCpuTime;


    public CPU(Queue queue, Statistics statistics, Gui gui, EventQueue eventQueue, long maxCpuTime)
    {
        this.queue = queue;
        this.statistics = statistics;
        this.gui = gui;
        this.eventQueue = eventQueue;
        this.maxCpuTime = maxCpuTime;
    }
    
    public void addToQueue(Process process, long clock)
    {
        this.queue.insert(process);
        process.arrivedAtCpuQueue(clock);

        if (this.activeProcess == null && this.queue.getQueueLength() == 1)
        {
            this.eventQueue.insertEvent(new Event(Constants.SWITCH_PROCESS, clock));
        }
        this.statistics.cpuQueueInserts++;
    }

    /* 
     *
     *
     */
    public void processNextProcess(long clock)
    {
        if (this.activeProcess != null)
        {
            if (this.activeProcess.getCpuTimeNeeded() > this.maxCpuTime && this.activeProcess.getTimeToNextIO() <= this.maxCpuTime)
            {
                this.activeProcess.updateCpuTimeNeeded(this.activeProcess.getTimeToNextIO());
                this.eventQueue.insertEvent(new Event(Constants.IO_REQUEST, clock + this.activeProcess.getTimeToNextIO()));
                this.eventQueue.insertEvent(new Event(Constants.SWITCH_PROCESS, clock + this.activeProcess.getTimeToNextIO() + 1));
            }
            else if (this.activeProcess.getCpuTimeNeeded() <= maxCpuTime)
            {
                this.eventQueue.insertEvent(new Event(Constants.END_PROCESS, clock + this.activeProcess.getCpuTimeNeeded()));
                this.eventQueue.insertEvent(new Event(Constants.SWITCH_PROCESS, clock + this.activeProcess.getCpuTimeNeeded() + 1));

                activeProcess.updateCpuTimeNeeded(maxCpuTime);

                this.statistics.timeSpentCPU += this.activeProcess.getCpuTimeNeeded();
            }
            else if (this.activeProcess.getCpuTimeNeeded() > this.maxCpuTime)
            {
            activeProcess.updateCpuTimeNeeded(maxCpuTime);

                this.eventQueue.insertEvent(new Event(Constants.SWITCH_PROCESS, clock + maxCpuTime));
                this.statistics.nofProcessSwitches++;
                this.statistics.timeSpentCPU += this.maxCpuTime;
            }
        }
    }

    public void switchProcess(long clock)
    {
        if (this.activeProcess != null)
        {
            this.queue.insert(this.activeProcess);
        }
        if (!queue.isEmpty())
        {
            this.activeProcess = (Process) queue.getNext();
            this.queue.removeNext();
            this.activeProcess.leftCpuQueue(clock);
            this.gui.setCpuActive(activeProcess);
        }
    }

    public Process endProcess()
    {
        Process tempProcess = null;
        if (activeProcess != null)
        {
            tempProcess = activeProcess;
            activeProcess = null;
        }

        this.gui.setCpuActive(null);
        this.statistics.nofCompletedProcesses++;
        this.statistics.totalTimeInSystem += tempProcess.calcTotalTimeInSystem();
        return tempProcess;
    }

    public Process getProcessForIO()
    {
        Process ioProcess = this.activeProcess;
        this.activeProcess = null;
        this.gui.setCpuActive(null);
        return ioProcess;
    }

    public void timePassed(long timePassed)
    {
        this.statistics.cpuQueueLengthTime += this.queue.getQueueLength() * timePassed;
        this.statistics.cpuQueueLargestSize = Math.max(this.statistics.cpuQueueLargestSize, this.queue.getQueueLength());
    }
}
