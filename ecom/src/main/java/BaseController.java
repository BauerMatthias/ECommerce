import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class BaseController extends Controller  {

    Edge testEdge = new Edge(20,20);

    public BaseController() {
        PM pm = new PM(100,100,100,testEdge);
        testEdge.addPM(pm);
    }

    @Override
    public void update() {
        //TODO: ALGORITHM
        continueMigrate();
        System.out.println("Failed PMS: " + failedPMs.size());
        for (PM pm: failedPMs) {
            for (VM vm: pm.vms) {
                if (vm.myTask != null) {
                    migrate(vm, testEdge.pms.get(0));
                }
            }
        }

        boolean breakFlag = false;
        for (Task newTask:this.newTasks) {
            VM vm = new VM(newTask.workloadCPU,newTask.workloadMemory,newTask.workloadBandwith,null);
            for (Edge e :edgeList) {
                for (PM pm: e.pms) {
                    if (pm.doesVmFitt(vm)){
                        pm.addVMIfFitts(vm);
                        vm.addAndAcceptTask(newTask);
                        migrate(vm,testEdge.pms.get(0));
                        breakFlag = true;
                        break;
                    }
                }
                if (breakFlag){
                    break;
                }
            }
        }



        edgeList.forEach(edge -> edge.update());
        newTasks.clear();
    }
}
