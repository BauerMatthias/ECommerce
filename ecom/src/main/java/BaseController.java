import java.util.List;

/**
 * Created by michael on 02.11.16.
 */
public class BaseController extends Controller  {

    @Override
    public void update() {
        //Anstehenden Migrationen ausführen
        continueMigrate();

        //Neustarten der ausgefallen PMs (algorithm)
        System.out.println("Failed PMS: " + failedPMs.size());
        for (PM pm : restartedPMs) {
            for (VM vm:pm.vms) {
                if (vm.getMyTask()!=null) {
                    vm.getMyTask().reset();
                }

                if (vm.migratingFrom!=null){
                   vm.migratingFrom.lastMigrationTime =Controller.getInstance().lastMigrationTime(vm.migratingFrom,vm);
                }
            }
        }
        boolean breakFlag = false;
        // User Bewegung => Migration starten
        for (Task movedTask:this.tasksPositionChanged) {
            if (movedTask.getOwner().migratingTo != null|| movedTask.getOwner().migratingFrom != null ){//Already migrating
                break;
            }
            if (movedTask.getOwner() == null){
                System.out.println("test");
            }
            breakFlag = false;
            int aktDist = dist(movedTask.user.x,movedTask.user.y,movedTask.getOwner().owner.owner.getX(),movedTask.getOwner().owner.owner.getY());
            if (aktDist > Controller.DISTTHRESHOLD){
                List<Edge> sortedEdges =Controller.getInstance().edgesSortedByDistance(movedTask.user.x,movedTask.user.y );
                for (int i = 0; i <sortedEdges.indexOf(movedTask.getOwner().owner.owner) ; i++) {
                    for (PM pm: sortedEdges.get(i).pms) {
                        if (pm.doesVmFitt(movedTask.getOwner())){
                            migrate(movedTask.getOwner(),pm);
                            breakFlag = true;
                            break;
                        }
                    }
                    if (breakFlag){
                        break;
                    }
                }

            }

        }

        //neue Task
        breakFlag = false;
        for (Task newTask:this.newTasks) {
            VM vm = new VM(newTask.workloadCPU,newTask.workloadMemory,newTask.workloadBandwith,null);
            breakFlag = false;
            for (Edge e:Controller.getInstance().edgesSortedByDistance(newTask.user.x,newTask.user.y)) {
                for (PM pm: e.pms) {
                    if (pm.doesVmFitt(vm)){
                        pm.addVMIfFitts(vm);
                        vm.addAndAcceptTask(newTask);
                        breakFlag = true;
                        break;
                    }
                }
                if (breakFlag){
                    break;
                }
            }
            if (!breakFlag){
                newTask.user.myTasks.remove(newTask);
            }


        }

        for (Edge e: edgeList) {
            totalEnergy+=e.energyConsumption();
        }
        //Tasks abarbeiten
        edgeList.forEach(edge -> edge.update());
        newTasks.clear();
        restartedPMs.clear();
        failedPMs.clear();
        tasksPositionChanged.clear();
        super.update();
    }
}
