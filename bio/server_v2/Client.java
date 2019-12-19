package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        System.out.println("I am client");
        try {
            Socket socket = new Socket("127.0.0.1", 18080);
            String inputStr="I am client";
            socket.getOutputStream().write(inputStr.getBytes());
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            char[] dataChar = new char[4000];
            br.read(dataChar, 0, 4000);
            System.out.print(new String(dataChar));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}