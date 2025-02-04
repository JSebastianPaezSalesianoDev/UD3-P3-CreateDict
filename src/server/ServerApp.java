package server;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

import utils.Constanst;

public class ServerApp {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(Constanst.SERVER_PORT);
        System.out.println("Servidor levantado en el puerto: " + Constanst.SERVER_PORT);

        ArrayList<DataOutputStream> clienDataOutputs = new ArrayList<>();
        
    }
}
