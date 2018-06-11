package com.XCoin.Core.cli;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.Wallet;
import com.XCoin.GUI.MinerGui;
import com.XCoin.Networking.Peer2Peer;
import com.XCoin.Util.KeyUtil;

public class Main {
	
	public static ArrayList<Wallet> wallets = new ArrayList<Wallet>();
	public static Wallet testWallet = new Wallet();
	
	public static void main(String [] args) throws IOException, GeneralSecurityException {
		testWallet.addFunds("USD", 500);
		testWallet.addFunds("JYP", 500);
		Commander cmd = new Commander();
		cmd.menu();
		BlockChain.propagateWallet();
		System.out.println("Propagated Wallets");
		BlockChain.processTransactions();
	}
}
