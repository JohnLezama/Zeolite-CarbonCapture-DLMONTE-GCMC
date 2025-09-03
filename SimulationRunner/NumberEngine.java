import java.util.*;
import java.io.*;
public class NumberEngine{
    static String[][] method(double[] ratios, List<Double> list1, List<Double> list2, List<Double> N2) throws IOException{
        int index = 0;
        String[] adsorptionOut = new String[18];
        String[] desorptionOut = new String[18];
        String[] n2Out = new String[18];
        int l1Index = 0, l2Index = 0, l3Index = 0;
        for(double ratio: ratios){
            double[] adsorptionValues = new double[7];
            double[] desorptionValues = new double[7];
            double[] n2Values = new double[7];
            for(int i = 0; i<7; i++){
                adsorptionValues[i] = list1.get(index);
                desorptionValues[i] = list2.get(index);
                n2Values[i] = N2.get(index++);
            }
            double coeff = .22;
            double[] pressures = {.1*coeff, .5*coeff, coeff, 2*coeff, 3*coeff, 4*coeff, 5*coeff};
            String[] curr = getValues(adsorptionValues, pressures);
            adsorptionOut[l1Index++] = curr[0];
            adsorptionOut[l1Index++] = curr[1];
            curr = getValues(desorptionValues, pressures);
            desorptionOut[l2Index++] = curr[0];
            desorptionOut[l2Index++] = curr[1];
            coeff = .69;
            pressures = new double[]{.1*coeff, coeff, 2*coeff, 3*coeff, 4*coeff, 5*coeff};
            curr = getValues(n2Values, pressures);
            n2Out[l3Index++] = curr[0];
            n2Out[l3Index++] = curr[1];
        }
        return new String[][]{adsorptionOut, desorptionOut, n2Out};
    }

    static void method2(String file, String absoluteDirectory) throws IOException{
        Scanner sc = new Scanner(new File(absoluteDirectory + file));
        sc.nextLine();
        int mod = 0;
        double[] adsorptionValues = new double[6];
        for(int x = 0; x<18; x++){
            for(int j = 0; j<6; j++){
                for(int i = 0; i<7; i++) sc.next();
                adsorptionValues[j] = sc.nextDouble();
                sc.nextLine();
            }
            double coeff = .22;
            double[] pressures = {.1*coeff, coeff, 2*coeff, 3*coeff, 4*coeff, 5*coeff};
            String[] curr = getValues(adsorptionValues, pressures);
            System.out.println(curr[0]);
            System.out.println(curr[1]);
            mod++;
            if(mod%2==0){System.out.println(); System.out.println();}
        }
    }

    static String[] getValues(double[] testValues, double[] pressures){
        double minA = 0;
        double minB = 0;
        double evaluator = evaluate(.01, .01, testValues, pressures);
        double minEvaluation = evaluator;
        for(double a = .01;a<12.0; a+=.01){
            for(double b = .01;b<200.0;b+=.01){
                evaluator = evaluate(a,b, testValues, pressures);
                if(evaluator<minEvaluation){
                    minA = a;
                    minB = b;
                    minEvaluation = evaluator;
                }
            }
        }
        System.out.println(minEvaluation);
        return new String[]{String.format("%.3f", minA), String.format("%.3f", minB)};
    }

    static double evaluate(double a, double b, double[] testValues, double[] pressures){
        double evaluator = 0;
        for(int index = 0; index<pressures.length; index++){
            evaluator+=Math.pow(((a*b*pressures[index])/(1+b*pressures[index])-testValues[index]),2);
        }
        return evaluator;
    }
}