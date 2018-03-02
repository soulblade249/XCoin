import java.io.IOException;
import java.security.Security;

public class Test {
	public static void main(String [] args) throws IOException {
		/**
		//Tester Class
		StringUtil su = new StringUtil();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

		//Create wallets:
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();		
		Wallet coinbase = new Wallet();
		
		System.out.println();
		System.out.println(su.getStringFromKey(walletA.privateKey));
		//System.out.println(su.getStringFromKey(walletA.publicKey));

		
		//Encrypt String Password 
		String pwd = "Password";
		System.out.println(su.applyECDSASig(walletA.privateKey, pwd));
		System.out.println(su.verifyECDSASig(walletA.publicKey, pwd, su.applyECDSASig(walletA.privateKey, pwd)));
		
		//isChainValid();
		 
		 */
		
		Peer2Peer p2p = new Peer2Peer(8888);
		p2p.listen();
		
		Peer peer = new Peer("0.0.0.0", 8888);
		System.out.println(peer.toString());
	}
}
