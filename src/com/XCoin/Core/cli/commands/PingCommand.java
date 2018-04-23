package com.XCoin.Core.cli.commands;

import com.XCoin.Core.cli.Commander;

public class PingCommand implements Command {

	@Override
	public String getHelp() {
		return  "cmd: -help \n" +
				"- description: Displays help for all known commands. \n" + 
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void run(String[] args) {
		System.out.println("\n------------------------------------------------------------------------\n"+
				"-  XCoin HELP\n" +
				"------------------------------------------------------------------------");
		for( String key : Commander.getInstance().cmds.keySet() ){
			System.out.println("- " + Commander.getInstance().cmds.get(key).getHelp());
		}

	}
}
