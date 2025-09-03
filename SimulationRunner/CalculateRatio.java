import java.util.*;
import java.io.*;
public class CalculateRatio{
    static double tolerance = .1;
    public static void main(String[] args) throws IOException{
        String[] zeo = {"FAU", "LTA", "RHO", "MFI", "MOR", "JSR", "RWY", "KFI", "FER"};
        for(String z: zeo){
            double[] latticeDimensions = RemoveDuplicates.method(z+".xyz", z+"Fixed.xyz");
            System.out.println(z+ ", " + method(z+"Fixed.xyz", latticeDimensions));
        }
    }

    static double method(String file, double[] latticeDimensions) throws IOException{
        Scanner sc = new Scanner(new File(file));
        double l1 = latticeDimensions[0], l2 = latticeDimensions[1], l3 = latticeDimensions[2];
        sc.nextLine();
        double distance = 2.00;
        double distance2 = 2.11;
        List<double[]> Si = new ArrayList<>();
        List<double[]> O = new ArrayList<>();
        List<double[]> Al = new ArrayList<>();
        while(sc.hasNextLine()){
            String elem = sc.next();
            if(elem.equals("O")) O.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            else if(elem.equals("Si")) Si.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            sc.nextLine();
        }
        sc.close();
        List<double[]> extraO = new ArrayList<>();
        List<double[]> extraAl = new ArrayList<>();
        Outer:
        {
            for(int i = 0; i<Si.size(); i++){
                double[] atom = Si.get(i);
                extraO.clear();
                extraAl.clear();
                if(atom[0] >= l1-4.5){
                    for(double[] O1: O){
                        if(O1[0]<=2.2) extraO.add(new double[]{O1[0]+l1, O1[1], O1[2]});
                        extraO.add(O1);
                    }
                    for(double[] Al1: Al){
                        if(Al1[0]<=4.5) extraAl.add(new double[]{Al1[0]+l1, Al1[1], Al1[2]});
                        extraAl.add(Al1);
                    }
                }

                else if(atom[1] >= l2-4.5){
                    for(double[] O1: O){
                        if(O1[1]<=2.2) extraO.add(new double[]{O1[0], O1[1]+l2, O1[2]});
                        extraO.add(O1);
                    }
                    for(double[] Al1: Al){
                        if(Al1[1]<=4.5) extraAl.add(new double[]{Al1[0], Al1[1]+l2, Al1[2]});
                        extraAl.add(Al1);
                    }
                }

                else if(atom[2] >= l3-4.5){
                    for(double[] O1: O){
                        if(O1[2]<=2.2) extraO.add(new double[]{O1[0], O1[1], O1[2]+l3});
                        extraO.add(O1);
                    }
                    for(double[] Al1: Al){
                        if(Al1[2]<=4.5) extraAl.add(new double[]{Al1[0], Al1[1], Al1[2]+l3});
                        extraAl.add(Al1);
                    }
                }
                else{
                    for(double[] O1: O){
                        extraO.add(O1);
                    }
                    for(double[] Al1: Al){
                        extraAl.add(Al1);
                    }
                }

                outer:
                {
                    for(double[] O1: extraO){
                        double d = calculateDistance(atom,O1);
                        if(d<=distance){
                            for(double[] Al1: extraAl){
                                if(calculateDistance(Al1, O1)<distance2) break outer;
                            }
                        }
                    }
                    if(Si.size()==Al.size() || Si.size()==Al.size()+1) break Outer;
                    Al.add(Si.remove(i--));
                }
            }
        }
        return (double)Si.size()/Al.size();
    }

    static double calculateDistance(double[] atom1, double[] atom2){
        return Math.sqrt(Math.pow(atom1[0]-atom2[0],2) + Math.pow(atom1[1]-atom2[1],2) + Math.pow(atom1[2]-atom2[2],2));
    }

    static boolean outOfBounds(double x, double y, double z, double l1, double l2, double l3){
        return x<0||x>l1||y<0||y>l2||z<0||z>l3;
    }
}