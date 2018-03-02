
public class Peer {
	public final int port;
	public final String address;
	
	public Peer(String address, int port) {
		this.address = address;
		this.port = port;
	}


    @Override
    public String toString() {
        return String.format("[%s:%s]", address, port);
    }
}
