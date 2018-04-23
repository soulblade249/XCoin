package com.XCoin.Core.cli;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.Wallet;
import com.XCoin.GUI.MinerGui;
import com.XCoin.Networking.Peer2Peer;
import com.XCoin.Util.KeyUtil;

public class Main {

	private static Thread mining;
	private static final int DEFAULT_PORT = 8888;
	public static void main(String [] args) throws IOException {

		boolean running = true;
		String command;
		BlockChain bc = new BlockChain();
		Scanner pInput = new Scanner(System.in);
		Peer2Peer p2p = new Peer2Peer(DEFAULT_PORT, bc);
		Date date = new Date();
		//////////////////
		//
		// Test Wallets
		//
		/////////////////
		Wallet A = new Wallet();
		System.out.println(KeyUtil.publicKeyToString(A.publicKey));
		System.out.println("0x"+A.address);
		Commander cmd = new Commander();
	}
}
