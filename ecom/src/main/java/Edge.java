import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Edge implements Updateable {
    public List<PM> pms = new ArrayList<>();
    private NormalDistribution d;


    private static NormalDistribution metaD;

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static double normalDistAvg(){
        double failurePerTick = Controller.FailurePerMinute*FailureManager.getInstance().minutesPreTick;
        double failurePerTickPerEdge = failurePerTick / Controller.EDGECOUNT;
        if (metaD == null) {
            metaD = new NormalDistribution(failurePerTickPerEdge, failurePerTickPerEdge * 0.3);
        }
        return metaD.sample();
    }

    public Edge(int x, int y) {
        this.x = x;
        this.y = y;
        double avg = Edge.normalDistAvg();
        d = new NormalDistribution(avg,avg*0.5);
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

    public int anzTimedFailed =0;
    public int anzFailedPM = 0;

    public int pmsFailed(){
        int anz = (int) Math.floor(d.sample());
        anzTimedFailed++;
        anzFailedPM+= anz;
        return anz;
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
