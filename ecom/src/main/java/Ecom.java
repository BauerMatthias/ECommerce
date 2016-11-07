import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Ecom {

    public static void main(String[] args) {
        Random random = new Random();
        Set<User> users = new HashSet<>();
        for (int i = 0; i < 1; i++) {
            users.add(new User(0,0,""+i));
        }
        for (int j = 0; j < Controller.EDGECOUNT ; j++) {
            Edge edge = new Edge(random.nextInt(Controller.WIDTH),random.nextInt(Controller.HEIGHT));
            Set<PM> pms = new HashSet<>();
            for (int i = 0; i < 2; i++) {
                pms.add(new PM(50,50,50,edge));
            }


            edge.addPMs(pms);
            Controller.getInstance().addEdge(edge);
        }




        TimerManager.start();
        try {
            Thread.sleep(20*1000);
            TimerManager.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
