/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Avisri Nandi
 */
public class Server_Thread extends Thread {

    static ArrayList<FileAndLasMoDate> fileobj_curr = new ArrayList<FileAndLasMoDate>();  //current directory content
    static ArrayList<FileAndLasMoDate> fileobj_prev = new ArrayList<FileAndLasMoDate>();  //previous directory content
    static ArrayList<FilesToFetch> files_to_fetch = new ArrayList<FilesToFetch>(); //stores files to be fetched from server
   
    static int updateTracker = 0;
   // Socket server_thread_socket;
    ServerSocket server;
    Socket socket = null;

//    public Socket getServerClient() {
//        return server_thread_socket;
//    }

    public static ArrayList<FileAndLasMoDate> listFilesAndFolders(String directoryName, ArrayList<FileAndLasMoDate> fileobj) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            FileAndLasMoDate readFile = new FileAndLasMoDate(file.getName(), file.lastModified());
            fileobj.add(readFile);
        }
        return fileobj;
    }
//
//    public static void SendFile(String filename,Socket s) throws FileNotFoundException, IOException, InterruptedException{
//    //   try{
//        System.out.println("Waiting...");
//        String FILE_TO_SEND = "F:/content_client/" + filename;
//        FILE_TO_SEND.trim();
//        System.out.println(FILE_TO_SEND);
//
//        byte[] mybytearray = new byte[4096];
//        InputStream is = s.getInputStream();
//        FileOutputStream fos = new FileOutputStream(FILE_TO_SEND);
//        BufferedOutputStream bos = new BufferedOutputStream(fos);
//        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
//        int bytesRead = is.read(mybytearray, 0, mybytearray.length);
//        bos.write(mybytearray, 0, bytesRead);
//        System.out.println("Sent " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
//        mybytearray=null;
//        bos.close();
//
//
//
//    }
//    
    
    
    public static void SendFile(String filename,Socket s) throws FileNotFoundException, IOException, InterruptedException{
  
        System.out.println("Waiting...");
        String FILE_TO_SEND = "F:/content_server/" + filename;
        FILE_TO_SEND.trim();
        
        System.out.println(FILE_TO_SEND);
        File myFile = new File(FILE_TO_SEND);
        byte[] mybytearray = new byte[(int) myFile.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = s.getOutputStream();
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        System.out.println("Sent " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
        mybytearray = null;




    }
    public void run() {
        System.out.println("+++++++++++++++++++++++++++++++SERVER_THREAD+++++++++++++++++++++++++++++++++");
        try {
            System.out.println("T2: Entered run of server_thread");

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            System.out.println("===============================================");

            MultithreadedSocketServer mtsv = new MultithreadedSocketServer();

            try {
                int counter = 0;
                int client = 0;

                System.out.println("T2: Inside try of T2");

                server = new ServerSocket(56025);
                
                System.out.println("--------Server_socket created---------");

                ObjectInputStream ois = null;
                ObjectOutputStream oos = null;
                
               
                client++;
                
               
                
                System.out.println("T2: Server directory content t2 that should be received : ");

                for (FileAndLasMoDate filevar1 : mtsv.fileobj_pass) {
                    System.out.println("T2: [" + filevar1.getFilename() + "] [" + "Last Modified: " + sdf.format(filevar1.getLastMoDate()) + "]");
                }
                
                FileModificationChecker fc=new FileModificationChecker();
                
                int first_time_flag = 0;
                int update_compare,update_delete=0;
                
                if (client > 0) {
                    do {
                        
                        System.out.println("T2: " + counter + ":Inside while of Server_Thread");
                        System.out.println("T2: " + counter + ":First time flag : " + first_time_flag);
                        System.out.println("T2: " + counter + ":Update tracker : " + updateTracker);
                        
                        try {
                            synchronized (this) {
                                System.out.println("T2: " + counter + ":Commencing handshake");                                

                                System.out.println("T2: " + counter + ":Sending data");

                                if (first_time_flag == 0) {
                                    first_time_flag = 1;
                                  
                                    System.out.println("T2: waiting for client socket");
                                    
                                    socket = server.accept();
                                    
                                    System.out.println("T2: created client socket");
                                    
                                    ois = new ObjectInputStream(socket.getInputStream());
                                    oos = new ObjectOutputStream(socket.getOutputStream());
                                    oos.flush();
                                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                    DataInputStream dis = new DataInputStream(socket.getInputStream());


                                    
                                    
                                    System.out.println("T2: " + counter + ":initial Server directory content t2 : ");
                                    for (FileAndLasMoDate filevar1 : mtsv.fileobj_pass) {
                                        System.out.println("T2: " + counter + ":[" + filevar1.getFilename() + "] [" + "Last Modified: " + sdf.format(filevar1.getLastMoDate()) + "]");
                                    }

                                    fileobj_curr = mtsv.fileobj_pass;

                                    System.out.println("T2: " + counter + ":Server directory content t2 final: ");
                                    
                                    for (FileAndLasMoDate filevar1 : fileobj_curr) {
                                        System.out.println("T2: " + counter + ":[" + filevar1.getFilename() + "] [" + "Last Modified: " + sdf.format(filevar1.getLastMoDate()) + "]");
                                    }
                                    //send curr to client
                                    
                                    System.out.println("Proceeding send File List to client");
                                    
                                    oos.writeObject(fileobj_curr);
                                  
                                    oos.flush();
                                    oos.reset();
                                    
                                    System.out.println("T2: " + counter + ":Sent server content to client"); 
                                    
                                    System.out.println("Proceeding to read File List from client");
                                    
                                    files_to_fetch=(ArrayList<FilesToFetch>)ois.readObject();
                                    
                                    System.out.println("File name(s) fetched from client are: ");
                                    
                                    for (FilesToFetch filevar1 : files_to_fetch) {
                                        System.out.println(filevar1.getFilename());
                                    }
                                    
                                     for (FilesToFetch filevar1 : files_to_fetch){
                                         SendFile(filevar1.getFilename(),socket);
                                         sleep(2000);
                                     }
                                    
                                } else {
                                    System.out.println("T2: " + counter + ":entered else of T2");
                                    
                                    
                                    update_compare = fc.compareArrayLists(fileobj_curr, fileobj_prev);
                                    update_delete = fc.deletefile(fileobj_prev, fileobj_curr);

                                    if (update_compare == 1 || update_delete == 1) {
                                        updateTracker = 1;
                                    } else {
                                        updateTracker = 0;
                                    }

                                    System.out.println("T2: " + counter + ":Update Tracker = " + updateTracker);
                                    
                                    if (updateTracker == 1) {
                                        //send curr to T2
                                        fileobj_curr = mtsv.fileobj_pass;
                                        System.out.println("T2: " + counter + ":Server directory content t2 final after change: ");
                                        for (FileAndLasMoDate filevar1 : fileobj_curr) {
                                            System.out.println("T2: " + counter + ":[" + filevar1.getFilename() + "] [" + "Last Modified: " + sdf.format(filevar1.getLastMoDate()) + "]");
                                        }
                                        updateTracker = 0;
                                        oos.reset();
                                        oos.writeObject(fileobj_curr);
                                        oos.flush();
                                        System.out.println("T2: " + counter + ":Sent server content to client");
                                        
                                        System.out.println("Proceeding to read File List from client");
                                    
                                    files_to_fetch=(ArrayList<FilesToFetch>)ois.readObject();
                                    
                                    System.out.println("File name(s) fetched from client are: ");
                                    
                                    for (FilesToFetch filevar1 : files_to_fetch) {
                                        System.out.println(filevar1.getFilename());
                                    }
                                    
                                    for (FilesToFetch filevar1 : files_to_fetch){
                                         SendFile(filevar1.getFilename(),socket);
                                         sleep(2000);
                                     }
                                        
                                    }

                                    System.out.println("T2: " + counter + ":Sent data to client from server");                                   

                                    System.out.println("T2: " + counter + ":Data from client received");                                   
                                }

                                fileobj_prev.clear();
                                fileobj_prev.addAll(fileobj_curr);

                                counter++;

//                                if (counter == 10) {
//                                    break;
//                                }

                                sleep(4000);

                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Server_Thread.class.getName()).log(Level.SEVERE, null, ex);
                        }catch (Exception ex) {
                          System.out.println(ex);
        }
                    } while (true);
                } else {
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server_Thread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
