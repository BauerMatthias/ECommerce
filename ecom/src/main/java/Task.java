/**
 * Created by stefandraskovits on 04/11/2016.
 */
public class Task implements Comparable<Task>{
    public double workloadCPU;
    public double workloadMemory;
    public double workloadBandwidth;
    public double duration;
    public double initDuration;

    private VM owner;
    public User user;


    public Task(double workloadCPU, double workloadMemory, double workloadBandwidth, double duration, User user) {
        this.workloadCPU = workloadCPU;
        this.workloadMemory = workloadMemory;
        this.workloadBandwidth = workloadBandwidth;
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

    public void progress() {
        duration--;
    }

    public boolean isFinished() {
        return duration <= 0;
    }

    public void reset() {
        this.duration = this.initDuration;
    }


    @Override
    public int compareTo(Task o) {
        return Integer.compare(o.user.getValue(),this.user.getValue());
    }
}
