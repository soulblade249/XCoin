import java.security.Key;

public class Transaction {

	private int amount;
	public Key sender;
	public Key reciever;
	public long timeStamp;
	
	public Transaction (int a, Key r, Key s, long t) {
		this.amount = a;
		this.timeStamp = t;
		this.reciever = r;
		this.sender = s;
	}
}
