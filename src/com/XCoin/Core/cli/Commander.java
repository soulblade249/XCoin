
package com.XCoin.Core.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.XCoin.Core.cli.commands.Command;
import com.XCoin.Core.cli.commands.HelpCommand;
import com.XCoin.Core.cli.commands.KeyUtilCommand;
import com.XCoin.Core.cli.commands.MinerCommand;
//import com.XCoin.Core.cli.commands.NodeCommand;
import com.XCoin.Core.cli.commands.PingCommand;


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
	public static boolean firstBoot = false;

	/* we get the command object from cmds and call command.run(args)*/
	public void call(String[] rawArgs){
		try{
                    //System.out.println("Test");
			String function = rawArgs[0];
                         //System.out.println("Test2");
			String[] args = Arrays.copyOfRange(rawArgs, 1,rawArgs.length);
                         //System.out.println("Test3");
                        //for(String out : args) {
                        //    System.out.println(out);
                        //}

			Command command = cmds.get(function);
                        //System.out.println("Test4");
			if(command == null){        
				System.out.println("- " + "command function: '" + function +"' not found. Type help for a list of functions");
			}else{
                            //System.out.println("Test5");
				command.run(args);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("- " + "command couldn't execute, perhaps not enough arguments? try: "+ rawArgs[0] + " -help");
			//invalidArg = true;
		}catch(Exception e){
                        e.printStackTrace();
			System.out.println("- " + "command failed to execute.");
		}

	}

	public void setup(){
		cmds = new HashMap<String,Command>();
		cmds.put("key-util", new KeyUtilCommand());
		//cmds.put("node", new NodeCommand());
		cmds.put("ping", new PingCommand());
		cmds.put("help", new HelpCommand());
		cmds.put("miner", new MinerCommand());
		scanner = new Scanner(System.in);
	}

	public static Commander getInstance(){
		if(instance != null) {
			instance = new Commander();
		}

		return instance;
	}

	public void menu() {
		while(true) {
			if(!firstBoot) {
				System.out.println("------------------------------------------------------------------------");
				System.out.println("			XCoin C.L.I Menu       		");
				System.out.println("------------------------------------------------------------------------");
				System.out.println("			  Commands       		");
				System.out.println(help.getHelp());
				//System.out.println(node.getHelp());
				System.out.println(ping.getHelp());
				System.out.println(keyUtil.getHelp());
				System.out.println(miner.getHelp());
				System.out.println("cmd: quit");
                                firstBoot = true;
			}
			System.out.print("XCoin-cli: ");
			String input = (String) scanner.nextLine(); //Casted as string just in case
			if(input.equals("quit")) {
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
}
