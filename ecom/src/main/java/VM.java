import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class VM implements Updateable {
    private int cpu;
    private int memory;
    private int bandwidth;
    public double dirtyRate;

    public VM migratingTo = null;
    public double lastMigrationTime;
    public PM owner;

    public VM(int cpu, int memory, int bandwidth, PM owner) {
        this.cpu = cpu;
        this.memory = memory;
        this.bandwidth = bandwidth;
        this.dirtyRate = (cpu+memory+bandwidth)*Controller.DIRTYFACTOR;
        this.owner = owner;
    }

    public Task myTask = null;
    @Override
    public void update() {
        if (myTask != null) {
            myTask.progress();
            if (myTask.isFinished()) {
                myTask = null;
            }
        }
      //  System.out.println("Now there are " + myTasks.size() + " tasks");
    }

    public boolean doesTaskFittIn(Task t){
        return this.cpu - this.consumedCPU() - t.workloadCPU >=0
                && this.memory - this.consumedMemory() - t.workloadMemory >=0
                && this.bandwidth - this.consumedBandwidth() - t.workloadBandwith >=0 ; //TODO: STUFF
    }

    public boolean addAndAcceptTask(Task t){
        boolean b = doesTaskFittIn(t);
        if (b){
            System.out.println("TASK added");
            myTask=t;
        }
        return b;
    }

    public int consumedCPU(){
        if (myTask == null) return 0;
        return myTask.workloadCPU;
    }
    public int consumedMemory(){
        if (myTask == null) return 0;
        return myTask.workloadMemory;
    }

    public int consumedBandwidth(){
        if (myTask == null) return 0;
        return myTask.workloadBandwith;
    }

    public int getCpu() {
        return cpu;
    }

    public int getMemory() {
        return memory;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public boolean isMigrating(){
        return migratingTo !=null;
    }
}
