import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Ecom {

    public static void main(String[] args) {
        Controller baseController = new BaseController();
        Controller.setInstance(baseController);
        startSimulation();

        Controller extendedController = new ExtendedController();
        Controller.setInstance(extendedController);
        startSimulation();

        System.out.println("=================================");
        System.out.println();

        System.out.println("Base:");
        output(baseController);
        System.out.println("Extended:");
        output(extendedController);
    }

    public static void startSimulation() {
        TimerManager.items.clear();
        Random random = new Random();
        Set<User> users = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            users.add(new BronzeUser(random.nextInt(Controller.WIDTH), random.nextInt(Controller.HEIGHT), 1));
        }
        for (int i = 0; i < 3; i++) {
            users.add(new SilverUser(random.nextInt(Controller.WIDTH), random.nextInt(Controller.HEIGHT), 1));
        }
        for (int i = 0; i < 1; i++) {
            users.add(new GoldUser(random.nextInt(Controller.WIDTH), random.nextInt(Controller.HEIGHT), 1));
        }


        for (int j = 0; j < Controller.EDGECOUNT ; j++) {
            Edge edge = new Edge(random.nextInt(Controller.WIDTH),random.nextInt(Controller.HEIGHT));
            Set<PM> pms = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                pms.add(new PM(50,50,50,edge));
            }


            edge.addPMs(pms);
            Controller.getInstance().addEdge(edge);
        }




        TimerManager.start();
        try {
            Thread.sleep(5*1000);
            TimerManager.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void output(Controller controller) {

        System.out.println("Total Energy: " + controller.totalEnergy);
        System.out.println("Total Tasks: "+  controller.totalTasks);
        System.out.println("Avg Latency: " + (double)(controller.totalTicksUntilFinished - controller.totalDuration) / (double) controller.totalTasks);
        System.out.println("Total Failures: "+ controller.totalFailures);
       // System.out.printf("Edge Fail Statistics: \n"+controller.failedEdgesStatistics());

    }
}
