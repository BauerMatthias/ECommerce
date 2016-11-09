import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Edge implements Updateable {
    public List<PM> pms = new ArrayList<>();
    private NormalDistribution d;

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Edge(int x, int y) {
        this.x = x;
        this.y = y;

        double failurePerTick = Controller.FailurePerMinute*FailureManager.getInstance().minutesPreTick;
        double failurePerTickPerEdge = failurePerTick / Controller.EDGECOUNT;
        d = new NormalDistribution(failurePerTickPerEdge,failurePerTickPerEdge*0.5); //TODO: Median muss schwanken
    }


    @Override
    public void update() {
        pms.forEach(pm -> pm.update());
    }

    public void addPM(PM pm){
        pms.add(pm);
    }

    public void addPMs(Set<PM> pms){
        this.pms.addAll(pms);
    }

    public int pmsFailed(){

        return (int) Math.floor(d.sample());
    }
    public void fail(){
        for (PM pm:pms) {
            pm.fail();
        }

    }

    public double energyConsumption(){
        double sum =0;
        for (PM pm:pms) {
            sum+= pm.energieConsumption();
        }
        return sum;
    }


}
