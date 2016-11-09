import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by michael on 02.11.16.
 */
public class TimerManager {
    public static Set<Updateable> items = new HashSet<Updateable>();
    private static Timer timer;

    public static void register(Updateable item) {
        items.add(item);
    }

    public static void start() {
        TimerTask timerTask = new Task();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 50);
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
