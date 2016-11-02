import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Edge implements Updateable {
    private Set<PM> pms = new HashSet<>();

    private int x;
    private int y;

    public Edge(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void update() {
        pms.forEach(pm -> pm.update());
    }
}
