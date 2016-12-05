import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class User implements Updateable {
    public int x;
    public int y;
    private String id;
    private Random random = new Random();
    public Set<Task> myTasks = new HashSet<>();
    //direction 0: North
    //1: East
    //2: South
    //3: West

    public User(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
        TimerManager.register(this);
    }

    private boolean canMoveDirection(int direction){
        boolean canMove =true;
        switch (direction){
            case 0:
                canMove = y>1;
                break;
            case 1:
                canMove = x<Controller.WIDTH-1;
                break;
            case 2:
                canMove = y<Controller.HEIGHT-1;
                break;
            case 3:
                canMove = x >1;
                break;
        }
        return canMove;
    }

    private boolean move() {
        boolean hasMoved = false;
        if(random.nextDouble()<Controller.MOVERATE) {
            hasMoved= true;
            int direction = random.nextInt(3);
            if (canMoveDirection(direction)) {
                switch (direction) {
                    case 0:
                        y--;
                        break;
                    case 1:
                        x++;
                        break;
                    case 2:
                        y++;
                        break;
                    case 3:
                        x--;
                        break;
                }
            }
        }
        return hasMoved;

    }

    private Task createTask(){
        if (random.nextDouble() > Controller.TASKCREATERATE) return  null;
        Task t = new Task(1,10,1,10,this);
        myTasks.add(t);
        //TODO: Adjust default values
        return t;
    }


    @Override
    public void update() {
        if (move()) {
            Controller.getInstance().tasksPostionUpdated(this.myTasks);
        }
        Task t;
        if ((t = createTask()) != null){
           // System.out.println("NEW TASK");
            Controller.getInstance().addTasks(t);
        }
       // System.out.println("User " + id + " x:"+x +" y:"+y);
    }
}
