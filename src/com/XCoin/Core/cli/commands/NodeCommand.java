/*
package com.XCoin.Core.cli.commands;

import java.util.Arrays;
import org.dilithium.Start;
import org.dilithium.cli.Commander;
import org.dilithium.core.Block;
import org.dilithium.db.Context;
import org.dilithium.serialization.Json;
import org.dilithium.util.Encoding;

*//**
 * This class 
 *//*
public class NodeCommand implements Command {

	@Override
	public String getHelp() {
		return "cmd: node \n" +
				"- description: Command the local full-node \n" +
				"- usage: node param [situational...] \n"+
				"- param: 'start' , 'show', 'show-block' [index], 'stop' '-help', '-params' \n"+
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[]{"-help", "-params", "start", "show", "show-block","stop" };
	}

	@Override
	public void run(String[] args) {
		if( !Arrays.asList(getParams()).contains(args[0]) ){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}

		String[] params = getParams();

		if(args[0].equals(params[2])){ //start
			System.out.println("- " + "Starting node: ");
			//Start.localNode.start();
			return;
		}

		if(args[0].equals(params[3])){ //show
			//System.out.println("- " + Start.localNode.toString());
			return;
		}

		if(args[0].equals(params[4])){ //show-block
			String indexString = args[1] ;
			if(indexString == null){
				System.out.println("- " + "ERROR ! no index included");
				return;
			}

			System.out.println("- " + "Fetching block...");
			System.out.println("- " + "Searching for block with index: " + indexString);          
			Context context = Start.localNode.getContext();
			long index = Long.parseLong(indexString);
			Block block = context.getBlock(index);

			if(block == null) {
				Commander.CommanderPrint("ERROR ! block not found.");
				return;
			}

			if(args[2] != null){
				if(args[2].equals("-json")){
					Commander.CommanderPrint("found block: " + Json.createJsonPretty(block));            
					return;
				}
				if(args[2].equals("-json-raw")){
					Commander.CommanderPrint("found block: " + Json.createJson(block));            
					return;
				}

				Commander.CommanderPrint("ERROR 2 argument unknown !");
				return;
			}

			Commander.CommanderPrint("found block: " + block.toString());            
			return;
		}

		if(args[0].equals(params[5])){ //stop
			Commander.CommanderPrint("Stopping node: ");
			Start.localNode.stop();
			return;
		}



		if(args[0].equals(params[0])){ //help
			Commander.CommanderPrint(getHelp());
			return;
		}

		if(args[0].equals(params[1])){ //params
			Commander.CommanderPrint(Arrays.toString(getParams()));
			return;
		}

		Commander.CommanderPrint("Sorry param not yet implemented");

	}

}
*/