import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class Os {
    static Object [] memory = new Object[40];
    static int counter = 0;
    static boolean flagp1 = false; //to indicate if it finished executing
   static boolean flagp2 =false;
   static boolean flagp3 =false;

   static String Hardisk="hardisk.txt";

  //  int [] processes = new int[]

    public void save_inst_in_memory(Pcb p, ArrayList<String[]>lines) throws FileNotFoundException {
       //check if there's enough space in memory do it after scheduler, loop 3ala el space in memory
       int i=0;
       if (memory[12]!=null){
            if(memory[25]!=null){
                int s=1;
                while (s<=9){
                    if ((int)(memory[i])!=p.id){
                        if (!((String) memory[s]).equals("Running")){
                            int [] bound = (int[]) memory[i+3];
                            Pcb hd = new Pcb((int)memory[i],(String) memory[i+1],(int)memory[i+2],bound[0],bound[1]);
                            swap (bound[0], bound[1],p , hd,lines);
                            break;
                        }
                            s+=4;
                            i+=4;

                    }
                    else{
                        s+=4;
                        i+=4;
                    }
                }
//
//
//
//                while ((int)(memory[i])!=p.id){
//                    if (!((String) memory[i+1]).equals("Running")){
//                        int [] bound = (int[]) memory[i+3];
//                        Pcb hd = new Pcb((int)memory[i],(String) memory[i+1],(int)memory[i+2],bound[0],bound[1]);
//                        swap (bound[0], bound[1],p , hd);
//                        break;
//                    }
//                    else{
//                        i=i+4;
//                    }
//                }
            }
                else{
                    int k=25;
                    for(int j=0;j<lines.size();j++){
                        memory[k]=lines.get(j);
                        k++;

                        //update lower upper
                    }
                    int s=0;
                    while(s<=8){
                        if((int)memory[s]==p.id){
                            ( (int[]) memory[i+3])[0]=25;
                            ( (int[]) memory[i+3])[1]=k-1;
                            memory[i+1] = "Ready";
                            break;
                        }
                        s+=4;
                    }

                }

        }
       else{
           int k=12;
           for(int j=0;j< lines.size();j++){
               memory[k]=lines.get(j);
               k++;
           }
           int s=0;
           while(s<=8){
               if((int)memory[s]==p.id){
                   ( (int[]) memory[i+3])[0]=12;
                   ( (int[]) memory[i+3])[1]=k-1;
                   memory[i+1] = "Ready";
                   break;
               }
               s+=4;
           }
       }

//        for(int i=0;i< lines.size();i++){
//           memory[(p.bounds[0])+i]=lines.get(i);
//       }

    }
    public void swap(int lower, int upper, Pcb in, Pcb outToHd, ArrayList<String[]> tobesaved) throws FileNotFoundException {
        ArrayList<String[]> instswap = new ArrayList<>();
        for(int i=lower; i<=upper;i++){
            instswap.add((String[])memory[i]);

        }
        int ind=lower;
       for(int n=0;n<tobesaved.size();n++){
           memory[ind]= tobesaved.get(n);
           ind++;
       }
        int s=0;
        while(s<=8){
            if((int)memory[s]==in.id){
                ( (int[]) memory[s+3])[0]=lower;
                ( (int[]) memory[s+3])[1]=ind-1;
                memory[s+1]= "Ready";
                break;
            }
            s+=4;
        }
        writeto(instswap,outToHd.id);


    }

    public void writeto(ArrayList<String[]>instr, int id) throws FileNotFoundException {
        try {
            ArrayList<String> ins =converttoarr(instr);
            FileOutputStream output = new FileOutputStream("./src/hardisk.txt", false);
            String id_string = id +"\n";
            byte[] idd= id_string.getBytes();
            output.write(idd);
            Iterator<String> itr = ins.iterator();
            while (itr.hasNext()) {

                byte[] array = itr.next().toString().getBytes();
                output.write(array);
            }
            output.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
    }

    public ArrayList<String> converttoarr (ArrayList<String[]> inst){
       ArrayList<String> res=new ArrayList<>();
        for(int i=0;i< inst.size();i++){
            String line="";
            int j;
            for( j=0;j<inst.get(i).length-1;j++){
                line+=inst.get(i)[j]+" ";
            }
            line+=inst.get(i)[j]+"\n";
            res.add(line);
        }
        return res;
    }
    public void savepcb(Pcb p1){
        memory[counter]=String.valueOf(p1.id);
        memory[counter+1]=p1.state;
        memory[counter+2]=String.valueOf(p1.pc);
        memory[counter+3]=p1.bounds;
        counter++;
    }
    public void create_process(Pcb p1, int quantum,ArrayList<String[]> lines ) throws FileNotFoundException {
        //to create the actual process
        savepcb(p1);
        save_inst_in_memory(p1,lines);

    }
     public static void main(String[] args) throws IOException {
        Os os = new Os();
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Please enter arrival time of P1: ");
        String arrival_p1= sc.nextLine();
        int arr_p1 = Integer.parseInt(arrival_p1);
         //reads string
        System.out.println("Arrival time of P1: "+arrival_p1);
        System.out.println("Please enter arrival time of P2: ");
        String arrival_p2= sc.nextLine();
         int arr_p2 = Integer.parseInt(arrival_p2);//reads string
        System.out.println("Arrival time of P2: "+arrival_p2);
        System.out.println("Please enter arrival time of P3: ");
        String arrival_p3= sc.nextLine();
         int arr_p3 = Integer.parseInt(arrival_p3);//reads string
        System.out.println("Arrival time of P3: "+arrival_p3);
        System.out.println("Please enter quantum time: ");
        String quantum_time= sc.nextLine();//reads string
         int quant = Integer.parseInt(quantum_time);
        System.out.println("Quantum time: "+quantum_time);
////
         File file = new File("resources/Program_1.txt");

         BufferedReader br = new BufferedReader(new FileReader(file));

         // Declaring a string variable
         String st;
         ArrayList<String[]> lines_p1 = new ArrayList<>();
         // Condition holds true till
         // there is character in a string
         String[] l1=new String[4];
         while ((st = br.readLine()) != null) {
             // Print the string
             //System.out.println(st);
             l1 = st.split(" ");
             lines_p1.add(l1);
         }
         //System.out.println(lines.get(0)[1]);

         Pcb p1 = new Pcb(0,"Ready",0,12,0);



         File file2 = new File("resources/Program_2.txt");

         BufferedReader br2 = new BufferedReader(new FileReader(file));

         // Declaring a string variable
         String st2;
         ArrayList<String[]> lines_p2 = new ArrayList<>();
         String[] l2=new String[4];
         // Condition holds true till
         // there is character in a string
         while ((st2 = br2.readLine()) != null) {
             // Print the string
             // System.out.println(st2);
             l2 = st2.split(" ");
             lines_p2.add(l2);
         }
         Pcb p2 = new Pcb(1, "Ready", 0, 24,0);


         File file3 = new File("resources/Program_3.txt");

         BufferedReader br3 = new BufferedReader(new FileReader(file));

         // Declaring a string variable
         String st3;
         ArrayList<String[]> lines_p3 = new ArrayList<>();
         String[] l3=new String[4];
         // Condition holds true till
         // there is character in a string
         while ((st3 = br3.readLine()) != null) {
             // Print the string
             // System.out.println(st3);
             l3 = st3.split(" ");
             lines_p3.add(l3);
         }
         Pcb p3 = new Pcb(2, "Ready", 0, 12,0);
         //System.out.println(lines_p3.get(0)[1]);
         if (Math.min(arr_p1,arr_p2)==arr_p1){
             //p1 came before p2
             if(Math.min(arr_p1,arr_p3)==arr_p1){
                 //p1 came first ,but we have to check who came after p2 or p3
                 os.create_process(p1,quant,lines_p1);

             }
             else{
                 //p3 then p1 then p2
             }
         }
         else{
             //p2 came before p1
             if(Math.min(arr_p2,arr_p3)==arr_p2){
                 //p2 came first, but we have to check who came after p1 or p3

             }
             else{
                 //p3 then p2 then p1

             }
         }

//        String[] one = new String[4];
//         one[0]="I";
//         one[1]="give";
//         one[2]="up";
//         one[3]=".";
//         String[]two=new String[2];
//         two[0]="ok";
//         two[1]="bye";
//         ArrayList<String[]> test=new ArrayList<>();
//
//         test.add(one);
//         test.add(two);
////         ArrayList<String> hi = os.converttoarr(test);
////         for(int i=0;i<hi.size();i++){
////             System.out.println(hi.get(i));
////         }
//         os.writeto(test,1);
     }
    }
