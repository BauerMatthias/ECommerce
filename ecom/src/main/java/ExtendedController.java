import java.util.List;

public class ExtendedController extends Controller {

    @Override
    public void update() {
        //Anstehenden Migrationen ausführen
        continueMigrate();

        //Neustarten der ausgefallen PMs (algorithm)
        boolean breakFlag = false;
        System.out.println("Failed PMS: " + failedPMs.size());
        for (PM pm : failedPMs) {
            for (VM vm:pm.vms) {
                if (vm.getMyTask()!=null&&vm.migratingFrom==null) {
                    breakFlag = false;
                    List<Edge> edges = Controller.getInstance().edgesSortedByDistance(pm.owner.getX(), pm.owner.getY());
                    for (Edge e : edges) {
                        for (PM nearPM : e.pms) {
                            if (nearPM.doesVmFitt(vm)){
                                migrate(vm, nearPM);
                                Controller.getInstance().totalTicksUntilFinished+=Controller.MIGRATIONLATENCY;
                                breakFlag = true;
                                break;
                            }
                        }
                        if (breakFlag) break;
                    }
                }

                if (vm.migratingFrom!=null){
                    VM migratingFrom = vm.migratingFrom;
                    stopMigration(vm.migratingFrom);
                    breakFlag = false;
                    List<Edge> edges = Controller.getInstance().edgesSortedByDistance(migratingFrom.owner.owner.getX(), migratingFrom.owner.owner.getY());
                    for (Edge e : edges) {
                        for (PM nearPM : e.pms) {
                            if (nearPM.doesVmFitt(migratingFrom)){
                                migrate(migratingFrom, nearPM);
                                breakFlag = true;
                                Controller.getInstance().totalTicksUntilFinished+=Controller.MIGRATIONLATENCY;
                                break;
                            }
                        }
                        if (breakFlag) break;
                    }
                }
            }
        }

        breakFlag = false;
        // User Bewegung => Migration starten
        for (Task movedTask:this.tasksPositionChanged) {
            if (movedTask.getOwner().migratingTo != null ){//Already migrating
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
            for (Edge e : Controller.getInstance().edgesSortedByDistance(newTask.user.x,newTask.user.y)) {
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
