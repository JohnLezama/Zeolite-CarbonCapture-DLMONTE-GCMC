import java.io.*;
public class BashScript{
    static void runSimulation(String directory, String bashScriptDirectory, String absoluteDirectory){
        String command = "";
        command = bashScriptDirectory+directory+"/./DLMONTE-SRL.X"; 
        ProcessBuilder processBuilder = new ProcessBuilder("wsl", command);
        processBuilder.directory(new File(absoluteDirectory+"dlmonte_sims\\"+directory));
        try{
            Process process = processBuilder.start();
            process.waitFor();
        }
        catch(Exception e){
            e.printStackTrace();
        }        
    }
}