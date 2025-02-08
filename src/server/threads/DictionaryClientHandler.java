package server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class DictionaryClientHandler extends Thread{

    private DataInputStream clientInputStream;
    private DataOutputStream clientOutputStream;
    private String name;
    private Map<String, String> dictionary;

    public DictionaryClientHandler(Socket clientSocket, String name, Map<String, String> dictionary) throws IOException {
        this.clientInputStream = new DataInputStream(clientSocket.getInputStream());
        this.clientOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        this.name = name;
        this.dictionary = dictionary;
    }
    @Override
    public void run() {
        try {
            while (true) {
                int option = clientInputStream.readInt();

                if (option == 1) {
                 
                    String word = clientInputStream.readUTF();
                    String meaning = dictionary.getOrDefault(word, "Palabra no encontrada en el diccionario.");
                    clientOutputStream.writeUTF(meaning);
                    clientOutputStream.flush();

                } else if (option == 2) {
                    
                    String word = clientInputStream.readUTF();
                    String meaning = clientInputStream.readUTF();
                    dictionary.put(word, meaning);
                    clientOutputStream.writeUTF("Palabra agregada correctamente.");
                    clientOutputStream.flush();

                } else if (option == 3) {
                    System.out.println("Cliente " + this.name + " se ha desconectado.");
                    break; 
                } else {
                    clientOutputStream.writeUTF("Opción inválida.");
                    clientOutputStream.flush();
                }
            }
        } catch (SocketException se) {
            System.out.println("Conexión cerrada con cliente " + this.name + ".");
        } catch (IOException ioe) {
            System.out.println("Error de cliente " + this.name );
        } finally {
            try {
                clientInputStream.close();
                clientOutputStream.close();
            } catch (IOException e) {
                System.err.println("Error ");
            }
        }
    }
}