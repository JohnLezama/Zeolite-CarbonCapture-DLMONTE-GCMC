import java.io.*;
import java.util.*;
public class ExportData{
    static int writeMoleculesAdsorbed(double ratio, int temp, double pressure, String fileName, String directory, String absoluteDirectory)throws Exception{
        Scanner sc; 
        sc = new Scanner(new File(absoluteDirectory+"dlmonte_sims//"+directory+"//YAMLDATA.000"));
        FileWriter output = new FileWriter(absoluteDirectory +"dlmonte_sims//"+ fileName, true);
        ArrayList<Integer> AdsorbedMolecules = new ArrayList<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contains("nmol:")){
                line = line.substring(line.length()-5,line.length()-1);
                line = line.trim();
                AdsorbedMolecules.add(Integer.parseInt(line));
            }
        }
        sc.close();
        int nmol = getHighestCount(AdsorbedMolecules);
        AdsorbedMolecules.clear();
        output.write("RATIO: " + ratio + "   TEMP: " + temp + "   PRESSURE: " + pressure + "    MOLECULES ADSORBED: " + nmol+"\n");
        output.close();
        return nmol;
    }
    
    static void writeMoleculesAdsorbed(double ratio, int temp, double pressure, String fileName, int numAtoms, String absoluteDirectory)throws Exception{
        Scanner sc; 
        FileWriter output = new FileWriter(absoluteDirectory +"dlmonte_sims//"+ fileName, true);
        ArrayList<Integer> AdsorbedMolecules = new ArrayList<>();
        output.write("RATIO: " + ratio + "   TEMP: " + temp + "   PRESSURE: " + pressure + "    MOLECULES ADSORBED: " + (numAtoms/3.0)+"\n");
        output.close();
    }

    static int getCount(double ratio, int temp, double pressure, String fileName, String directory, String absoluteDirectory)throws Exception{
        Scanner sc; 
        sc = new Scanner(new File(absoluteDirectory+"dlmonte_sims//"+directory+"//YAMLDATA.000"));
        ArrayList<Integer> AdsorbedMolecules = new ArrayList<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contains("nmol:")){
                line = line.substring(line.length()-5,line.length()-1);
                line = line.trim();
                AdsorbedMolecules.add(Integer.parseInt(line));
            }
        }
        sc.close();
        int nmol = getHighestCount(AdsorbedMolecules);
        AdsorbedMolecules.clear();
        return nmol;
    }    

    static int getHighestCount(ArrayList<Integer> list){
        int sum  = 0;
        for(int i  = list.size()-20; i<list.size(); i++){
            sum += list.get(i);
        }
        int avg = sum/20;
        return avg;
    }

}