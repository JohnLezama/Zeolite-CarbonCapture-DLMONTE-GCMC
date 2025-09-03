import java.util.*;
import java.io.*;
public class DataToXYZ{
    public static void method(String zeo, double ratio) throws IOException{
        Scanner sc = new Scanner(new File("C://users//aul12//Downloads//LAMMPSTest//"+zeo+(int)(ratio)+ "," + (int)Math.round(ratio%1*10)+"OUT.data"));
        FileWriter out = new FileWriter((int)(ratio)+ "," + (int)Math.round(ratio%1*10)+"ratioSet.xyz");
        sc.nextLine(); sc.nextLine();
        int numAtoms = sc.nextInt(); sc.nextLine();
        out.append(numAtoms+"\nRandoLine\n");
        List<double[]> O = new ArrayList<>();
        for(int i = 0; i<22; i++) sc.nextLine();
        while(numAtoms-->0){
            sc.next();
            String elem = switch(sc.nextInt()){
                case 1 -> "Si";
                case 2 -> "O";
                case 3 -> "Al";
                case 4 -> "Na";
                default -> "Null";
            };
            sc.next();
            if(elem.equals("O")) O.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            else out.append(elem + " " + sc.nextDouble() + " " + sc.nextDouble() + " " + sc.nextDouble() + "\n");
            sc.nextLine();
        }
        sc.close();
        for(double[] arr: O){
            out.append("O " + arr[0] + " " + arr[1] + " " + arr[2] + "\n");
        }
        out.close();
    }
}