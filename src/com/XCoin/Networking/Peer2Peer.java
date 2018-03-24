package com.XCoin.Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.XCoin.Networking.Commands.Command;
import com.XCoin.Networking.Commands.PingCommandHandler;

public class Peer2Peer {

    private int port = 8888;
    private ArrayList<Peer>    peers;
    private DataInputStream  inputStream;
    private DataOutputStream outputStream;
    private Thread           serverThread;
    private Thread           clientThread;
    private boolean          runningServer;
    private HashMap<String, Command> commands = new HashMap<>();
    private ServerSocket server;
    private Socket socket = null;

    public Peer2Peer(int port){
    		System.out.println("Making node");
        this.port = port;
        peers = new ArrayList<>();
        serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    listen();
                    System.out.println("Connection Ended");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });		
        
        initializeCommands();

    }
    
    private void initializeCommands() {
        this.commands.put("ping", new PingCommandHandler());
       
    }

    public void start(){
        if(serverThread.isAlive()){
            System.out.println("Server is already running.");
            return;
        }
        runningServer = true;
        serverThread.start();
    }

    public void stop() throws IOException{
    		Thread t = Thread.currentThread();    		
    		runningServer = false;
    		try {
        		serverThread.interrupt();
    			socket.close();
        } catch (NullPointerException n) {
        		n.printStackTrace();
        }
        System.out.println("Server Stopped");
    }

    public void listen() throws IOException, SocketTimeoutException{
        System.out.println("Server starting...");
        server = new ServerSocket(this.port);
        System.out.println("Server started on port " + this.port);
        	
        Peer peer;
        server.setSoTimeout(10000);
        while(runningServer){
        		//System.out.println("Waiting for a connection");
        		try{
        			socket = server.accept();
                System.out.println("Passed Accept");
                peer = new Peer(socket);
                System.out.println("Connection received from: " + peer.toString());
                peers.add(peer);
                System.out.println("New peer: " + peer.toString());
        		} catch (SocketTimeoutException e) {
        			//e.printStackTrace();
        		}
            

        }
    }

    public void connect(Socket socket){
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            //System.out.println("Sending Message");
            Peer.send("ping", outputStream);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Peer2Peer node1 = new Peer2Peer(8888);
        Peer2Peer node2 = new Peer2Peer(8888);

        node2.start();
        Socket socket = new Socket("127.0.0.1", 8888);
        node1.connect(socket);
        //node1.stop();
        //node2.stop();


    }

}