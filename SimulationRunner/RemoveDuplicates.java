import java.io.*;
import java.util.*;
public class RemoveDuplicates{
    static double tolerance = .1;

    static double[] method(String file, String outFile) throws IOException{
        Scanner sc = new Scanner(new File("C://users//aul12//Downloads//Zeolites To Try//"+file));
        double l1 = 0, l2 = 0, l3 = 0;
        List<double[]> O = new ArrayList<>();
        List<double[]> Si = new ArrayList<>();
        List<double[]> Na = new ArrayList<>();
        List<double[]> Al = new ArrayList<>();
        sc.nextLine();
        l1 = sc.nextDouble(); l2 = sc.nextDouble(); l3 = sc.nextDouble(); sc.nextLine();
        while(sc.hasNextLine()){
            String elem = sc.next();
            if(elem.equals("O")) O.add(new double[]{sc.nextDouble(), sc.nextDouble(),sc.nextDouble()});
            if(elem.equals("Si")) Si.add(new double[]{sc.nextDouble(), sc.nextDouble(),sc.nextDouble()});
            if(elem.equals("Na")) Na.add(new double[]{sc.nextDouble(), sc.nextDouble(),sc.nextDouble()});
            if(elem.equals("Al")) Al.add(new double[]{sc.nextDouble(), sc.nextDouble(),sc.nextDouble()});
            sc.nextLine();
        }
        sc.close();
        List<List<double[]>> lst = new ArrayList<>();
        lst.add(O); lst.add(Si); lst.add(Na); lst.add(Al);
        for(int j = 0; j<4; j++){
            List<double[]> curr = lst.get(j);
            for(int i = 0; i<curr.size(); i++){
                double[] Oi = curr.get(i);
                for(int x = 0; x<curr.size(); x++){
                    double[] Of = curr.get(x);
                    double x1 = Oi[0], y1 = Oi[1], z1 = Oi[2];
                    double x2 = Of[0], y2 = Of[1], z2 = Of[2];
                    if(x1==x2 && y1==y2 && z1==z2) continue;
                    if((Math.abs(Math.abs(x1-x2)-l1)<=tolerance || Math.abs(x1-x2)<=tolerance) && 
                    (Math.abs(Math.abs(y1-y2)-l2)<=tolerance || Math.abs(y1-y2)<=tolerance) && 
                    (Math.abs(Math.abs(z1-z2)-l3)<=tolerance || Math.abs(z1-z2)<=tolerance)){ 
                        curr.remove(x--);
                    }
                }
            }
        }
        FileWriter out = new FileWriter(outFile);
        out.append(O.size()+Si.size()+Na.size()+Al.size()+"\n");
        for(double[] curr: lst.get(3))
            out.append("Al " + curr[0] + " " + curr[1] + " " + curr[2] + "\n");
        for(double[] curr: lst.get(1))
            out.append("Si " + curr[0] + " " + curr[1] + " " + curr[2] + "\n");
        for(double[] curr: lst.get(2))
            out.append("Na " + curr[0] + " " + curr[1] + " " + curr[2] + "\n");
        for(double[] curr: lst.get(0))
            out.append("O " + curr[0] + " " + curr[1] + " " + curr[2] + "\n");
        out.close();
        return new double[]{l1, l2, l3};
    }

    static boolean outOfBounds(double x, double y, double z, double l1, double l2, double l3){
        return x<0||x>l1||y<0||y>l2||z<0||z>l3;
    }
}