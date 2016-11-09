/**
 * Created by stefandraskovits on 04/11/2016.
 */
public class Task {
    public int workloadCPU;
    public int workloadMemory;
    public int workloadBandwith;
    public int duration;
    public int initDuration;

    private VM owner;
    public User user;

    

    public Task(int workloadCPU, int workloadMemory, int workloadBandwith, int duration,User user) {
        this.workloadCPU = workloadCPU;
        this.workloadMemory = workloadMemory;
        this.workloadBandwith = workloadBandwith;
        this.duration = duration;
        this.initDuration = duration;
        this.user = user;
        Controller.getInstance().totalDuration += duration;
        Controller.getInstance().totalTasks++;
    }

    public VM getOwner() {
        return owner;
    }

    public void setOwner(VM owner) {
        this.owner = owner;
    }

    public void progress(){
        duration--;
    }

    public boolean isFinished(){
        return duration<=0;
    }
    public void reset(){
        this.duration = this.initDuration;
    }
}
