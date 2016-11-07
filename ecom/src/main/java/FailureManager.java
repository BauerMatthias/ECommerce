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
        edgeFail = new NormalDistribution(1.0,0.6);
    }

    @Override
    public void update() {

        /*Edge failed;
        if (edgeFail.sample() > 1.0){
            failed = Controller.getInstance().edgeList.get(random.nextInt(Controller.EDGECOUNT));
            failed.fail();
            Controller.getInstance().failEdge(failed);
        }*/
        
        List<PM> failedPM = new ArrayList<>();
        for (Edge e: Controller.getInstance().edgeList) {
            int anzFailed = e.pmsFailed();
            for (int i = 0; i < anzFailed; i++) {

                if (i < e.pms.size() ) {
                    PM pm = e.pms.get(i);
                    if (!pm.failed && !pm.isMigrating()) {
                        pm.fail();
                        failedPM.add(pm);
                    }
                }
            }
        }
        Controller.getInstance().failedPMs(failedPM);
    }
}
