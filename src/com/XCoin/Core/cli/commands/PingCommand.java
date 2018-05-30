package com.XCoin.Core.cli.commands;

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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void run(String[] args) {
		System.out.println("Not Implemented Yet");
		Commander.invalidArg = true;
		Commander.repeat = false;
	}
		//Commander.invalidArg = true;
}

