
package com.XCoin.Core.cli;

import com.XCoin.Core.Block;
import com.XCoin.Core.BlockChain;
import com.XCoin.Core.Wallet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.XCoin.Core.cli.commands.Command;
import com.XCoin.Core.cli.commands.HelpCommand;
import com.XCoin.Core.cli.commands.KeyUtilCommand;
import com.XCoin.Core.cli.commands.MinerCommand;
//import com.XCoin.Core.cli.commands.NodeCommand;
import com.XCoin.Core.cli.commands.PingCommand;
import com.XCoin.Core.cli.commands.WalletCommand;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * This class 
 */
public class Commander {

	public HashMap<String,Command> cmds;
	public Scanner scanner;
	public static Commander instance;
	public HelpCommand help = new HelpCommand();
	public PingCommand ping = new PingCommand();
	public KeyUtilCommand keyUtil = new KeyUtilCommand();
	public MinerCommand miner = new MinerCommand();
        public WalletCommand wallet = new WalletCommand();
	public static boolean invalidArg = false;

	/* we get the command object from cmds and call command.run(args)*/
	public void call(String[] rawArgs){
		try{
			String function = rawArgs[0];
			String[] args = Arrays.copyOfRange(rawArgs, 1,rawArgs.length);

			Command command = cmds.get(function);
			if(command == null){        
				System.out.println("- " + "command function: '" + function +"' not found. Type -help for a list of functions");
			}else{
				command.run(args);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("- " + "command couldn't execute, perhaps not enough arguments? try: "+ rawArgs[0] + " -help");
			invalidArg = true;
		}catch(Exception e){
			System.out.println("- " + "command failed to execute.");
		}

	}

	public void setup(){
		cmds = new HashMap<String,Command>();
		cmds.put("key-util", new KeyUtilCommand());
		//cmds.put("node", new NodeCommand());
		cmds.put("ping", new PingCommand());
		cmds.put("-help", new HelpCommand());
		cmds.put("miner", new MinerCommand());
                cmds.put("wallet", new WalletCommand());
		scanner = new Scanner(System.in);
	}

	public static Commander getInstance(){
		if(instance != null) {
			instance = new Commander();
		}

		return instance;
	}

	public void menu() throws FileNotFoundException{
		while(true) {
			if(!invalidArg) {
				System.out.println("------------------------------------------------------------------------");
				System.out.println("			XCoin C.L.I Menu       						  ");
				System.out.println("------------------------------------------------------------------------");
				System.out.println("			  Commands       					  		  ");
				System.out.println(help.getHelp());
				//System.out.println(node.getHelp());
				System.out.println(ping.getHelp());
				System.out.println(keyUtil.getHelp());
				System.out.println(miner.getHelp());
                                System.out.println(wallet.getHelp());
				System.out.println("cmd: quit");
			}
			System.out.print("XCoin-cli: ");
			String input = (String) scanner.nextLine(); //Casted as string just in case
			if(input.equals("quit")) {
                                onTerminate();
				break;
			}
			String[] argumentArray = input.split("\\s+");

			if(!argumentArray[0].equals("")) { //We check to see if its a blank argument
				call(argumentArray);
				//break;
			}
		}
	}

	public Commander(){
		setup();
		instance = this;
	}
        
        public void onTerminate() throws FileNotFoundException {
            PrintWriter out = new PrintWriter(new File("wallets.txt"));
            for(Map.Entry<Wallet, byte[]> w : Main.wallets.entrySet()) {
                out.println(w.getKey().toString() + " " + w.getValue());
            }
            out.close();
        }
}
