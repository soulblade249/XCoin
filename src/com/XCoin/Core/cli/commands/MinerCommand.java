package com.XCoin.Core.cli.commands;

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
		return new String[] {"-cli, , -help, -params, info"};
	}

	@Override
	public void run(String[] args) {
		if( !Arrays.asList(getParams()).contains(args[0])){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}
		
		if(args[0].equals("start")) {
			if(args.length > 1) {
				System.out.println("Starting miner with GUI");
				MinerGui gui = new MinerGui();
			}else {
				System.out.println("Starting miner without GUI");
				System.out.println("- " + "Sorry param not yet implemented");
			}
		}else if(args[0].equals("-help")){
			System.out.println("- " + getHelp());
		}else {
			System.out.println("- " + "Sorry param not yet implemented");
		}
	}

}
