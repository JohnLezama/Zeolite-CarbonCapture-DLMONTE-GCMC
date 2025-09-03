import java.util.*;
import java.io.*;
public class AlInserter{
    static double method(String file) throws IOException{
        double distance = 2.00;
        double distance2 = 2.11;
        Scanner sc = new Scanner(new File(file+".xyz"));
        FileWriter out = new FileWriter(file+"AlInserted.xyz");
        List<double[]> Si = new ArrayList<>();
        List<double[]> O = new ArrayList<>();
        List<double[]> Al = new ArrayList<>();
        out.append(sc.nextLine()+"\n"+sc.nextLine()+"\n");
        while(sc.hasNextLine()){
            String elem = sc.next();
            if(elem.equals("O")) O.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            else if(elem.equals("Si")) Si.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            sc.nextLine();
        }
        sc.close();
       while(Si.size()>Al.size()){
           Al.add(getFurthest(Si, Al));
       }
        for(double[] atom: Si){
            out.append("Si " + atom[0] + " " + atom[1] + " " + atom[2]+"\n");
        }
        for(double[] atom: Al){
            out.append("Al " + atom[0] + " " + atom[1] + " " + atom[2]+"\n");
        }
        for(double[] atom: O){
            out.append("O " + atom[0] + " " + atom[1] + " " + atom[2]+"\n");
        }
        out.close();
        return Si.size()/Al.size();
    }
    
    static double[] getFurthest(List<double[]> Si, List<double[]> Al){
        if(Al.isEmpty()) return Si.remove(0);
        double maxDistance = 0;
        int index = 0;
        for(int i = 0; i<Si.size(); i++){
            double[] Si1 = Si.get(i);
            double currMinDis = Double.MAX_VALUE;
            for(double[] Al1: Al){
                double dis = calculateDistance(Al1, Si1);
                if(dis<currMinDis) currMinDis = dis;
            }
            if(currMinDis>maxDistance){maxDistance = currMinDis; index = i;} 
        }
        return Si.remove(index);
    }

    static double calculateDistance(double[] atom1, double[] atom2){
        return Math.sqrt(Math.pow(atom1[0]-atom2[0],2) + Math.pow(atom1[1]-atom2[1],2) + Math.pow(atom1[2]-atom2[2],2));
    }
}