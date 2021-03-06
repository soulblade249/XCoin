
package com.XCoin.Core.cli.commands;

import java.util.Map;

import com.XCoin.Core.cli.Commander;

/**
 * This class 
 */
public class HelpCommand implements Command{

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
        for( String key : Commander.getInstance().cmds.keySet() ){
        		System.out.println(Commander.getInstance().cmds.get(key).getHelp());
        }
    }

}
