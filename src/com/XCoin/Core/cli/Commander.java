
package com.XCoin.Core.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.XCoin.Core.cli.commands.Command;
import com.XCoin.Core.cli.commands.HelpCommand;
import com.XCoin.Core.cli.commands.KeyUtilCommand;
import com.XCoin.Core.cli.commands.NodeCommand;
import com.XCoin.Core.cli.commands.PingCommand;
import com.XCoin.Core.cli.commands.WalletCommand;


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
                CommanderPrint("command function: '" + function +"' not found. Type -help for a list of functions");
            }else{
                command.run(args);
            }
        }catch(ArrayIndexOutOfBoundsException e){
            CommanderPrint("command couldn't execute, perhaps not enough arguments? try: "+ rawArgs[0] + " -help");
        }catch(Exception e){
            CommanderPrint("command failed to execute.");
        }
             
    }
    
    /* populates the command arraylist with commands. */
    public void setup(){
        cmds = new HashMap<String,Command>();
        cmds.put("key-util", new KeyUtilCommand());
        cmds.put("wallet", new WalletCommand());
        cmds.put("node", new NodeCommand());
        cmds.put("ping", new PingCommand());
        cmds.put("-help", new HelpCommand());
        scanner = new Scanner(System.in);
    }
    
    /* uses a scanner to get input from the user, we check if cmd was quit first then call(args). */
    public void listen(){
        while(true){
            CommanderInput("dilithium-cli");
            String rawInput = (String) scanner.nextLine();
            
            if(rawInput.equals("quit") || rawInput.equals("exit")){
                break;
            }
            String[] rawArgs = rawInput.split("\\s+");

            if(!rawArgs[0].equals("")){
                call(rawArgs);
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
        setup();
        instance = this;
    }
    
    /* Standard commander print method. */
    public static void CommanderPrint(String msg){
        System.out.println("- " + msg);
    }
    /* Commander print method for inputs. */
    public static String CommanderInput(String msg){
        System.out.print("" + msg + ": ");
        return null;
    }
    
}
