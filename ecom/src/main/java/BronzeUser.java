public class BronzeUser extends User {

    public BronzeUser(int x, int y, int id) {
        super(x, y);
        this.id = "Bronze " + id;
    }

    @Override
    public double getMoveRate() {
        return 0.3;
    }

    @Override
    public Task getNewTask() {
        int cpu = 2;
        int memory = 1;
        int bandwidth = 2;
        int duration = 3;
        return new Task(cpu, memory, bandwidth, duration, this);
    }
}

