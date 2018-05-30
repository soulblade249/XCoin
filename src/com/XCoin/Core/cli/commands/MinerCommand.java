package com.XCoin.Core.cli.commands;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.cli.Commander;

import java.util.Arrays;
import com.XCoin.GUI.MinerGui;

public class MinerCommand implements Command{

	@Override
	public String getHelp() {
		return  "cmd: miner \n" +
				"- description: The XCoin mining tool. \n" + 
				"- usage: miner param[situational...] \n" +
				"- param: 'start' [-gui], 'info', '-help', '-params' \n" + 
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[] {"-gui","help","-params","info", "start"};
	}

	@Override
	public void run(String[] args) {
		if( !Arrays.asList(getParams()).contains(args[0])){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}
		if(args[0].equals("start")) {
			BlockChain bc = new BlockChain();
			bc.mine(new MinerGui());
			Commander.repeat = false;
		}else if(args[0].equals("help")){
			System.out.println("- " + getHelp());
			Commander.repeat = false;
		}else {
			System.out.println("- " + "Sorry param not yet implemented");
			Commander.repeat = false;
		}
	}

}
