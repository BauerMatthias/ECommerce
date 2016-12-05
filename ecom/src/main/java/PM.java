import java.util.*;

/**
 * Created by michael on 02.11.16.
 */
public class PM implements Updateable{
    public Set<VM> vms = new HashSet<>();
    private int cpu;
    private int memory;
    private int bandWidth;
    public boolean failed = false;
    public Edge owner;
    private int downTime = 0;
    private double cpuEnegrieConsumption;
    private double memoryEnegrieConsumption;
    private double bandwidthEnegrieConsumption;
    private double baseEnegrieConsumption;
    private static Random random = new Random();

    public List<VM> removeMe = new ArrayList<>();
    public PM(int cpu, int memory, int bandWidth, Edge owner) {
        this.cpu = cpu;
        this.memory = memory;
        this.bandWidth = bandWidth;
        this.owner = owner;


        cpuEnegrieConsumption = random.nextDouble()*Controller.RANGEENEGERYRANDOM+Controller.MINENEGERYRANDOM;
        memoryEnegrieConsumption = random.nextDouble()*Controller.RANGEENEGERYRANDOM+Controller.MINENEGERYRANDOM;
        bandwidthEnegrieConsumption = random.nextDouble()*Controller.RANGEENEGERYRANDOM+Controller.MINENEGERYRANDOM;
        baseEnegrieConsumption = random.nextDouble()*Controller.RANGEENEGERYRANDOM+Controller.MINENEGERYRANDOM;


    }

    @Override
    public void update() {

        if (failed) {
            downTime--;
            if (downTime <=0){
                failed = false;
                Controller.getInstance().restartPM(this);
            }
        }else {
            vms.forEach(vm -> vm.update());
            vms.remove(removeMe);
        }
    }

    public boolean doesVmFitt(VM v){
        if (failed) return false;
        return this.cpu - this.reservedCPU() - v.getCpu() >=0
                && this.memory - this.reservedMemory() - v.getMemory() >=0
                && this.bandWidth - this.reservedBandwidth() - v.getBandwidth() >=0 ;
    }

    public boolean addVMIfFitts(VM v){
        boolean b = doesVmFitt(v);
        if (b){
            this.vms.add(v);
            v.owner = this;
            System.out.println("VM added now there are "+ vms.size() + " vms ");

        }else{
           // System.out.println("Not enough Space to add such a VM");
        }

        return b;
    }

    public int consumedCPU(){
        int sum =0;
        for (VM v:vms) {
            sum += v.consumedCPU();
        }
        return sum;
    }
    public int consumedMemory(){
        int sum =0;
        for (VM v:vms) {
            sum += v.consumedMemory();
        }
        return sum;
    }

    public int consumedBandwidth(){
        int sum =0;
        for (VM v:vms) {
            sum += v.consumedBandwidth();
        }
        return sum;
    }

    public int reservedCPU(){
        int sum =0;
        for (VM v:vms) {
            sum += v.getCpu();
        }
        return sum;
    }
    public int reservedMemory(){
        int sum =0;
        for (VM v:vms) {
            sum += v.getMemory();
        }
        return sum;
    }

    public int reservedBandwidth(){
        int sum =0;
        for (VM v:vms) {
            sum += v.getBandwidth();
        }
        return sum;
    }

    public void fail(){
        failed = true;
        this.downTime = Controller.DOWNTIME;
    }

    public boolean isMigrating(){
        for (VM vm :vms) {
            if (vm.isMigrating()) {
                return true;
            }
        }
        return false;
    }

    public double energieConsumption(){
        return baseEnegrieConsumption
                + this.consumedBandwidth() * bandwidthEnegrieConsumption
                + this.consumedCPU() * cpuEnegrieConsumption
                + this.consumedMemory()*memoryEnegrieConsumption;
    }

    public double engeryConsumptionForTask(Task task){
        return task.workloadCPU * cpuEnegrieConsumption + task.workloadMemory * memoryEnegrieConsumption + task.workloadBandwith * bandwidthEnegrieConsumption;
    }
}
