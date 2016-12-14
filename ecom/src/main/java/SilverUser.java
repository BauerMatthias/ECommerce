public class SilverUser extends User {

    public SilverUser(int x, int y, int id) {
        super(x, y);
        this.id = "Silver " + id;
    }

    @Override
    public double getMeanMemory() {
        return 7;
    }

    @Override
    public double getMeanDuration() {
        return 4;
    }

    @Override
    public double getMeanBandwidth() {
        return 8;
    }

    @Override
    public double getMeanCpu() {
        return 9;
    }

    @Override
    public int getValue() {
        return 20;
    }

    @Override
    public double getMoveRate() {
        return 0.5;
    }


}