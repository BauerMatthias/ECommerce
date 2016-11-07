import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefandraskovits on 07/11/2016.
 */
public class FailureManager implements Updateable {

    private static FailureManager instance = null;

    public static FailureManager getInstance() {
        if(instance == null) {
            instance = new FailureManager();
        }
        return instance;
    }


    @Override
    public void update() {



        
        List<PM> failedPM = new ArrayList<>();
        for (Edge e: Controller.getInstance().edgeList) {
            int anzFailed = 8;
            for (int i = 0; i < e.pms.size(); i++) {
                e.pms.get(i).fail();
                failedPM.add(e.pms.get(i));
            }
        }
        Controller.getInstance().failedPMs(failedPM);
    }
}
