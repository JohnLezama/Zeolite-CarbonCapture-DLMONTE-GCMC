import java.io.*;
import java.util.*;
public class CONFIGSetter{
    static void setCONFIG(String fileName, String directory)throws Exception{
        Scanner sc = new Scanner(new File(fileName));
        FileWriter output;
        output = new FileWriter("C://users//aul12//Downloads//dlmonte_sims//"+directory+"//CONFIG");
        while(sc.hasNextLine()){
            output.append(sc.nextLine()+"\n");
        }
        sc.close();
        output.close();
    }
}