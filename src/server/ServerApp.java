package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.threads.DictionaryClientHandler;
import utils.Constanst;

public class ServerApp {
    private static Map<String, String> dictionary = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Constanst.SERVER_PORT);
        System.out.println("Servidor levantado en el puerto: " + Constanst.SERVER_PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();

            DataOutputStream clientOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            DataInputStream clientInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            String name = clientInputStream.readUTF();
            System.out.println("Cliente conectado: " + name);

     
            new DictionaryClientHandler(clientSocket, name, dictionary).start();
        }
    }
}
