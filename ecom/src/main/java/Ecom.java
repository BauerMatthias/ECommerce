import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Ecom {

    public static void main(String[] args) {
        Set<User> users = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            users.add(new User(0,0,""+i));
        }
        TimerManager.start();
        try {
            Thread.sleep(10*1000);
            TimerManager.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
