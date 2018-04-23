package com.XCoin.Core.cli.commands;

import org.dilithium.cli.Commander;

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
	        Commander.CommanderPrint("\n------------------------------------------------------------------------\n"+
	                "-  DILITHIUM HELP\n" +
	                "------------------------------------------------------------------------");
	        for( String key : Commander.getInstance().cmds.keySet() ){
	            Commander.CommanderPrint(Commander.getInstance().cmds.get(key).getHelp());
	        }
	        
	    }
}
