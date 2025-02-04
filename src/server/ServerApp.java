package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import utils.Constanst;

public class ServerApp {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(Constanst.SERVER_PORT);
        System.out.println("Servidor levantado en el puerto: " + Constanst.SERVER_PORT);

        ArrayList<DataOutputStream> clienDataOutputs = new ArrayList<>();

        while (true) {
            Socket clientSocket = serverSocket.accept();

            DataOutputStream clientOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        }
        
    }
}
