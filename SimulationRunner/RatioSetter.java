import java.util.*;
import java.io.*;
public class RatioSetter{
    static LinkedList<double[]> cationList = new LinkedList<>();
    static int createFile(String inputFile, String outputFile, int numAtoms, double ratio) throws IOException{
        Scanner sc = new Scanner(new File(inputFile));
        FileWriter output = new FileWriter(outputFile);
        int numAl = 0;
        int numSi = 0;
        while(sc.hasNextLine()){
            String element = sc.next();
            if(element.equals("Al")) numAl++;
            else if(element.equals("Si")) numSi++;
            else if(element.equals("Na"))cationList.add(new double[]{sc.nextDouble(), sc.nextDouble(), sc.nextDouble()});
            sc.nextLine();
        }
        if(numAl == 0){
            sc.close();
            sc = new Scanner(new File(inputFile));
            output.append(numAtoms-2 + "\n");
            output.append("Rando Line\n");
            while(sc.hasNextLine()){
                output.append(sc.nextLine()+"\n");
            }
            output.close(); sc.close();
            return numAtoms;
        }
        else{
            int newNumAl = numAl, newNumSi = numSi;
            double prev = 0;
            while((double)newNumSi/newNumAl <ratio){
                prev = (double)newNumSi/newNumAl;
                newNumSi++; newNumAl--;
            }
            if(Math.abs(prev-ratio)<Math.abs((double)newNumSi/newNumAl - ratio)){ newNumSi--; newNumAl++;}
            int AlToSi = numAl-newNumAl;
            sc.close();
            sc = new Scanner(new File(inputFile));
            int index = 0;
            int temp = 0;
            output.append(numAtoms-AlToSi + "\nRando Line\n");
            while(sc.hasNextLine()){
                String element = sc.next();
                if(!"Al O Na Si".contains(element)) sc.nextLine();
                else{
                    if(element.equals("Al")&&AlToSi>0){
                        AlToSi--;
                        double a = sc.nextDouble(), b = sc.nextDouble(), c = sc.nextDouble();
                        if(cationList.isEmpty()) continue;
                        removeNa();
                        output.append("Si "+a+" "+b+" "+c+"\n");
                        temp++;
                        sc.nextLine();
                    }
                    else if(!element.equals("Na")){
                        output.append(element+sc.nextLine()+"\n");
                        temp++;
                    }
                    else sc.nextLine();
                }
            }
            for(double[] arr: cationList){
                output.append("Na"+" "+arr[0]+" "+arr[1]+" "+arr[2]+"\n");
                temp++;
            }
            sc.close();
            output.close();
            cationList.clear();
            return temp;
        }
    }

    static void findClosest(double a, double b, double c){
        double minDistance = Double.MAX_VALUE;
        int removeIndex =-1;
        for(int i = 0; i<cationList.size(); i++){
            double[] arr = cationList.get(i);
            double distance = Math.sqrt(Math.pow(arr[0]-a,2) + Math.pow(arr[1]-b,2) + Math.pow(arr[2]-c,2));
            if(distance<minDistance){
                minDistance = distance;
                removeIndex = i;
            }
        }
        cationList.remove(removeIndex);
    }

    static void removeNa(){
        int index1 = 0, index2 = 0;
        double distance1 = Double.MAX_VALUE, distance2 = Double.MAX_VALUE;
        double minDistance = Double.MAX_VALUE;
        for(int i = 0; i<cationList.size()-1; i++){
            for(int j = i+1; j<cationList.size(); j++){
                double distance = Math.sqrt(Math.pow(cationList.get(i)[0]-cationList.get(j)[0],2) + Math.pow(cationList.get(i)[1]-cationList.get(j)[1],2) +Math.pow(cationList.get(i)[2]-cationList.get(j)[2],2));
                if(distance<minDistance){ minDistance = distance; index1 = i; index2 = j;}
            }
        }
        double[] temp = cationList.remove(index1);
        for(int i = 0; i<cationList.size()-1; i++){
            for(int j = i+1; j<cationList.size(); j++){
                double distance = Math.sqrt(Math.pow(cationList.get(i)[0]-cationList.get(j)[0],2) + Math.pow(cationList.get(i)[1]-cationList.get(j)[1],2) +Math.pow(cationList.get(i)[2]-cationList.get(j)[2],2));
                if(distance<distance1) distance1 = distance;
            }
        }
        cationList.add(index1, temp);
        temp = cationList.remove(index2);
        for(int i = 0; i<cationList.size()-1; i++){
            for(int j = i+1; j<cationList.size(); j++){
                double distance = Math.sqrt(Math.pow(cationList.get(i)[0]-cationList.get(j)[0],2) + Math.pow(cationList.get(i)[1]-cationList.get(j)[1],2) +Math.pow(cationList.get(i)[2]-cationList.get(j)[2],2));
                if(distance<distance2) distance2 = distance;
            }
        }
        cationList.add(index2, temp);
        if(distance1<distance2){
            cationList.remove(index1);
        }
        else{
            cationList.remove(index2);
        }
    }
}