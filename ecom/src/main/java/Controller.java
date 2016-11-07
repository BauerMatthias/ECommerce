import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public abstract class Controller implements Updateable {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 15;
    public static final double MOVERATE=0.5;
    public static final double TASKCREATERATE=0.2;

    public List<Edge> edgeList = new ArrayList<>();
    public List<Edge> failedEdges = new ArrayList<>();
    public List<PM> failedPMs = new ArrayList<>();
    protected Set<Task> tasksPositionChanged = new HashSet<>();
    protected Set<Task> newTasks = new HashSet<>();

    private static Controller instance = null;

    public static Controller getInstance() {
        if(instance == null) {
            instance = new BaseController();
        }
        return instance;
    }

    @Override
    public void update() {

    }

    public void tasksPostionUpdated(Set<Task> changed){
        tasksPositionChanged.addAll(changed);
    }

    public void addTasks(Task newTask){
        newTasks.add(newTask);
    }

    public void addEdge(Edge e){
        edgeList.add(e);
    }

    public void failEdges(List<Edge> failedEdges){
        this.failedEdges = failedEdges;
    }

    public void failedPMs(List<PM> failedPMs){
        this.failedPMs = failedPMs;

    }
}
