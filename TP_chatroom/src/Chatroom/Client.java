package Chatroom;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 2022);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            Thread serverListener = new Thread(new Runnable() {
                public void run() {
                    try {
                        String serverMessage;
                        while ((serverMessage = in.readLine()) != null) {
                            System.out.println("Server: " + serverMessage);
                        }
                    } catch (IOException e) {
                        System.out.println("Disconnected from the server.");
                    }
                }
            });
            serverListener.start();

            String userMessage;
            while (true) {
                userMessage = scanner.nextLine();
                out.println(userMessage);
                if (userMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}