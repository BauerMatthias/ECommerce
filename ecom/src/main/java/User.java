import java.util.Random;

/**
 * Created by michael on 02.11.16.
 */
public class User implements Updateable {
    private int x;
    private int y;
    private String id;
    private Random random = new Random();
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
    public boolean canMoveDirection(int direction){
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
    public boolean move() {
        boolean hasMoved = false;
        if(random.nextDouble()>=Controller.MOVERATE) {
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

    @Override
    public void update() {
        move();
        System.out.println("User " + id + " x:"+x +" y:"+y);
    }
}
