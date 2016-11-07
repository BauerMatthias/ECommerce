/**
 * Created by stefandraskovits on 04/11/2016.
 */
public class Task {
    public int workloadCPU;
    public int workloadMemory;
    public int workloadBandwith;
    public int duration;
    public User user;

    public Task(int workloadCPU, int workloadMemory, int workloadBandwith, int duration,User user) {
        this.workloadCPU = workloadCPU;
        this.workloadMemory = workloadMemory;
        this.workloadBandwith = workloadBandwith;
        this.duration = duration;
        this.user = user;
    }

    public void progress(){
        duration--;
    }

    public boolean isFinished(){
        return duration<=0;
    }
}
