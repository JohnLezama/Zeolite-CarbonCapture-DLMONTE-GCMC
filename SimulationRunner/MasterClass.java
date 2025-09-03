import java.util.Scanner;
import java.io.*;

public class MasterClass{
    public static void main(String[] args)throws Exception{
        String absoluteDirectory = Directories.absolutePath;
        String bashScriptDirectory = Directories.bashScriptPath;
        boolean isCO2 = true;
        double AlODistance = 2.1074;
        double OxygenCageDistance = Double.MAX_VALUE;
        double latticeDimension1 = 0.0, latticeDimension2 = 0.0, latticeDimension3 = 0.0;
        int tempAtoms = 0;
        double[] pressures;
        int[] temps;
        double[] ratios;
        String file;
        String directory;
        String[] Zeolites = {"FAU", "LTA", "RHO", "MFI", "MOR", "JSR", "RWY", "KFI", "FER"};
        pressures = new double[]{.1, .5, 1, 2, 3, 4, 5};
        temps = new int[]{313, 393};
        ratios = new double[]{1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5};
        for(String ZEO: Zeolites){
            file = ZEO;
            outer:
            {
                double[] latticeDimensions = RemoveDuplicates.method(file+".xyz", file+"Fixed.xyz", absoluteDirectory);
                latticeDimension1 = latticeDimensions[0]; latticeDimension2 = latticeDimensions[1]; latticeDimension3 = latticeDimensions[2]; 
                directory = "CO2"; 
                boolean inKPa = false;
                if(inKPa){
                    for(int i = 0; i<pressures.length; i++) pressures[i] *= 0.00986923;
                }
                int inARow = 0;
                for(double deltaRatio: ratios){
                    int numAtoms = getAtoms(ZEO+(int)(deltaRatio)+ "," + (int)Math.round(deltaRatio%1*10)+ "ratioSet.xyz");
                    latticeDimensions = XyzAdjuster.method(ZEO+(int)(deltaRatio)+ "," + (int)Math.round(deltaRatio%1*10)+ "ratioSet.xyz");
                    latticeDimension1 = latticeDimensions[0]; latticeDimension2 = latticeDimensions[1]; latticeDimension3 = latticeDimensions[2]; 
                    OxygenIdentification.IdentifyOxygen(ZEO+(int)(deltaRatio)+ "," + (int)Math.round(deltaRatio%1*10)+ "ratioSet.xyz", "OxygenIndentified.xyz", AlODistance, latticeDimension1, latticeDimension2, latticeDimension3, numAtoms);
                    XyzToCONFIG.createConfig("OxygenIndentified.xyz", "preConfig"+(int)(deltaRatio)+ "," + (int)(deltaRatio%1*10), latticeDimension1, latticeDimension2, latticeDimension3);    
                    CONFIGSetter.setCONFIG("preConfig"+(int)(deltaRatio)+ "," + (int)(deltaRatio%1*10),   directory, absoluteDirectory);
                    CONFIGSetter.setCONFIG("preConfig"+(int)(deltaRatio)+ "," + (int)(deltaRatio%1*10),   "N2", absoluteDirectory);
                    FIELDAdjuster.setFIELD(numAtoms, directory, absoluteDirectory);
                    FIELDAdjuster.setFIELD(numAtoms, "N2", absoluteDirectory);
                    for(int deltaTemp:temps){
                        isCO2 = true;
                        for(double deltaPressure: pressures){
                            double CO2percent = .22; 
                            CONTROLAdjuster.setCONTROL(deltaTemp, deltaPressure*CO2percent/1000, isCO2, directory, absoluteDirectory);
                            BashScript.runSimulation(directory, bashScriptDirectory, absoluteDirectory);
                            if((ExportData.writeMoleculesAdsorbed(deltaRatio, deltaTemp, deltaPressure*CO2percent, ZEO+"Data.txt", directory, absoluteDirectory))==0) inARow++;
                            else inARow = 0;
                        }
                        if(inARow==12) break outer;
                        isCO2 = false;
                        if(deltaTemp!=313) continue;
                        for(double deltaPressure: pressures){
                            double N2percent = .69;
                            CONTROLAdjuster.setCONTROL(deltaTemp, deltaPressure*N2percent/1000, isCO2, "N2", absoluteDirectory);
                            BashScript.runSimulation("N2", bashScriptDirectory, absoluteDirectory);
                            ExportData.writeMoleculesAdsorbed(deltaRatio, deltaTemp, deltaPressure*N2percent, ZEO+"N2Data.txt", "N2", absoluteDirectory);
                        }
                        
                    }
                }
                ConvertMlcstoMmol.Convert(ZEO, absoluteDirectory);
            }
        }
    }

    static int getAtoms(String file) throws IOException{
        Scanner sc = new Scanner(new File(file));
        int n = sc.nextInt();
        sc.close();
        return n;
    }
}

