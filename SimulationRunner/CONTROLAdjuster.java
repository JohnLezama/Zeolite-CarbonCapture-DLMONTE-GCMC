import java.util.*;
import java.io.*;
import java.math.BigDecimal;
public class CONTROLAdjuster{
    static void setCONTROL(int temperature, double pressure, boolean isCO2, String directory) throws Exception{
        Scanner sc;
        sc = new Scanner(new File("C://users//aul12//Downloads//dlmonte_sims//"+directory+"//CONTROL"));
        ArrayList<String> lines = new ArrayList<>();
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        sc.close();
        lines.set(4, "temperature       " + temperature);
        String mlc = "n2 ";
        if(isCO2) mlc = "co2 ";
        lines.set(20, mlc + BigDecimal.valueOf(pressure).toPlainString());
        FileWriter output;
        output = new FileWriter("C://users//aul12//Downloads//dlmonte_sims//"+directory+"//CONTROL");
        for(String s: lines){
            output.append(s+"\n");
        }
        output.close();
    }
}