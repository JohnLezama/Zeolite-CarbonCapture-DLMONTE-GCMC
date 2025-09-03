import java.util.*;
import java.io.*;
public class ConvertMlcstoMmol{
    static void Convert(String zeolite, String absoluteDirectory) throws IOException{
        Scanner sc = new Scanner(new File(absoluteDirectory + "dlmonte_sims//"+zeolite + "Data.txt"));
        FileWriter out = new FileWriter(absoluteDirectory + "dlmonte_sims//"+zeolite + "CorrectUnits.txt");
        double[] ratios = new double[9];
        int index = 0;
        while(sc.hasNextLine()){
            sc.next(); //RATIO:
            double ratio =  sc.nextDouble(); //1.0
            if(index>0 && ratios[index-1] != ratio) ratios[index++] = ratio;
            else if(index==0) ratios[index++] = ratio;
            sc.next(); //TEMP:
            int temp= sc.nextInt(); //273
            sc.next(); //PRESSURE;
            double pressure = sc.nextDouble();
            sc.next(); //MOLECULES
            sc.next(); //ADSORBED:
            int mlcs = sc.nextInt();
            sc.nextLine();
            //double mass = getMass.getMass(file.substring(0,3)+".xyz", ratio, false);
            double mass = getMass.getMass(zeolite + ".xyz", ratio, false, absoluteDirectory);
            double mmolPerG = 1000*(mlcs/mass);
            out.append("RATIO: " + ratio + " TEMP: " + temp + " PRESSURE: " + pressure + " Mmol/G: " +mmolPerG+"\n");
        }
        out.close();
        sc.close();
        sc = new Scanner(new File(absoluteDirectory + "dlmonte_sims//"+zeolite + "N2Data.txt"));
        out = new FileWriter(absoluteDirectory + zeolite + "N2CorrectUnits.txt");
        while(sc.hasNextLine()){
            sc.next(); //RATIO:
            double ratio = sc.nextDouble();
            sc.next(); //TEMP:
            int temp= sc.nextInt(); //273
            sc.next(); //PRESSURE;
            double pressure = sc.nextDouble();
            sc.next(); //MOLECULES
            sc.next(); //ADSORBED:
            int mlcs = sc.nextInt();
            sc.nextLine();
            double mass = getMass.getMass(zeolite + ".xyz", ratio, false, absoluteDirectory);
            double mmolPerG = 1000*(mlcs/mass);
            out.append("RATIO: " + ratio + " TEMP: " + temp + " PRESSURE: " + pressure + " Mmol/G: " +mmolPerG+"\n");
        }
        out.close();
        sc.close();
        toCSV.toCSV(zeolite, zeolite+".csv", ratios, absoluteDirectory);
    }
}