/**
 * Created by stefandraskovits on 04/11/2016.
 */
public class Task {
    public int workloadCPU;
    public int workloadMemory;
    public int workloadBandwith;
    public int duration;

    public Task(int workloadCPU, int workloadMemory, int workloadBandwith, int duration) {
        this.workloadCPU = workloadCPU;
        this.workloadMemory = workloadMemory;
        this.workloadBandwith = workloadBandwith;
        this.duration = duration;
    }
}
