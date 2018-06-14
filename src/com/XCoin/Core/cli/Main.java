package com.XCoin.Core.cli;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.Wallet;
import com.XCoin.Networking.Peer2Peer;

public class Main {
	
	public static ArrayList<Wallet> wallets = new ArrayList<Wallet>();
	public static Wallet testWallet = setUpTestWallet();
	public static Peer2Peer node;
	
	public static void main(String [] args) throws IOException, GeneralSecurityException {
		BlockChain.propagateWallet();
		Commander cmd = new Commander();
		node = new Peer2Peer(8888);
		node.start();
		//node.connect(new Socket("10.70.21.135", 8888));
		//System.out.println("Test Wallet Bal: " + testWallet.getBal());
		cmd.menu();
		BlockChain.processTransactions();
	}
	
	public static Wallet setUpTestWallet() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		Wallet test = new Wallet();
		test.addFunds("USD", 500);
		test.addFunds("JPY", 500);
		return test;
	}
}
