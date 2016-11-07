import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class Edge implements Updateable {
    public List<PM> pms = new ArrayList<>();

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

    public void addPM(PM pm){
        pms.add(pm);
    }

    public void addPMs(Set<PM> pms){
        this.pms.addAll(pms);
    }


}
