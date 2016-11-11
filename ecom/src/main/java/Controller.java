import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Controller implements Updateable {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 15;
    public static final double MOVERATE=0.5;
    public static final double TASKCREATERATE=0.3;
    public static final int EDGECOUNT = 18;
    public static final int FailurePerMinute = 30;
    public static final double TRANSMISSIONFACTOR = 50;
    public static final double DIRTYFACTOR = 0.01;
    public static final double MigrationFinishedThreshold = 5.0;
    public static final int DOWNTIME = 8;
    public static final int DISTTHRESHOLD = 5;
    public static final double MINENEGERYRANDOM=0.8;
    public static final double RANGEENEGERYRANDOM=1/10;
    public static final double MIGRATIONLATENCY=5;

    public double totalEnergy =0;
    public int totalTasks=0;
    public int totalDuration=0;
    public int totalTicksUntilFinished=0;
    public int totalFailures=0;

    public List<Edge> edgeList = new ArrayList<>();

    public List<PM> failedPMs = new ArrayList<>();

    protected Set<Task> tasksPositionChanged = new HashSet<>();
    protected Set<Task> newTasks = new HashSet<>();

    public List<PM> restartedPMs = new ArrayList<>();
    protected List<VM> migratingVMS = new ArrayList<>();

    private static Controller instance = null;

    public static Controller getInstance() {
        if(instance == null) {
            instance = new BaseController();
        }
        return instance;
    }

    public static void setInstance(Controller instance) {
        Controller.instance = instance;
    }

    @Override
    public void update() {

        //Calc total ticks until finished
        for (Edge e : edgeList) {
            for (PM pm:e.pms) {
                for (VM vm : pm.vms) {
                    if (vm.getMyTask() != null) {
                        totalTicksUntilFinished++;
                    }
                }
            }
        }
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


    public void failedPMs(List<PM> failedPMs){
        this.failedPMs = failedPMs;

    }

    public void continueMigrate(){
        ArrayList<VM> finished = new ArrayList<>();
        for (VM source:this.migratingVMS) {
            if (!source.migratingTo.owner.failed) {
                int srcX = source.owner.owner.getX();
                int srcY = source.owner.owner.getY();
                int destX = source.migratingTo.owner.owner.getX();
                int destY = source.migratingTo.owner.owner.getY();
                double transmittedVolume = source.lastMigrationTime * source.dirtyRate;
                System.out.println("TV: " + transmittedVolume);
                source.lastMigrationTime = transmittedVolume / transmissionRate(dist(srcX, srcY, destX, destY));
                System.out.println(source + ":  " + transmittedVolume);
                source.migratedMemory += transmittedVolume;
                if (transmittedVolume < MigrationFinishedThreshold) {
                    totalEnergy += source.migratedMemory;
                    System.out.println("Finished Migrating");
                    finished.add(source);
                    if (source.getMyTask() == null){
                        System.out.println("test:   " + source);
                    }
                   if ( source.migratingTo.addAndAcceptTask(source.getMyTask())){
                        source.setMyTask(null);
                        source.migratingTo.migratingFrom = null;
                    }else{
                       System.out.println("Shouldn't happen");
                   }

                }
            }
        }
        this.migratingVMS.removeAll(finished);
        finished.forEach(vm -> vm.delete());
    }

    public void migrate(VM source, PM target){
        System.out.println("Starting Migration" +source + " " + target);
        VM vm = new VM(source.getMyTask().workloadCPU,source.getMyTask().workloadMemory,source.getMyTask().workloadBandwith,target);
        target.addVMIfFitts(vm);
        source.setMigratingTo(vm);
        source.lastMigrationTime = lastMigrationTime(source,vm);
        source.migratedMemory = source.getMemory();
        migratingVMS.add(source);
    }

    public double lastMigrationTime(VM source , VM target){
        int srcX= source.owner.owner.getX();
        int srcY=source.owner.owner.getY();
        int destX=target.owner.owner.getX();
        int destY=target.owner.owner.getY();
        return (double) source.getMyTask().workloadMemory /  transmissionRate(dist(srcX,srcY,destX,destY));
    }

    public void stopMigration(VM source){
        this.migratingVMS.remove(source);
        if (source.migratingTo != null) {
            source.migratingTo.delete();
            source.migratingTo = null;
        }
    }
    public void restartPM(PM pm){
        this.restartedPMs.add(pm);
    }
    public int dist(int srcX, int srcY, int destX,int destY){
        return Math.abs(srcX-destX)+Math.abs(srcY-destY);
    }

    public double transmissionRate(int dist){
        if (dist == 0) return Double.MAX_VALUE;
        return 1.0/(double)dist * TRANSMISSIONFACTOR + 1;
    }

    public List<Edge> edgesSortedByDistance(int x, int y){
        edgeList.sort((o1, o2) -> Integer.compare(dist(o1.getX(),o1.getY(),x,y),dist(o2.getX(),o2.getY(),x,y)));
        return edgeList;
    }
}
