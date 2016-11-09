import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stefandraskovits on 07/11/2016.
 */
public class FailureManager implements Updateable {

    private static FailureManager instance = null;
    private NormalDistribution edgeFail;
    private Random random = new Random();

    public double minutesPreTick=1;
    public static FailureManager getInstance() {
        if(instance == null) {
            instance = new FailureManager();
        }
        return instance;
    }

    public FailureManager() {
        edgeFail = new NormalDistribution(0.8,0.4);
    }

    @Override
    public void update() {
        List<PM> failedPM = new ArrayList<>();
        Edge failedEdge;
        if (edgeFail.sample() > 1.0){
            failedEdge = Controller.getInstance().edgeList.get(random.nextInt(Controller.EDGECOUNT));
            failedEdge.fail();
            failedPM.addAll(failedEdge.pms);
            Controller.getInstance().totalFailures+=failedEdge.pms.size();
        }
        

        for (Edge e: Controller.getInstance().edgeList) {
            int anzFailed = e.pmsFailed();
            Controller.getInstance().totalFailures+=anzFailed;
            for (int i = 0; i < anzFailed; i++) {
                if (i < e.pms.size() ) {
                    PM pm = e.pms.get(i);
                    if (!pm.failed) {
                        pm.fail();
                        failedPM.add(pm);
                    }
                }
            }
        }
        Controller.getInstance().failedPMs(failedPM);
    }
}
