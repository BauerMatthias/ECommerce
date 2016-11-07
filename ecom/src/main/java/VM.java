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

    public VM(int cpu, int memory, int bandwidth) {
        this.cpu = cpu;
        this.memory = memory;
        this.bandwidth = bandwidth;
    }

    private Set<Task> myTasks = new HashSet<>();
    @Override
    public void update() {

        myTasks.forEach(task -> task.progress());
        myTasks.removeIf(task -> task.isFinished());
        System.out.println("Now there are " + myTasks.size() + " tasks");
    }

    public boolean doesTaskFittIn(Task t){
        return this.cpu - this.consumedCPU() - t.workloadCPU >0
                && this.memory - this.consumedMemory() - t.workloadMemory >0
                && this.bandwidth - this.consumedBandwidth() - t.workloadBandwith >0 ; //TODO: STUFF
    }

    public boolean addAndAcceptTask(Task t){
        boolean b = doesTaskFittIn(t);
        if (b){
            System.out.println("TASK added");
            myTasks.add(t);
        }
        return b;
    }

    public int consumedCPU(){
        int sum =0;
        for (Task t:myTasks) {
            sum += t.workloadCPU;
        }
        return sum;
    }
    public int consumedMemory(){
        int sum =0;
        for (Task t:myTasks) {
            sum += t.workloadMemory;
        }
        return sum;
    }

    public int consumedBandwidth(){
        int sum =0;
        for (Task t:myTasks) {
            sum += t.workloadBandwith;
        }
        return sum;
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
}
