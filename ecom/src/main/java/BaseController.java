import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class BaseController extends Controller implements Updateable {
    private Set<Edge> edgeSet = new HashSet<>();

    @Override
    public void update() {
        edgeSet.forEach(edge -> edge.update());
    }
}
