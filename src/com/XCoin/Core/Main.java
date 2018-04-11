package com.XCoin.Core;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.XCoin.Networking.Peer2Peer;

public class Main {
	
	private static Thread mining;
	private static final int DEFAULT_PORT = 8888;
	public static void main(String [] args) throws IOException {
				
		help();
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
		Wallet A = new Wallet();
		Wallet B = new Wallet();
		
		System.out.println(A.publicKey.toString());
		while(running) {
			command = pInput.next();
			switch(command) {
				case "help" : 
					help();
					break;
				case "mine" :
					System.out.println("Entered mining");
					command = pInput.next();
					if(command.equals("-s")) {
						System.out.println("minign");
						mining = new Thread(new Runnable() {
				            public void run() {
				                try {
				                    bc.mine();				                    
				                } catch (Exception e) {
				                    e.printStackTrace();
				                }
				            }
				        });		
						mining.start();
						break;
					} else if(command.equals("-c")) {
						bc.bMining = false;
						break;
					} else {
						break;
					}
				case "tx":
					command = pInput.next();
					if(command.equals("-c")) {
						//bc.addTransaction(new Transaction(pInput.nextInt(), pInput.next(), A.publicKey, date.getTime()));
					} else {
						break;
					}
					break;
				default :
					System.out.println("Enter valid command >");
					break;
			}
		}
	}
	
	
	public static void help() {
		System.out.println("====================");
		System.out.println("      Commands");
		System.out.println("====================");
		System.out.println("\nhelp -Prints the command menu");
		System.out.println("mine -Launches block miner");
		System.out.println("    -s Starts the mining program");
		System.out.println("    -c Closes the mining program");
		System.out.println("tx -Transaction creator");
		System.out.println("    -c (amount) (address) Creates transaction"); 
		System.out.print("\n");
		System.out.print(">");

	}
	
}
