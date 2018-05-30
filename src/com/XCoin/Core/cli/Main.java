package com.XCoin.Core.cli;
import java.io.IOException;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.Wallet;
import java.util.HashMap;

public class Main {

        public static HashMap<Wallet, byte[]> wallets = new HashMap<Wallet, byte[]>();
    
	public static void main(String [] args) throws IOException {
		Commander cmd = new Commander();
		cmd.menu();
		BlockChain.processTransactions();
	}
}
