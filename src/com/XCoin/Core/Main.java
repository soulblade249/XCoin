package com.XCoin.Core;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.XCoin.GUI.MinerGui;
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
		System.out.println(StringUtil.publicKeyToString(A.publicKey));
		System.out.println(StringUtil.privateKeyToString(A.privateKey));

		
		//////////////////
		//
		// CLI Menu
		//
		while(running) {
			command = pInput.next();
			if(command.equals("help")) {
				help();
			}else if(command.equals("mine")) {
				command = pInput.next();
				if(command.equals("-s")) {
					MinerGui gui = new MinerGui();
					mining = new Thread(new Runnable() {
						public void run() {
							try {
								bc.mine(gui);				                    
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});		
					gui.mining = mining;
				}else {
					System.out.println("Error: Invalid argument");
					System.out.print(">");
				}
			}else if(command.equals("tx")) {
				command = pInput.next();
				if(command.equals("-c")) {
					Scanner amount = new Scanner(System.in);
					Scanner adress = new Scanner(System.in);
					//bc.addTransaction(new Transaction(pInput.nextInt(), pInput.next(), A.publicKey, date.getTime()));
				}else {
					System.out.println("Error: Invalid argument" + ">");
				}
			}else if(command.equals("quit")) {
				System.exit(0);
			}else {
				System.out.println("Please enter a valid command.");
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
		System.out.println("quit -Quits the program");
		System.out.print("\n");
		System.out.print(">");
	}

}
