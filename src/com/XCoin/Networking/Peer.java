package com.XCoin.Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.XCoin.Networking.Commands.Command;
import com.XCoin.Networking.Commands.PingCommandHandler;


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
        this.commands.put("ping", new PingCommandHandler());
       
    }
	
	public void listen() throws IOException {
		String command;
		while(true){
	    		try{
	    			DataInputStream in = new DataInputStream(this.socket.getInputStream());
        			DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
        			command = receive(in);
        			send(serve(command), out);
	    		} catch (SocketTimeoutException e) {
	    			e.printStackTrace();
	    		}
        

		}
	}
	
	public static byte[] serve(String input) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find()) {
            list.add(m.group(1));
        }

        String command = list.remove(0); // Get the command and remove it from the list.

        if(!commands.containsKey(command)){
        		System.out.println("That is not a command!");
        		return null;
        }

        String[] args = null;
        if (list.size() > 0){
            args = list.toArray(new String[list.size()]);
        }

        return commands.get(command).execute(args);
    }

    public static void send(byte[] data, DataOutputStream out){
        System.out.println("Sending message: " + data.toString());
        try {
            out.writeUTF(data.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive(DataInputStream in){
        String data = null;
        try {
            data = in.readUTF();
            System.out.println("Received message: "+data);
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
