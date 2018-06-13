package com.XCoin.Networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.XCoin.Networking.Commands.CommandHandler;
import com.XCoin.Networking.Commands.PingCommandHandler;
import com.XCoin.Networking.Commands.TransactionCommandHandler;
import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.ByteUtil;

public class Peer2Peer {

	private int port;
    private static ArrayList<Peer>  peers;
    private DataOutputStream out;
    private Thread           serverThread;
    private boolean          runningServer;
    public 	static HashMap<ByteArrayKey, CommandHandler> commands = new HashMap<>();
    private ServerSocket server;
    private Socket socket;
    
    //Node with out storing Blockchain
    public Peer2Peer(int port){
	    this.port = port;
	    peers = new ArrayList<Peer>();
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
    		/**List of Commands
    		 * 0xFF - Ping
    		 * 0x00 - Transaction
    		 */
        this.commands.put(new ByteArrayKey((byte)0xFF), new PingCommandHandler()); 
        this.commands.put(new ByteArrayKey((byte)0x00), new TransactionCommandHandler());
    }
    
    public HashMap<ByteArrayKey, CommandHandler> getCommands() {
    		return commands;
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
    		runningServer = false;
    		try {
        		serverThread.interrupt();
    			socket.close();
    			for(Peer p : peers) {
            		p.stop();
            }
        } catch (NullPointerException n) {
        		n.printStackTrace();
        }
        System.out.println("Server Stopped");
    }

    public void listen() throws IOException, SocketTimeoutException{
        server = new ServerSocket(this.port);
        Peer peer;
        server.setSoTimeout(10000);
        while(runningServer){
        		//System.out.println("Waiting for a connection");
        		try{
        			socket = server.accept();
                System.out.println("Passed Accept");
                peer = new Peer(socket, this);
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
            out = new DataOutputStream(socket.getOutputStream());
            Peer peer = new Peer(socket, this);
            peer.send(commands.get(new ByteArrayKey((byte) 0xFF)).handle(new ByteArrayKey((byte) 0xFF, (byte) 0x00)), out);	
            peers.add(peer);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
    		Peer2Peer node1 = new Peer2Peer(8888);
    		node1.start();
		node1.connect(new Socket("10.70.21.135", 8888));
		//node1.peers.add(new Peer(new Socket("10.70.21.149", 8888)));
    }
    
    public static void propagate(ByteArrayKey data) {
    		for(Peer p: peers) {
    			System.out.println(p.socket.toString());
    			p.send(data.toByteArray(), p.out);
    		}
    }
}