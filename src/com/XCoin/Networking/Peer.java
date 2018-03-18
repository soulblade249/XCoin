package com.XCoin.Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Peer {
	
    private Thread peerThread;  
    private Socket socket;
    
	public Peer(Socket socket)  {
		this.socket = socket;
		//System.out.println("TEst");
		peerThread = new Thread(new Runnable() {
			public void run() {
                try {
                    listen();
                    System.out.println("Closing connection to " + socket.getInetAddress() + ":" + socket.getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });	
		peerThread.start();
	}

	public void listen() throws IOException {
		while(true){
	    		//System.out.println("Listening for commands");
	    		try{
	    			DataInputStream in = new DataInputStream(this.socket.getInputStream());
        			DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
        			

	    		} catch (SocketTimeoutException e) {
	    			e.printStackTrace();
	    		}
        

		}
	}

    @Override
    public String toString() {
        return String.format("[%s:%s]", socket.getInetAddress(), socket.getPort());
    }
}
