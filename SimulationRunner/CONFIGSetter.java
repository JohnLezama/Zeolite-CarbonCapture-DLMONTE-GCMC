import java.io.*;
import java.util.*;
public class CONFIGSetter{
    static void setCONFIG(String fileName, String directory, String absoluteDirectory)throws Exception{
        Scanner sc = new Scanner(new File(fileName));
        FileWriter output;
        output = new FileWriter(absoluteDirectory+"dlmonte_sims//"+directory+"//CONFIG");
        while(sc.hasNextLine()){
            output.append(sc.nextLine()+"\n");
        }
        sc.close();
        output.close();
    }
}