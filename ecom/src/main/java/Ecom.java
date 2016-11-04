import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Ecom {

    public static void main(String[] args) {
        Set<User> users = new HashSet<>();
        for (int i = 0; i < 1; i++) {
            users.add(new User(0,0,""+i));
        }

        Set<PM> pms = new HashSet<>();
        for (int i = 0; i < 1; i++) {
            pms.add(new PM(5,5,5));
        }

        Edge edge = new Edge(2,2);
        edge.addPMs(pms);

        Controller.getInstance().addEdge(edge);
        TimerManager.start();
        try {
            Thread.sleep(10*1000);
            TimerManager.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
