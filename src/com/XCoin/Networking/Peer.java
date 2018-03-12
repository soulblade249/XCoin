package com.XCoin.Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Peer {
	public final int    port;
	public final String address;
    private Thread      peerThread;
    private ServerSocket server;
    private Socket socket = null;
	
    Peer2Peer p2p = new Peer2Peer(8888);
    
	public Peer(String address, int port)  {
		this.address = address;
		this.port = port;
		//System.out.println("TEst");
		peerThread = new Thread(new Runnable() {
			public void run() {
                try {
                    listen();
                    System.out.println("Thread Ending");
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });	
		peerThread.start();
	}

	public void listen() throws IOException {
        server = new ServerSocket(this.port);
		System.out.println("TEst");
		while(true){
	    		System.out.println("Listening for commands");
	    		try{
        			socket = server.accept();
        			DataInputStream in = new DataInputStream(socket.getInputStream());
        			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        			

	    		} catch (SocketTimeoutException e) {
	    			e.printStackTrace();
	    		}
        

		}
	}

    @Override
    public String toString() {
        return String.format("[%s:%s]", address, port);
    }
}
