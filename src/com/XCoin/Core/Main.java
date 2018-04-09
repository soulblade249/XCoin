package com.XCoin.Core;
import java.io.IOException;
import java.security.Security;
import java.util.Scanner;

import com.XCoin.Networking.Peer;
import com.XCoin.Networking.Peer2Peer;

public class Main {
	
	private static Thread mining;
	private static final int DEFAULT_PORT = 8888;
	public static void main(String [] args) throws IOException {
				
		help();
		boolean running = true;
		String command;
		BlockChain bc = new BlockChain();
		Scanner input = new Scanner(System.in);
		Peer2Peer p2p = new Peer2Peer(DEFAULT_PORT, bc);
		while(running) {
			command = input.next();
			switch(command) {
				case "help" : 
					help();
				case "mine" :
					System.out.println("Entered mining");
					if(command.equals("-s")) {
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
					} else if(command.equals("-c")) {
						mining.interrupt();
						System.out.println("Stopped mining");
					}
				default :
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
		System.out.println("\n");
	}
	
}
