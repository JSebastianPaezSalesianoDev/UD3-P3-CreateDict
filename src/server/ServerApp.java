package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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

            int option = clientInputStream.readInt();

            if (option == 1) {
                // Buscar palabra
                String word = clientInputStream.readUTF();
                String meaning = dictionary.getOrDefault(word, "Palabra no encontrada en el diccionario.");
                clientOutputStream.writeUTF(meaning);
                clientOutputStream.flush();
            } else if (option == 2) {
                // Agregar palabra
                String word = clientInputStream.readUTF();
                String meaning = clientInputStream.readUTF();
                dictionary.put(word, meaning);
                clientOutputStream.writeUTF("Palabra agregada correctamente.");
                clientOutputStream.flush();
            }

            clientOutputStream.close();
            clientInputStream.close();
            clientSocket.close();
        }
    }
}