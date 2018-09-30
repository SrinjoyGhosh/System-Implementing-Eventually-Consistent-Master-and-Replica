/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Srinjoy
 */
public class FileModificationChecker {
    public int compareArrayLists(ArrayList<FileAndLasMoDate> fileobj1, ArrayList<FileAndLasMoDate> fileobj2) throws IOException {        
        System.out.println("===============================================");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        int flag,updateTracker=0;
        System.out.println("T1: ARRAYLISTS TO BE ADDED/UPDATED: ");

        /* compare the current directory content with the previous directory content for files to be added and updated */
        for (FileAndLasMoDate filevar1 : fileobj1) //current directory content
        {
            flag = 0;
            for (FileAndLasMoDate filevar2 : fileobj2) //previous directory content
            {
                if (filevar1.getFilename().equals(filevar2.getFilename())) //current file found in previous
                {
                    flag = 1;
                    if (filevar1.getLastMoDate() > filevar2.getLastMoDate()) //check the last modification date
                    {                     
                        System.out.print("T1: File to be updated:  ");
                        System.out.println("T1: File Name: " + filevar1.getFilename() + "  Modification Date: " + sdf.format(filevar1.getLastMoDate()));
                        System.out.println("----------------------------------------------");
                        System.out.println("T1: Update "+filevar1.getFilename());                       
                        updateTracker = 1;
                    }
                }
            }
            if (flag == 0) //current file not found in previous
            {          
                System.out.print("T1: Files to be added: ");
                System.out.println("T1: File Name: " + filevar1.getFilename() + "  Modification Date: " + sdf.format(filevar1.getLastMoDate()));
                System.out.println("----------------------------------------------");
                System.out.println("T1: Add "+filevar1.getFilename());                
                updateTracker = 1;
            }
        }
        return updateTracker;
    }

    /**
     *
     * @param fileobj1
     * @param fileobj2
     */
    public int deletefile(ArrayList<FileAndLasMoDate> fileobj1, ArrayList<FileAndLasMoDate> fileobj2) throws IOException {
        //updateTracker=0;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        int flag = 0;
        int updateTracker=0;
        System.out.println("T1: ARRAYLISTS TO BE DELETED: ");

        /* compare the previous directory content with the current directory content for files to be deleted */
        for (FileAndLasMoDate filevar1 : fileobj1) //previous directory content
        {
            flag = 0;
            for (FileAndLasMoDate filevar2 : fileobj2) //current directory content
            {
                if (filevar1.getFilename().equals(filevar2.getFilename())) //prev file present in client
                {
                    flag = 1;                    
                }
            }

            if (flag == 0) {
                System.out.print("T1: Files to be deleted: ");
                System.out.println("T1: File Name: " + filevar1.getFilename() + "  Modification Date: " + sdf.format(filevar1.getLastMoDate()));
                System.out.println("----------------------------------------------");
                System.out.println("T1: Delete "+filevar1.getFilename());              
                updateTracker = 1;
            }
        }
        return updateTracker;
    }
}
