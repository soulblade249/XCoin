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
                    System.out.println("Thread Ending");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });		
        
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
        		
        String command;
        Peer peer;
        server.setSoTimeout(5000);
        while(runningServer){
        		System.out.println("Waiting for a connection");
        		try{
        			socket = server.accept();
                System.out.println("Passed Accept");
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String clientAddress = socket.getInetAddress().getHostAddress();
                int clientPort = socket.getPort();
                System.out.println("Connection received from: " + clientAddress + ":" + clientPort);
                peer = new Peer(socket.getInetAddress().getHostAddress(), clientPort);
                peers.add(peer);

                System.out.println("New peer: " + peer.toString());
                command = receive(in);
                send(serve(command), out);
                System.out.println("Done waiting");
                
                System.out.println("New peer: " + peer.toString());
                command = receive(in);
                send(serve(command), out);
                System.out.println("Done waiting");
        		} catch (SocketTimeoutException e) {
        			e.printStackTrace();
        		}
            

        }
    }

    public void connect(String host, int port){
        try {
            Socket socket = new Socket(host, port);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private String serve(String input) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find()) {
            list.add(m.group(1));
        }

        String command = list.remove(0); // Get the command and remove it from the list.

        if(!commands.containsKey(command)){
            return "'" + command + "' is not a command.";
        }

        String[] args = null;
        if (list.size() > 0){
            args = list.toArray(new String[list.size()]);
        }

        return commands.get(command).execute(args);
    }

    public void send(String data, DataOutputStream out){
        System.out.println("Sending message: " + data);
        try {
            out.writeUTF(data);
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

    public static void main(String[] args) throws IOException {
        Peer2Peer node1 = new Peer2Peer(8888);
        node1.connect("10.70.21.139", 8888);
        node1.send("ping", node1.outputStream);

    }

}