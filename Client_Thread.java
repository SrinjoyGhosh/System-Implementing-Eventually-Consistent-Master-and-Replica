
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;

import java.io.BufferedInputStream;import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Srinjoy Ghosh
 */
public class Client_Thread extends Thread implements Serializable {

    Socket client_thread_socket;
    int clientNo;
    static ArrayList<FileAndLasMoDate> fileobj_curr = new ArrayList<FileAndLasMoDate>();  //current directory content
    static ArrayList<FileAndLasMoDate> fileobj_prev = new ArrayList<FileAndLasMoDate>();  //previous directory content
    static ArrayList<FilesToFetch> files_to_fetch = new ArrayList<FilesToFetch>(); //stores files to be fetched from server
    static int updateTracker = 0;
     public final static int FILE_SIZE = 6022386;
    
    Client_Thread(Socket inSocket, int counter) {
        System.out.println("+++++++++++++++++++++++++Inside Client Thread+++++++++++++++++++++++++++++++++++");
        client_thread_socket = inSocket;
        clientNo = counter;
        System.out.println("Client Thread Constructor created ");
    }

//    public static void ReceiveFile(String filename, Socket s) throws FileNotFoundException, IOException {
//        
//    
//        
//        String FILE_TO_RECEIVE = "F:/content_server/" + filename;
//        FILE_TO_RECEIVE.trim();
//        System.out.println("Trying to Receive: "+FILE_TO_RECEIVE);
//        System.out.println("Connecting...");
//        File myFile = new File(FILE_TO_RECEIVE);
//        byte[] mybytearray = new byte[(int) myFile.length()];
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
//        bis.read(mybytearray, 0, mybytearray.length);
//        OutputStream os = s.getOutputStream();
//        os.write(mybytearray, 0, mybytearray.length);
//        
//        System.out.println("File " + FILE_TO_RECEIVE + " downloaded ");
//        mybytearray=null;
//        os.flush();
////        bos.close();
////        fos.close();
//
//    }
//    
    
        public static void ReceiveFile(String filename, Socket s) throws FileNotFoundException, IOException {
        
    
        
        String FILE_TO_RECEIVE = "F:/content_client/" + filename;
            FILE_TO_RECEIVE.trim();
            System.out.println("Trying to Receive: " + FILE_TO_RECEIVE);
            System.out.println("Connecting...");
            byte[] mybytearray = new byte[1024];
            InputStream is = s.getInputStream();
            FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVE);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            bos.write(mybytearray, 0, bytesRead);
            bos.close();

            System.out.println("File " + FILE_TO_RECEIVE + " downloaded ");
            mybytearray=null;
        
            bos.close();
            fos.close();

    }
    
    
    public void run() {
        try {
            System.out.println("Executing run of client thread- 1");
            
            
            ObjectOutputStream oos = new ObjectOutputStream(client_thread_socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client_thread_socket.getInputStream());
           
            while (true) {

                try {
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    
                    System.out.println("Inside try of while : ");
                    System.out.println("Ack sent!");

                    fileobj_curr = (ArrayList<FileAndLasMoDate>) ois.readObject();//received server content 

                    System.out.println("Printing Arraylist");
                    
                    for (FileAndLasMoDate filevar1 : fileobj_curr) {
                        System.out.println("[" + filevar1.getFilename() + "] [" + "Last Modified: " + sdf.format(filevar1.getLastMoDate()) + "]");
                    }

                    System.out.println("Received data from server: ");
                    
                     for (FileAndLasMoDate filevar1 : fileobj_curr) {
                         FilesToFetch ftf=new FilesToFetch(filevar1.getFilename());
                         files_to_fetch.add(ftf);
                     }
                    
                     
                    System.out.println("=======Fetching from Server filename(s): ==========");
                    for(FilesToFetch filevar1 : files_to_fetch ){
                        System.out.println(filevar1.getFilename());
                    } 
                    System.out.println("=====================================");
                   
                    System.out.println("Sending files: ");
                    
                    //Sending files

                    oos.writeObject(files_to_fetch);
                    
                    oos.flush();
                    
                    oos.reset();
                    
                    for(FilesToFetch filevar1 : files_to_fetch ){
                        ReceiveFile(filevar1.getFilename(), client_thread_socket);
                        sleep(2000);
                    } 
                    
                    files_to_fetch.clear();
                    fileobj_curr.clear();
                    sleep(3000);
     
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (IOException ex) {
                    Logger.getLogger(Client_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Client -" + clientNo + " exit!! ");
        }

    }
}
