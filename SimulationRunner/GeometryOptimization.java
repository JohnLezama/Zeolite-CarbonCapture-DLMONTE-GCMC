import java.util.*;
import java.io.*;
public class GeometryOptimization{
    public static void main(String[] args)throws IOException{
        String absoluteDirectory = "C://(INSERT PATHS HERE)//Downloads//dlmonte_sims//";
        Avogadro("FAU", 1.0, false, absoluteDirectory);
    }
    
    static void Avogadro(String zeo, double ratio, boolean allSilica, String absoluteDirectory) throws IOException{
        double[] latticeDimensions = RemoveDuplicates.method(zeo+".xyz", zeo+"Fixed.xyz", absoluteDirectory);
        latticeDimensions = XyzAdjuster.method(zeo+"Fixed.xyz");
        if(allSilica) return;
        double d1 = latticeDimensions[0], d2 = latticeDimensions[1], d3 = latticeDimensions[2]; 
        double initialRatio = AlInserter.method(zeo+"Fixed");
        int numAtoms = NaPlacer.method(d1, d2, d3, zeo+"FixedAlInserted", zeo+"NaInserted.xyz");
        RatioSetter.createFile(zeo+"NaInserted.xyz", zeo+ (int)(ratio)+ "," + (int)Math.round(ratio%1*10)+"ratioSet.xyz", numAtoms, ratio);
        File f1 = new File(zeo+"Fixed.xyz");
        f1.delete();
        f1 = new File(zeo+"FixedAlInserted.xyz");
        f1.delete();
        f1 = new File(zeo+"NaInserted.xyz");
        f1.delete();
    }
}