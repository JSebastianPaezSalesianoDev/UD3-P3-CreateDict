package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
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

        System.out.println("Bienvenido, " + name + "!");

        while (true) {
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Buscar palabra");
            System.out.println("2. Agregar palabra");
            System.out.println("3. Salir");
            System.out.print("-> ");

            int option = -1;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.nextLine(); 
                continue;
            }
            scanner.nextLine(); 

            outputStream.writeInt(option);
            outputStream.flush();

            if (option == 1) {
                System.out.println("Ingresa la palabra a buscar:");
                System.out.print("-> ");
                String word = scanner.nextLine();
                outputStream.writeUTF(word);
                outputStream.flush();

                String meaning = inputStream.readUTF();
                System.out.println("Significado: " + meaning);

            } else if (option == 2) {
                System.out.println("Ingresa la palabra a agregar:");
                System.out.print("-> ");
                String word = scanner.nextLine();
                System.out.println("Ingresa el significado:");
                System.out.print("-> ");
                String meaning = scanner.nextLine();

                outputStream.writeUTF(word);
                outputStream.writeUTF(meaning);
                outputStream.flush();

                String response = inputStream.readUTF();
                System.out.println(response);

            } else if (option == 3) {
                System.out.println("Saliendo del diccionario.");
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }


        outputStream.close();
        inputStream.close();
        socket.close();
        scanner.close();
    }
}