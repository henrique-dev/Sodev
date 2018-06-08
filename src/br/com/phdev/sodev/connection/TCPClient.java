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
public class TCPClient extends Thread implements ReadListener{
    
    private int port;
    private String ip;
    
    private Socket socket;
    
    private Scanner in;
    private PrintWriter out;
    
    private WriteListener writeListener;
    
    public TCPClient(int port, String ip, WriteListener writeListener) {
        this.port = port;
        this.ip = ip;
        this.writeListener = writeListener;
    }
    
    public ReadListener getReadListener() {
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
                writeListener.write(msg);
            }                        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Desconectando");
        }
    }

    @Override
    public void read(String msg) {
        out.write(msg);
    }
    
}
