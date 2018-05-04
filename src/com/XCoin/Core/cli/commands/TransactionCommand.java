package com.XCoin.Core.cli.commands;

public class TransactionCommand implements Command{

	@Override
	public String getHelp() {
		return  "cmd: transaction \n" +
				"- description: A tool for creating transactions \n" +
				"- usage: transaction param [situational...] \n"+
				"- param: 'create' [-senderKey] [-receiverKey] [-tbd], 'pem' location [-private], 'info', '-help', '-params' \n"+
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
