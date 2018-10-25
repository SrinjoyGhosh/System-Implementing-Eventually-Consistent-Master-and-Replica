/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system2506;


import java.net.Socket;

/**
 *
 * @author Srinjoy Ghosh
 */
public class TCPClient {

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Client Started !");

            int counter = 1;
            
            Socket socket = new Socket("localhost", 56025);
            
            System.out.println("Socket created: ");

            Client_Thread ct = new Client_Thread(socket, counter);
            ct.start();

            System.out.println("Client Thread Started");            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
