package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import utils.Constanst;

public class ClientApp {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa tu nombre de usuario:");
        String name = scanner.nextLine();

        Socket socket = new Socket("localhost", Constanst.SERVER_PORT);
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        outputStream.writeUTF(name);
        outputStream.flush();

        System.out.println("¿Qué deseas hacer?\n 1. Buscar palabra \n 2. Agregar palabra");
        int option = scanner.nextInt();
        scanner.nextLine(); 

        outputStream.writeInt(option);
        outputStream.flush();

        if (option == 1) {
            System.out.println("Ingresa la palabra a buscar:");
            String word = scanner.nextLine();
            outputStream.writeUTF(word);
            outputStream.flush();

            String meaning = inputStream.readUTF();
            System.out.println("Significado: " + meaning);
        } else if (option == 2) {
            System.out.println("Ingresa la palabra:");
            String word = scanner.nextLine();
            System.out.println("Ingresa el significado:");
            String meaning = scanner.nextLine();

            outputStream.writeUTF(word);
            outputStream.writeUTF(meaning);
            outputStream.flush();

            String response = inputStream.readUTF();
            System.out.println(response);
        }

        outputStream.close();
        inputStream.close();
        socket.close();
        scanner.close();
    }
}