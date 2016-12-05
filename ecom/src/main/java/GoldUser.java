public class GoldUser extends User {
    public GoldUser(int x, int y, int id) {
        super(x, y);
        this.id = "Gold " + id;
    }

    @Override
    public double getMoveRate() {
        return 0.8;
    }

    @Override
    public Task getNewTask() {
        int cpu = 10;
        int memory = 12;
        int bandwidth = 10;
        int duration = 20;
        return new Task(cpu, memory, bandwidth, duration, this);
    }
}
