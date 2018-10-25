/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;


import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Srinjoy Ghosh
*/

public class ServerClientThread extends Thread implements Serializable {

    static ArrayList<FileAndLasMoDate> fileobj_curr = new ArrayList<FileAndLasMoDate>();  //current directory content
    static ArrayList<FileAndLasMoDate> fileobj_prev = new ArrayList<FileAndLasMoDate>();  //previous directory content
    static int updateTracker = 0;    
    
    public static ArrayList<FileAndLasMoDate> listFilesAndFolders(String directoryName, ArrayList<FileAndLasMoDate> fileobj) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            FileAndLasMoDate readFile = new FileAndLasMoDate(file.getName(), file.lastModified());
            fileobj.add(readFile);
        }
        return fileobj;
    }
  
    
    
    public void run() {
        System.out.println("+++++++++++++++++++++++++++++++SERVER_CLIENT_THREAD+++++++++++++++++++++++++++++++++");
        try {
            int counter = 0;

            System.out.println("T1: Entered run of server-client thread");

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            System.out.println("===============================================");
            
            MultithreadedSocketServer mtsv=new MultithreadedSocketServer();
            FileModificationChecker fc=new FileModificationChecker();
            int update_compare,update_delete=0;
            do {                

                fileobj_curr.clear();
                fileobj_curr = listFilesAndFolders("F:/content_server/", fileobj_curr);
                update_compare = fc.compareArrayLists(fileobj_curr, fileobj_prev);
                update_delete = fc.deletefile(fileobj_prev, fileobj_curr); 
                
                if(update_compare==1 || update_delete==1)
                    updateTracker=1;
                else
                    updateTracker=0;
                
                System.out.println("T1: "+counter+": Update Tracker = "+updateTracker);;
                if (updateTracker == 1) {
                    //send curr to T2
                    mtsv.fileobj_pass=fileobj_curr;
                    System.out.println("T1: "+counter+": Server directory content t1: ");              
                    for (FileAndLasMoDate filevar1 : mtsv.fileobj_pass) {
                        System.out.println("T1: "+counter+": [" + filevar1.getFilename() + "] [" + "Last Modified: " + sdf.format(filevar1.getLastMoDate()) + "]");
                    }  
                    updateTracker=0;
                }
                
//                if (counter == 5) {
//                    break;
//                }
                
                counter++;
                fileobj_prev.clear();
                fileobj_prev.addAll(fileobj_curr);
                sleep(10000);

            } while (true);

           // System.out.println("T1: Outside while of T1");            
            
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("finally!!!!!!!!!!!!!!!!!!!");
        }
    }
}
