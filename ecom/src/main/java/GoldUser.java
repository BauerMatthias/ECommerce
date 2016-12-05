public class GoldUser extends User {
    public GoldUser(int x, int y, int id) {
        super(x, y);
        this.id = "Gold " + id;
    }

    @Override
    public double getMeanMemory() {
        return 14;
    }

    @Override
    public double getMeanDuration() {
        return 10;
    }

    @Override
    public double getMeanBandwidth() {
        return 11;
    }

    @Override
    public double getMeanCpu() {
        return 12;
    }

    @Override
    public double getMoveRate() {
        return 0.8;
    }

}
