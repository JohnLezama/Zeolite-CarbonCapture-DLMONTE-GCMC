import java.util.*;
import java.io.*;
public class XyzAdjuster{
    static double[] method(String in) throws IOException{
        double minX = 0;
        double minY = 0;
        double minZ = 0;
        double maxX = 0, maxY = 0, maxZ = 0;
        Scanner sc = new Scanner(new File(in));
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<double[]> coords = new ArrayList<>();
        lines.add(sc.nextLine()); lines.add(sc.nextLine());
        double tolerance = .1;
        while(sc.hasNextLine()){
            String elem = sc.next();
            double x = sc.nextDouble(), y = sc.nextDouble(), z = sc.nextDouble();
            sc.nextLine();
            minX = Math.max(x*-1, minX);
            maxX = Math.max(maxX, x);
            minY = Math.max(y*-1, minY);
            maxY = Math.max(maxY, y);
            minZ = Math.max(z*-1, minZ);
            maxZ = Math.max(maxZ, z);
            boolean add = true;
            for(double[] curr : coords){
                if(Math.abs(curr[0]-x)<=tolerance && Math.abs(curr[1]-y)<=tolerance && Math.abs(curr[2]-z)<=tolerance){add=false; break;}
            }
            coords.add(new double[]{x,y,z});
            if(add) lines.add(elem + " " + x + " " + y + " " + z);
        }
        maxX+=minX; maxY+=minY; maxZ+=minZ;
        sc.close();
        FileWriter output = new FileWriter(in);
        output.append(lines.remove(0)+"\n"+lines.remove(0)+"\n");
        while(!lines.isEmpty()){
            sc = new Scanner(lines.remove(0));
            output.append(sc.next() + " " + (sc.nextDouble()+minX) + " " + (sc.nextDouble()+minY) + " " + (sc.nextDouble()+minZ)+"\n");
            sc.close();
        }
        output.close();
        return new double[]{maxX+.1, maxY+.1, maxZ+.1};
    }
}