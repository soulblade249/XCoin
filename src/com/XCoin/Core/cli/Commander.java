
package com.XCoin.Core.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.XCoin.Core.cli.commands.Command;
import com.XCoin.Core.cli.commands.HelpCommand;
import com.XCoin.Core.cli.commands.KeyUtilCommand;
//import com.XCoin.Core.cli.commands.NodeCommand;
import com.XCoin.Core.cli.commands.PingCommand;


/**
 * This class 
 */
public class Commander {

	public HashMap<String,Command> cmds;
	public Scanner scanner;
	public static Commander instance;

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
		}catch(Exception e){
			System.out.println("- " + "command failed to execute.");
		}

	}

	public void Menu(){
		cmds = new HashMap<String,Command>();
		cmds.put("key-util", new KeyUtilCommand());
		//cmds.put("node", new NodeCommand());
		cmds.put("ping", new PingCommand());
		cmds.put("-help", new HelpCommand());
		scanner = new Scanner(System.in);

		System.out.print("XCoin-cli: ");
		String input = (String) scanner.nextLine(); //Casted as string just in case
		new HelpCommand().getHelp();
		while(true) {
			if(input.equals("quit")) {
				break;
			}
			String[] argumentArray = input.split("\\s+");
			
			if(!argumentArray[0].equals("")) { //We check to see if its a blank argument
				call(argumentArray);
			}
		}
	}

	public static Commander getInstance(){
		if(instance != null) {
			instance = new Commander();
		}

		return instance;
	}

	public Commander(){
		instance = this;
		Menu();
	}
}
