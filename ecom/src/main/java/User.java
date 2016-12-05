import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class User implements Updateable {
    public int x;
    public int y;
    protected String id;
    private Random random = new Random();
    public Set<Task> myTasks = new HashSet<>();
    //direction 0: North
    //1: East
    //2: South
    //3: West

    public User(int x, int y) {
        this.x = x;
        this.y = y;
        TimerManager.register(this);
    }

    private boolean canMoveDirection(int direction){
        boolean canMove =true;
        switch (direction){
            case 0:
                canMove = y>1;
                break;
            case 1:
                canMove = x< Controller.WIDTH-1;
                break;
            case 2:
                canMove = y< Controller.HEIGHT-1;
                break;
            case 3:
                canMove = x >1;
                break;
        }
        return canMove;
    }

    public abstract double getMoveRate();

    private boolean move() {
        boolean hasMoved = false;
        if(random.nextDouble()< getMoveRate()) {
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

    public abstract Task getNewTask();

    private Task createTask(){
        if (random.nextDouble() > Controller.TASKCREATERATE) return  null;
        Task t = getNewTask();
        myTasks.add(t);
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
