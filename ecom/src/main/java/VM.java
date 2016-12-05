/**
 * Created by michael on 02.11.16.
 */
public class VM implements Updateable {
    private double cpu;
    private double memory;
    private double bandwidth;
    public double dirtyRate;

    public VM migratingTo = null;
    public VM migratingFrom = null;
    public double lastMigrationTime;
    public PM owner;

    public double migratedMemory = 0;

    public VM(double cpu, double memory, double bandwidth, PM owner) {
        this.cpu = cpu;
        this.memory = memory;
        this.bandwidth = bandwidth;
        this.dirtyRate = (cpu + memory + bandwidth) * Controller.DIRTYFACTOR;
        this.owner = owner;
    }

    private Task myTask = null;

    @Override
    public void update() {
        if (myTask != null) {
            myTask.progress();
            if (myTask.isFinished()) {
                System.out.println("VM" + this + " finished with Task " + myTask);
                myTask.user.myTasks.remove(myTask);
                myTask.setOwner(null);
                myTask = null;

                Controller.getInstance().stopMigration(this);
                this.delete();
            }
        }
        //  System.out.println("Now there are " + myTasks.size() + " tasks");
    }


    public Task getMyTask() {
        return myTask;
    }

    public void setMyTask(Task myTask) {
        this.myTask = myTask;
        if (myTask != null) {
            myTask.setOwner(this);
        }
    }

    public boolean doesTaskFittIn(Task t) {
        return this.cpu - this.consumedCPU() - t.workloadCPU >= 0
                && this.memory - this.consumedMemory() - t.workloadMemory >= 0
                && this.bandwidth - this.consumedBandwidth() - t.workloadBandwidth >= 0;
    }

    public boolean addAndAcceptTask(Task t) {
        boolean b = doesTaskFittIn(t);
        if (b) {
            System.out.println("TASK added");
            setMyTask(t);

        }
        return b;
    }

    public double consumedCPU() {
        if (myTask == null) return 0;
        return myTask.workloadCPU;
    }

    public double consumedMemory() {
        if (myTask == null) return 0;
        return myTask.workloadMemory;
    }

    public double consumedBandwidth() {
        if (myTask == null) return 0;
        return myTask.workloadBandwidth;
    }

    public double getCpu() {
        return cpu;
    }

    public double getMemory() {
        return memory;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setMigratingTo(VM vm) {
        migratingTo = vm;
        vm.migratingFrom = this;
    }

    public boolean isMigrating() {
        return migratingTo != null || migratingFrom != null;
    }

    public void delete() {
        this.owner.removeMe.add(this);
        this.migratingTo = null;
        this.migratingFrom = null;
    }
}
