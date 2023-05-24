public class Pcb {
    int id;
    String state;
    int pc;
    int[] bounds=new int[2];

    public Pcb(int id, String state, int pc, int memlower, int memupper){
        this.id= id;
        this.state=state;
        this.pc=pc;
        bounds[0]=memlower;
        bounds[1]=memupper;

    }

}
