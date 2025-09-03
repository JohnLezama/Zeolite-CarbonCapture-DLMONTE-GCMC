import java.util.*;
import java.io.*;
public class OxygenIdentification{
    static ArrayList<double[]> list = new ArrayList<>();
    static double maxDistance = 2.1074;
    static HashMap<String, ArrayList<double[]>> map = new HashMap<>();
    static void IdentifyOxygen(String inputFile, String outputFile,double maxDistance, double latticeDimension1, double latticeDimension2, double latticeDimension3, int numAtoms) throws Exception{
        Scanner sc = new Scanner(new File(inputFile));
        FileWriter output = new FileWriter(outputFile);
        for(int i = 0; i<1; i++) output.append(numAtoms+"\n");sc.nextLine();sc.nextLine();
        while(sc.hasNextLine()){
            String element = sc.next();
            if(element.equals("O")){
                double[] arr = {sc.nextDouble(), sc.nextDouble(), sc.nextDouble()};
                double distance = calculateDistance(arr[0], arr[1], arr[2]);
                sc.nextLine();
                if(distance<=maxDistance){
                    if(map.containsKey("O2")) map.get("O2").add(arr);
                    else{ map.put("O2", new ArrayList<double[]>()); map.get("O2").add(arr);}
                }
                else{
                    if(map.containsKey("O1")) map.get("O1").add(arr);
                    else{ map.put("O1", new ArrayList<double[]>()); map.get("O1").add(arr);}
                }
            }
            else{
                if(element.equals("Al")){
                    double a = sc.nextDouble(), b = sc.nextDouble(), c = sc.nextDouble();
                    sc.nextLine();
                    list.add(new double[]{a, b, c});
                    output.append(element + " "+a + " " + b + " " + c + "\n");
                }
                else{
                    output.append(element + " "+ sc.nextDouble() + " " + sc.nextDouble() + " " + sc.nextDouble() + "\n");
                    sc.nextLine();
                }
            }
        }
        double tolerance = .01;
        for(int i = 0; map.containsKey("O1") && i<map.get("O1").size(); i++){
            double[] arr = map.get("O1").get(i);
            if(calculateDistance(arr[0]+latticeDimension1, arr[1], arr[2])<=maxDistance){
                map.get("O2").add(map.get("O1").remove(i));
            }
            else if(calculateDistance(arr[0]-latticeDimension1, arr[1], arr[2])<=maxDistance){
                map.get("O2").add(map.get("O1").remove(i));
            }
            else if(calculateDistance(arr[0], arr[1]+latticeDimension2, arr[2])<=maxDistance){
                map.get("O2").add(map.get("O1").remove(i));
            }
            else if(calculateDistance(arr[0], arr[1]-latticeDimension2, arr[2])<=maxDistance){
                map.get("O2").add(map.get("O1").remove(i));
            }
            else if(calculateDistance(arr[0], arr[1], arr[2]+latticeDimension3)<=maxDistance){
                map.get("O2").add(map.get("O1").remove(i));
            }
            else if(calculateDistance(arr[0], arr[1], arr[2]-latticeDimension3)<=maxDistance){
                map.get("O2").add(map.get("O1").remove(i));
            }
        }
        if(map.containsKey("O1")){
            for(double[] arr: map.get("O1")){
                output.append("O1 "+arr[0] + " " + arr[1] + " " + arr[2]+"\n");
            }
        }
        if(map.containsKey("O2")){
            for(double[] arr: map.get("O2")){
                output.append("O2 "+arr[0] + " " + arr[1] + " " + arr[2]+"\n");
            }
        }
        sc.close();
        output.close();
        list.clear();
        map.clear();
    }

    static double calculateDistance(double a, double b, double c){
        double minDistance =  maxDistance+1;
        for(double[] arr : list){
            double distance = Math.sqrt(Math.pow((arr[0]-a), 2) + Math.pow((arr[1]-b), 2) + Math.pow((arr[2]-c), 2));
            if(distance<minDistance) minDistance = distance;
        }
        return minDistance;
    }
}
