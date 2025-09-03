import java.util.*;
import java.io.*;
public class getMass{
    static double getMass(String inputFile, double ratio, boolean CaCations) throws IOException{
        Scanner sc = new Scanner(new File("C://users//aul12//Downloads//Zeolites To Try//"+inputFile));
        int numAtoms = sc.nextInt();
        sc.close();
        sc = new Scanner(new File(inputFile.substring(0,3)+(int)(ratio)+ "," + (int)Math.round(ratio%1*10)+ "ratioSet.xyz"));
        double mass = 0;
        sc.nextLine(); sc.nextLine();
        while(sc.hasNextLine()){
            String element = sc.next(); sc.nextLine();
            mass += switch(element){
                case "Si" -> 28.08;
                case "Al" -> 26.98;
                case "Na" -> 22.99;
                case "O" -> 16;
                case "Ca" -> 40.08;
                default ->0;
            };
        }
        sc.close();
        return mass;
    }
}