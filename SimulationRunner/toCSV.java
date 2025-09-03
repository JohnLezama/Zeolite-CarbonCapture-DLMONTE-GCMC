import java.io.*;
import java.util.*;
public class toCSV{
    public static void toCSV(String zeo, String fileName, double[] ratios, String absoluteDirectory) throws IOException{
        Scanner sc; 
        sc = new Scanner(new File(absoluteDirectory+"dlmonte_sims//"+zeo+"CorrectUnits.txt"));
        FileWriter output = new FileWriter(absoluteDirectory + "dlmonte_sims//" + fileName);
        ArrayList<Double> col1 = new ArrayList<>();
        ArrayList<Double> col2 = new ArrayList<>();
        ArrayList<Double> col3 = new ArrayList<>();
        int index = 0;
        while(sc.hasNextLine()){
            for(int i = 0; i<7; i++) sc.next();
            switch((index++/7)%2){
                case 0:
                    col1.add(sc.nextDouble());
                    break;
                case 1:
                    col2.add(sc.nextDouble());
                    break;
            }
            sc.nextLine();
        }
        sc.close();

        sc = new Scanner(new File(absoluteDirectory+"dlmonte_sims//"+zeo+"N2CorrectUnits.txt"));
        while(sc.hasNextLine()){
            for(int i = 0; i<7; i++) sc.next();
            col3.add(sc.nextDouble());
            sc.nextLine();
        }
        sc.close();
        double coeff =.22;
        double[] pressures = new double[]{.1, .5, 1, 2,3,4,5};
        for(int i = 0; i<pressures.length; i++) pressures[i]*=coeff;
        coeff = .69;
        double[] n2Pressures = new double[]{.1, .5, 1, 2,3,4,5};
        for(int i = 0; i<pressures.length; i++) n2Pressures[i]*=coeff;

        String[][] vals = NumberEngine.method(ratios, col1, col2, col3);
        String[] adsVals = vals[0];
        String[] desVals = vals[1];
        String[] n2Vals = vals[2];
        index = 0;
        int adsValIndex = 0;
        int desValIndex = 0;
        int n2ValIndex = 0;
        for(double ratio: ratios){
    
            output.append(ratio+","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+","+adsVals[adsValIndex++]+",,"+n2Pressures[index++%7]+","+col3.remove(0)+","+n2Vals[n2ValIndex++]+"\n");
            output.append(","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+","+adsVals[adsValIndex++]+",,"+n2Pressures[index++%7]+","+col3.remove(0)+","+n2Vals[n2ValIndex++]+"\n");
            output.append(","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+","+desVals[desValIndex++]+",,"+n2Pressures[index++%7]+","+col3.remove(0)+"\n");
            output.append(","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+","+desVals[desValIndex++]+",,"+n2Pressures[index++%7]+","+col3.remove(0)+"\n");
            output.append(","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+",,,"+n2Pressures[index++%7]+","+col3.remove(0)+"\n");
            output.append(","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+",,,"+n2Pressures[index++%7]+","+col3.remove(0)+"\n");
            output.append(","+pressures[index%7]+","+col1.remove(0)+","+col2.remove(0)+",,,"+n2Pressures[index++%7]+","+col3.remove(0)+"\n");
            /*
            output.append("CO2 Langmuir Constants:," + ratio+",313K,"+adsVals[adsValIndex++]+","+adsVals[adsValIndex++]+"\n");
            output.append("N2 Langmuir Constants:," + ratio+",313K,"+n2Vals[n2ValIndex++]+","+n2Vals[n2ValIndex++]+"\n");
            output.append("CO2 Langmuir Constants:," + ratio+",393K,"+desVals[desValIndex++]+","+desVals[desValIndex++]+"\n");
            */
        }
        output.close();
    }
}
