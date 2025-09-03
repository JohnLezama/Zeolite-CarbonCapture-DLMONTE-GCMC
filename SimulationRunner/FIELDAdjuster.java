import java.util.*;
import java.io.*;
public class FIELDAdjuster{
    static void setFIELD(int numAtoms, String directory) throws Exception{
        Scanner sc;
        sc = new Scanner(new File("C://users//aul12//Downloads//dlmonte_sims//"+directory+"//FIELD"));
        ArrayList<String> lines = new ArrayList<>();
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        sc.close();
        if(directory.contains("5AN2")){
            lines.set(13, "MAXATOM " + numAtoms);
        }
        else if(directory.contains("5A"))
            lines.set(14, "MAXATOM " + numAtoms); //15
        else if(directory.contains("Test")) lines.set(15, "MAXATOM " + numAtoms);
        else lines.set(15, "MAXATOM " + numAtoms);
        FileWriter output;
        output = new FileWriter("C://users//aul12//Downloads//dlmonte_sims//"+directory+"//FIELD");
        for(String s: lines){
            output.append(s+"\n");
        }
        output.close();
    }
}