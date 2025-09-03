import java.util.*;
import java.io.*;
public class XyzToCONFIG{
    static void createConfig(String inputFile, String outputFile, double latticeDimension1, double latticeDimension2, double latticeDimension3) throws IOException{
        double subtractor1 = latticeDimension1/2;
        double subtractor2 = latticeDimension2/2;
        double subtractor3 = latticeDimension3/2;
        Scanner sc = new Scanner(new File(inputFile));
        System.out.println(inputFile);
        int numAtoms = sc.nextInt(); sc.nextLine(); 
        FileWriter output = new FileWriter(outputFile);
        output.append("Zeolite with some CO2 molecules\n0 0\n");
        output.append(latticeDimension1+" 0 0\n" + "0 " + latticeDimension2+" 0\n" + "0 0 " + latticeDimension3+"\n");
        output.append("NUMMOL 1 1 200\nMOLECULE zeolite " + numAtoms + " " + numAtoms+"\n");
        while(sc.hasNextLine()){
            String element = sc.next();
            output.append(" " + element + " c\n" + (sc.nextDouble()-subtractor1)/latticeDimension1 + " " + (sc.nextDouble()-subtractor2)/latticeDimension2 + " " + (sc.nextDouble()-subtractor3)/latticeDimension3 + " 0\n");
            sc.nextLine();
        }
        output.close();
        sc.close();
    }
}