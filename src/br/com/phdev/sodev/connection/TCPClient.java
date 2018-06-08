/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.phdev.sodev.connection;

import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class TCPClient extends Thread implements WriteListener{
    
    private int port;
    private String ip;
    
    private Socket socket;
    
    private Scanner in;
    private PrintWriter out;
    
    private ReadListener readListener;
    
    public TCPClient(int port, String ip, ReadListener readListener) {
        this.port = port;
        this.ip = ip;
        this.readListener = readListener;
    }
    
    public WriteListener getWriteListener() {
        return this;
    }
    
    @Override
    public void run() {
        try {
            this.socket = new Socket(Inet4Address.getByName(ip), port);
            System.out.println("Conectou");
            
            in = new Scanner(this.socket.getInputStream());
            out = new PrintWriter(this.socket.getOutputStream());
            
            String msg = "";
            
            while ((msg = in.nextLine()) != null) {
                readListener.read(msg);
            }                        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Desconectando");
        }
    }

    @Override
    public void write(String msg) {
        out.write(msg);
        out.flush();
    }
    
}
