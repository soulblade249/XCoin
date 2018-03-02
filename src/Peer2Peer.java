import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Peer2Peer {
	private int port;
	public ArrayList<Peer> peer = new ArrayList(); ; 
	public DataInputStream inputStream;
	public DataOutputStream outputStream;
	public Thread clientThread;
	public Thread serverThread;
	public boolean runningServer = false;
	
	public Peer2Peer(int port) {
		this.port = port;
		peer = new ArrayList();
		serverThread = new Thread(new Runnable() {
			public void run() {
                try {
                    listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		});
	}	
	
	public void listen() throws IOException {
		System.out.println("Server Starting...");
		ServerSocket server = new ServerSocket(this.port);
        System.out.println("Server started on port " + this.port);

	}
	
	public void start(){
        if(serverThread.isAlive()){
            System.out.println("Server is already running.");
            return;
        }
        runningServer = true;
        serverThread.start();
    }
	 public void stop() {
		 runningServer = false;
	 }
}
