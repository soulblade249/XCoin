package com.XCoin.Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;


import com.XCoin.Networking.Commands.Command;
import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.*;

public class Peer {
	
    private Thread peerThread;  
    public Socket socket;
    private static HashMap<String, Command> commands = new HashMap<>();
    public DataOutputStream out;
    public DataInputStream in;
    
	public Peer(Socket socket)  {
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
		peerThread.start();
	}

	private void initializeCommands() {
		
    }
	
	public void listen() throws IOException {
		byte[] command;
		DataInputStream in = new DataInputStream(socket.getInputStream());
		while(true){
	    		try{
        			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        			command = receive(in);
        			send(serve(command), out);
	    		} catch (SocketTimeoutException e) {
	    			e.printStackTrace();
	    		}
        

		}
	}
	
	public static byte[] serve(byte[] input) {
		//TODO In execute, send the args as input without the first byte
        return commands.get(new ByteArrayKey(input[0])).handle(new ByteArrayKey(input));
    }

    public static void send(byte[] data, DataOutputStream out){
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
            System.out.println("Received message: "+ ByteUtil.bytesToBigInteger(new ByteArrayKey(data).subSet(2, data.length-1)));
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
