import java.security.Key;

public class Transaction {

	private int amount;
	public Key sender;
	public Key reciever;
	public long timeStamp;
	public String id;
	private Wallet wallet;
	
	public Transaction (int a, Key r, Key s, long t, String i, Wallet w) {
		this.amount = a;
		this.timeStamp = t;
		this.reciever = r;
		this.sender = s;
		this.id = i;
		this.wallet = w;
	}
	
	public boolean isTransactionValid() {
		return wallet.getBalance() >= amount ? true : false; 
	}
}
