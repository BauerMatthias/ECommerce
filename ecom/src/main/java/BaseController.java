import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class BaseController extends Controller  {


    @Override
    public void update() {
        //TODO: ALGORITHM
        edgeList.forEach(edge -> edge.pms.forEach(pm -> pm.addVMIfFitts(new VM(3,3,3))));

        for (Task newTask:this.newTasks) {
            for (Edge e :edgeList) {
                for (PM pm: e.pms) {
                    for (VM vm:pm.vms) {
                        if (vm.addAndAcceptTask(newTask)){
                            continue;
                        }
                    }
                }
            }
        }



        edgeList.forEach(edge -> edge.update());
        newTasks.clear();
    }
}
