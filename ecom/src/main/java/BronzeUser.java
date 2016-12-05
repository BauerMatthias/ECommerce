public class BronzeUser extends User {

    public BronzeUser(int x, int y, int id) {
        super(x, y);
        this.id = "Bronze " + id;
    }

    @Override
    public double getMeanMemory() {
        return 3;
    }

    @Override
    public double getMeanDuration() {
        return 4;
    }

    @Override
    public double getMeanBandwidth() {
        return 3;
    }

    @Override
    public double getMeanCpu() {
        return 4;
    }

    @Override
    public double getMoveRate() {
        return 0.3;
    }

}

