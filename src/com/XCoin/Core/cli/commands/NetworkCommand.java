package com.XCoin.Core.cli.commands;

import java.io.IOException;
import java.util.Arrays;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.cli.Commander;
import com.XCoin.GUI.MinerGui;
import com.XCoin.Networking.Peer2Peer;

public class NetworkCommand implements Command {

	private Peer2Peer p2p = new Peer2Peer(8888);
	@Override
	public String getHelp() {
		return  "cmd: net \n" +
				"- description: Control your node to connect and communicate to other nodes \n" + 
				"- usage: net param [situational...]\n" +
				"- param: 'start', 'stop'\n" + 
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[]{ "-help", "start", "stop", "pem", "info", "-get"};
	}

	@Override
	public void run(String[] args) {
		if( !Arrays.asList(getParams()).contains(args[0])){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}
		if(args[0].equals("start")) {
			p2p.start();
		}else if(args[0].equals("help")){
			System.out.println("- " + getHelp());
			Commander.repeat = false;
		} else if (args[0].equals("stop")){
			try {
				p2p.stop();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}else {
			System.out.println("- " + "Sorry param not yet implemented");
			Commander.repeat = false;
		}
	}
		//Commander.invalidArg = true;
}

