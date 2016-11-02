/**
 * Created by michael on 02.11.16.
 */
public abstract class Controller {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 15;
    public static final double MOVERATE=0.5;

    private static Controller instance = null;

    public static Controller getInstance() {
        if(instance == null) {
            instance = new BaseController();
        }
        return instance;
    }
}
