import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by michael on 02.11.16.
 */
public class TimerManager {
    private static Set<Updateable> items = new HashSet<Updateable>();
    private static Timer timer = new Timer(true);

    public static void register(Updateable item) {
        items.add(item);
    }

    public static void start() {
        TimerTask timerTask = new Task();
        timer.scheduleAtFixedRate(timerTask, 0, 1*1000);
    }

    public static void stop() {
        timer.cancel();
    }

    private static class Task extends TimerTask {

        public void run() {
            items.forEach(name -> name.update());
            FailureManager.getInstance().update();
            Controller.getInstance().update();
            System.out.println("-----------------------------");
        }
    }

}
