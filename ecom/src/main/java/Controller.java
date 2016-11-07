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
    public static final double TASKCREATERATE=1.0;
    public static final int EDGECOUNT = 2;
    public static final int FailurePerMinute = 10;
    public static final double TRANSMISSIONFACTOR = 20;
    public static final double DIRTYFACTOR = 0.05;
    public static final double MigrationFinishedThreshold = 1.0;

    public List<Edge> edgeList = new ArrayList<>();
    public Edge failedEdge ;
    public List<PM> failedPMs = new ArrayList<>();
    protected Set<Task> tasksPositionChanged = new HashSet<>();
    protected Set<Task> newTasks = new HashSet<>();

    protected List<VM> migratingVMS = new ArrayList<>();

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

    public void failEdge(Edge failedEdge){
        this.failedEdge = failedEdge;
    }

    public void failedPMs(List<PM> failedPMs){
        this.failedPMs = failedPMs;

    }

    public void continueMigrate(){
        ArrayList<VM> finished = new ArrayList<>();
        for (VM source:this.migratingVMS) {

            int srcX= source.owner.owner.getX();
            int srcY=source.owner.owner.getY();
            int destX=source.migratingTo.owner.owner.getX();
            int destY=source.migratingTo.owner.owner.getY();
            double transmittedVolume = source.lastMigrationTime * source.dirtyRate;
            System.out.println("TV: " +  transmittedVolume);
            source.lastMigrationTime = transmittedVolume / transmissionRate(dist(srcX,srcY,destX,destY));//TODO: Speichern der gesamten Transmitted Volume
            System.out.println(source + ":  " + transmittedVolume);
            if (transmittedVolume <MigrationFinishedThreshold){
                System.out.println("Finished Migrating");
                finished.add(source);
            }
        }
        this.migratingVMS.removeAll(finished);
        finished.forEach(vm -> vm.owner.vms.remove(vm));
    }

    public void migrate(VM source, PM target){
        System.out.println("Starting Migration" +source + " " + target);
        VM vm = new VM(source.myTask.workloadCPU,source.myTask.workloadMemory,source.myTask.workloadBandwith,target);
        target.addVMIfFitts(vm);
        int srcX= source.owner.owner.getX();
        int srcY=source.owner.owner.getY();
        int destX=target.owner.getX();
        int destY=target.owner.getY();
        source.migratingTo = vm;
        source.lastMigrationTime = (double) source.myTask.workloadMemory /  transmissionRate(dist(srcX,srcY,destX,destY));
        migratingVMS.add(source);
    }

    public int dist(int srcX, int srcY, int destX,int destY){
        return Math.abs(srcX-destX)+Math.abs(srcY-destY);
    }
    public double transmissionRate(int dist){
        return 1.0/(double)dist * TRANSMISSIONFACTOR + 1;
    }
}
