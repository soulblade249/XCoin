package com.XCoin.Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;


import com.XCoin.Networking.Commands.CommandHandler;
import com.XCoin.Networking.Commands.PingCommandHandler;
import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.*;

public class Peer {
	
	private Peer2Peer p2p;
    private Thread peerThread;  
    public Socket socket;
    private static HashMap<ByteArrayKey, CommandHandler> commands = new HashMap<>();
    public DataOutputStream out;
    public DataInputStream in;
    private boolean runningServer;
    
	public Peer(Socket socket, Peer2Peer p2p)  {
		this.p2p = p2p;
		this.socket = socket;
        initializeCommands();
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
		start();
	}

	public void start(){
        if(peerThread.isAlive()){
            System.out.println("Peer Thread is already running.");
            return;
        }
        runningServer = true;
        peerThread.start();
    }
	
	public void stop() throws IOException{
		runningServer = false;
		try {
			peerThread.interrupt();
			socket.close();
	    } catch (NullPointerException n) {
	    		n.printStackTrace();
	    }
	    System.out.println("Peer Closed");
	}

	private void initializeCommands() {
		this.commands = p2p.getCommands(); 
    }
	
	public void listen() throws IOException {
		byte[] command;
		DataInputStream in = new DataInputStream(socket.getInputStream());
		while(runningServer){
	    		try{
        			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        			command = receive(in);
        			send(serve(command), out);
	    		} catch (SocketTimeoutException e) {
	    			e.printStackTrace();
	    		}
        

		}
	}
	
	public byte[] serve(byte[] input) {
		//TODO In execute, send the args as input without the first byte
        return commands.get(new ByteArrayKey(input[0])).handle(new ByteArrayKey(input));
    }

    public void send(byte[] data, DataOutputStream out){
        System.out.println("Sending message: " + data);
        try {
        		System.out.println(data.length);
        		out.writeInt(data.length);
            out.write(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receive(DataInputStream in){
		byte[] data = null;
		int size = 0;
		try {
        		size = in.readInt();
        		data = new byte[size];
        		in.readFully(data, 0, size);
            System.out.println("Received message: "+ ByteUtil.bytesToBigInteger(new ByteArrayKey(data).subSet(2, data.length-1)) + " " + size);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return data;
    }

    @Override
    public String toString() {
        return String.format("[%s:%s]", socket.getInetAddress(), socket.getPort());
    }
}
