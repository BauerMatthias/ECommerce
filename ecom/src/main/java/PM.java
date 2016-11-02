import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class PM implements Updateable{
    private Set<VM> vms = new HashSet<>();

    @Override
    public void update() {
        vms.forEach(vm -> vm.update());
    }
}
