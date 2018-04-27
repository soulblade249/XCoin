package com.XCoin.Core.cli.commands;

import java.util.Arrays;

import com.XCoin.Core.cli.Commander;

public class PingCommand implements Command {

	@Override
	public String getHelp() {
		return  "cmd: ping \n" +
				"- description: Displays help for all known commands. \n" + 
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[] {"-adress", "-help"};
	}

	@Override
	public void run(String[] args) {
		
		if(args.length == 0) {
			//TODO ping command
		} else {
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}
	}
}
