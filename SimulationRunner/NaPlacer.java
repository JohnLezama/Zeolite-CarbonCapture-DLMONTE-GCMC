import java.util.*;
import java.io.*;
public class NaPlacer{
    static int method(double l1, double l2, double l3, String file, String fileOut) throws IOException{
        double d = 2.5;
        Scanner sc = new Scanner(new File(file+".xyz"));
        sc.nextLine();
        ArrayList<double[]> Al = new ArrayList<>();
        ArrayList<double[]> Na = new ArrayList<>();
        ArrayList<double[]> O = new ArrayList<>();
        ArrayList<double[]> Si = new ArrayList<>();
        while(sc.hasNextLine()){
            String elem = sc.next();
            if(elem.equals("O")) O.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            if(elem.equals("Al")) Al.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            if(elem.equals("Si")) Si.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            sc.nextLine();
        }
        sc.close();
        int counter = 0;
        for(double[] atom:Al){
            double[][] positions = generatePositions(d,atom);
            double[] Na1 = getPos(positions, O, Si, Na, Al, l1, l2, l3); 
            if(Na1 != null){
                Na.add(Na1);
                counter++;
            }
        }
        ArrayList<double[]> lst = new ArrayList<>();
        lst.addAll(Si); lst.addAll(Al); lst.addAll(O); lst.addAll(Na);
        FileWriter out = new FileWriter(fileOut);
        out.append(lst.size()+"\n");
        int tempAtoms = lst.size();
        sc = new Scanner(new File(file+".xyz"));
        sc.nextLine(); sc.nextLine();
        while(!lst.isEmpty()){
            String elem = "";
            double[] curr = lst.remove(0);
            if(!Si.isEmpty()){
                elem = "Si";
                Si.remove(0);
            }
            else if(!Al.isEmpty()){
                elem = "Al";
                Al.remove(0);
            }
            else if(!O.isEmpty()){
                elem = "O";
                O.remove(0);
            }
            else if(!Na.isEmpty()){
                elem = "Na";
                Na.remove(0);
            }
            out.append(elem + " " + curr[0] + " " + curr[1] + " " + curr[2] + "\n");
        }
        sc.close();
        out.close();
        return tempAtoms;
    }

    static double[][] generatePositions(double d, double[] atom){
        double x1= atom[0], y1 = atom[1], z1 = atom[2];
        double[][] positions = new double[7688][];
        for(int i =0, index = 0; i<=90; i+=3){
            for(int j = 0; j<=90; j+=3){
                double x = d*Math.cos(Math.toRadians(j));
                double y = d*Math.sin(Math.toRadians(j));
                double z = d*Math.cos(Math.toRadians(i));
                positions[index++]= new double[]{x1+x,y1+y,z1+z};
                positions[index++]= new double[]{x1-x,y1+y,z1+z};
                positions[index++]= new double[]{x1+x,y1-y,z1+z};
                positions[index++]= new double[]{x1-x,y1-y,z1+z};
                positions[index++]= new double[]{x1+x,y1+y,z1-z};
                positions[index++]= new double[]{x1-x,y1+y,z1-z};
                positions[index++]= new double[]{x1+x,y1-y,z1-z};
                positions[index++]= new double[]{x1-x,y1-y,z1-z};
            }
        }
        return positions;
    }

    static boolean inBounds(double[] atom, double l1, double l2, double l3){
        double x= atom[0], y = atom[1], z = atom[2];
        return x>=0 && x<=l1 && y>=0 && y<=l2 && z>=0 && z<=l3;
    }

    static List<double[]> nearbyAtoms(double[] atom1, double d, List<double[]> atoms){
        double x = atom1[0], y = atom1[1], z = atom1[2];
        d = d*2;
        List<double[]> output = new ArrayList<>();
        for(double[] atom : atoms){
            double x1 = atom[0], y1= atom[1], z1 = atom[2];
            if(x1==x && y1==y && z1==z) continue;
            double distance = calculateDistance(atom, new double[]{x,y,z});
            if(distance<=d) output.add(atom);
        }
        return output;
    }

    static double calculateDistance(double[] atom1, double[] atom2){
        return Math.sqrt(Math.pow(atom1[0]-atom2[0],2) + Math.pow(atom1[1]-atom2[1],2) + Math.pow(atom1[2]-atom2[2],2));
    }

    static boolean notTouching(double[] atom, List<double[]> atoms){
        double tolerance = 3.5;
        for(double[] atom1: atoms){
            if(calculateDistance(atom1, atom)<tolerance) return false;
        }
        return true;
    }

    static double getNearest(double[] arr, List<double[]> arr2){
        if(arr2.size()==0) return 0;
        double minDistance = Double.MAX_VALUE;
        for(double[] arr1: arr2){
            if(arr1[0] == arr[0] && arr1[1] == arr[1] && arr1[2] == arr[2]) continue;
            double dis = calculateDistance(arr1, arr);
            if(dis<minDistance) minDistance = dis;
        }
        return minDistance;
    }

    static double[] getPos(double[][] positions, List<double[]> O, List<double[]> Si, List<double[]> Na, List<double[]> Al, double l1, double l2, double l3){
        double tolerance = 0;
        double Od = 0;
        double Sid = 0;
        double Ald = 0;
        double Nad = 0;
        double oDistance = 2.35; //2.35
        double alDistance = 3.2;
        double siDistance = 3.2;
        double naDistance = 2.5;
        double min = Double.MAX_VALUE;
        double[] curr = {-1, -1, -1};
        for(double[] atom: positions){
            if(inBounds(atom, l1, l2, l3)){
                Od = getNearest(atom, O);
                Sid = getNearest(atom, Si);
                Ald = getNearest(atom, Al);
                Nad = getNearest(atom, Na);
                double score = Math.abs(Od-oDistance)+ Math.abs(Sid-siDistance) + Math.abs(Ald-alDistance) + Math.abs(Nad-naDistance); 
                if(score<min){min=score; curr = atom;}
            }
        }
        return curr;
    }
}

class Obj implements Comparable<Obj>{
    double d;
    double[] atom;
    String elem;
    public Obj(double[] atom, double d, String elem){
        this.atom = atom;
        this.d = d;
        this.elem = elem;
    }

    public int compareTo(Obj o){
        double bias = 
            switch(elem){
                case "Si" -> 3.2;
                case "Al" -> 3.2;
                case "O" -> 2.35;
                case "Na" -> 2.5;
                default -> 0;
            };
        if( Math.abs(d-bias) < Math.abs(o.d-bias)) return -1;
        return 1;
    }

    public String toString(){
        return Arrays.toString(atom);
    }
}