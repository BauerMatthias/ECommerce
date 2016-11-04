import java.util.HashSet;
import java.util.Set;

/**
 * Created by michael on 02.11.16.
 */
public class PM implements Updateable{
    public Set<VM> vms = new HashSet<>();
    private int cpu;
    private int memory;
    private int bandWidth;

    public PM(int cpu, int memory, int bandWidth) {
        this.cpu = cpu;
        this.memory = memory;
        this.bandWidth = bandWidth;
    }

    @Override
    public void update() {
        vms.forEach(vm -> vm.update());
    }

    public boolean doesVmFitt(VM v){
        return this.cpu - this.consumedCPU() - v.getCpu() >0 && this.memory - this.consumedMemory() - v.getMemory() >0 && this.bandWidth - this.consumedBandwidth() - v.getBandwidth() >0 ;
    }

    public boolean addVMIfFitts(VM v){
        boolean b = doesVmFitt(v);
        if (b){
            this.vms.add(v);
            System.out.println("VM added now there are "+ vms.size() + " vms ");

        }else{
            System.out.println("Not enough Space to add such a VM");
        }

        return b;
    }

    public int consumedCPU(){
        int sum =0;
        for (VM v:vms) {
            sum += v.getCpu();
        }
        return sum;
    }
    public int consumedMemory(){
        int sum =0;
        for (VM v:vms) {
            sum += v.getMemory();
        }
        return sum;
    }

    public int consumedBandwidth(){
        int sum =0;
        for (VM v:vms) {
            sum += v.getBandwidth();//TODO:bandwidth or consumedbandwidth
        }
        return sum;
    }
}
