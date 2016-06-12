package com.example;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * Created by Gary on 16/5/28.
 */
public class MyClass extends JFrame implements Runnable{
    private JTextArea textarea = new JTextArea();
    private Thread thread;
    private ServerSocket servSock;
    private JTextArea text = new JTextArea();

    public MyClass(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(text);

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            text.append("IP : "+IP.getHostAddress()+"\n");
            text.append("Waitting to connect......\n");
            // Create server socket
            servSock = new ServerSocket(8888);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("ERROR");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        BufferedReader reader;
        while (true) {
            try{
                // After client connected, create client socket connect with client
                System.out.println(servSock.getLocalSocketAddress());
                System.out.println(servSock.getLocalPort());
                System.out.println(servSock.getInetAddress().getHostAddress());
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();
                System.out.println("connect");
                text.append("Connected!!");
                // Transfer data
                byte[] b = new byte[1024];
                int length;
                length = in.read(b);
                String ss = new String(b);
                text.setText("");
                text.setText("ANS ==  "+ss);
                System.out.println(ss);

            }
            catch(Exception e){
                System.out.println("Error: "+e.getMessage());
            }
        }

    }
}
