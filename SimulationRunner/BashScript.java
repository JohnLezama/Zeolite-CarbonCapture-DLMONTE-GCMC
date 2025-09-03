import java.io.*;
public class BashScript{
    static void runSimulation(String directory){
        String command = "";
        command = "/mnt/c/users/aul12/Downloads/dlmonte_sims/"+directory+"/./DLMONTE-SRL.X"; 
        ProcessBuilder processBuilder = new ProcessBuilder("wsl", command);
        processBuilder.directory(new File("C:\\users\\aul12\\Downloads\\dlmonte_sims\\"+directory));
        try{
            Process process = processBuilder.start();
            process.waitFor();
        }
        catch(Exception e){
            e.printStackTrace();
        }        
    }
}