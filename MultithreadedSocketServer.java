/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;


import java.util.ArrayList;
import system2506.ServerClientThread;


/**
 *
 * @author Srinjoy Ghosh
 */
public class MultithreadedSocketServer {

    public static volatile ArrayList<FileAndLasMoDate> fileobj_pass = new ArrayList<FileAndLasMoDate>();

    public static void main(String[] args) throws Exception {
        try {
            int updatetracker = 0;
            int counter = 0;

            System.out.println("Server Started ....");

            counter++;
            System.out.println(" >> " + "Client No:" + counter + " started!");

            Server_Thread st = new Server_Thread();
            st.start();
            System.out.println("T2 invoked");

            ServerClientThread sct = new ServerClientThread(); //send  the request to a separate thread
            sct.start();
            System.out.println("T1 invoked");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
