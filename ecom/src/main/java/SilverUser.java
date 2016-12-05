public class SilverUser extends User {

    public SilverUser(int x, int y, int id) {
        super(x, y);
        this.id = "Silver " + id;
    }

    @Override
    public double getMoveRate() {
        return 0.5;
    }

    @Override
    public Task getNewTask() {
        int cpu = 7;
        int memory = 5;
        int bandwidth = 8;
        int duration = 10;
        return new Task(cpu, memory, bandwidth, duration, this);
    }
}