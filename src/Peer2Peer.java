import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Peer2Peer {
	private int port;
	Peer peer; 
	public Socket connection;
	public DataInputStream inputStream;
	public DataOutputStream outputStream;
	public Thread peerThread;
	public Thread sendThread;
	
	
}
