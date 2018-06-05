package com.XCoin.Core.cli.commands;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Arrays;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.Transaction;
import com.XCoin.Core.Wallet;
import com.XCoin.Core.cli.Commander;
import com.XCoin.Core.cli.Main;
import com.XCoin.Util.ByteUtil;
import com.XCoin.Util.KeyUtil;

public class TransactionCommand implements Command{

	private byte[] currencies;
	private String[] currency;
	private String[] amounts;
	private byte[] data;

	@Override
	public String getHelp() {
		return  "cmd: transaction \n" +
				"- description: A tool for creating transactions, if more info is needed about parameter, please type 'transaction help paramName' \n" +
				"- usage: transaction param [situational...] \n"+
				"- param: 'create' [-private] [-receiver] [-amount] [-currency], 'accept' [-private], 'help' [-param] \n"+
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[] {"create", "-private", "-receiver", "-amount", "-currency", "accept", "privateKey", "help", "-param"};
	}

	@Override
	public void run(String[] args) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

		if(!Arrays.asList(getParams()).contains(args[0]) ){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}
		int currencyIndex = 0;
		boolean stop = false;
		if(args[0].equals("create")) {
			if(args.length > 2 && args[1].equals("-private")) {
				if(args.length > 4 && args[3].equals("-receiver")) {
					if(args.length > 6 && args[5].equals("-amount")) {
						for(int i = 6; i < args.length && stop == false; i++) {
							if(args[i].equals("-currency")) {
								currencyIndex = i;
								stop = true;
							}
						}
						System.out.println("Curency Index: " + currencyIndex);
						amounts = new String[currencyIndex - 6];
						currency = new String[amounts.length];
						int b = 0;
						for(int i = 6; i < currencyIndex; i++) {
							if(!args[i].contains("-")) {
								amounts[b] =  args[i];
								b++;
							}
						}
						b = 0;
						for(int i = currencyIndex; i < args.length; i++) {
							if(!args[i].contains("-")) {
								currency[b] = args[i];
								b++;
							}
						}
						if(args.length > currencyIndex && args[currencyIndex].equals("-currency")) {
							if(args[2].length() != 0) {
								byte[] data = new byte[amounts.length], transHold = new byte[1];
								data = Integer.toString(amounts.length).getBytes();
								String numEnder = "|";
								for(int a = 0; a < amounts.length; a++) {
									byte[] currencyHold = new byte[1], amountHold = new byte[1];
									currencyHold = currency[a].getBytes();
									amountHold = amounts[a].getBytes();
									transHold = ByteUtil.concat(transHold, numEnder.getBytes(), currencyHold, numEnder.getBytes(), amountHold);
								}
								data = ByteUtil.concat(data, transHold);
								System.out.println(new String(data));
								Wallet w = null;
								try {
									w = new Wallet(KeyUtil.stringToPrivateKey(args[2]));
								} catch (GeneralSecurityException e) {
									e.printStackTrace();
								}
								Transaction t = new Transaction(Long.toString(w.getId()).getBytes(), w.getAdress(), args[4].getBytes(), "transactionCommand".getBytes(), "main".getBytes(), data);
								System.out.println(t.toString());
								BlockChain.addTransaction(t);
							}
						}
					}
				}
			}
			Commander.repeat = false;
		}else if(args[0].equals("-help")) {//General help
			System.out.println("-" + getHelp());
			Commander.repeat = false;
		}else if(args[0].equals("help")){//Specific parameter help
			System.out.print("- ");
			getParamHelp(args[1]);
			Commander.repeat = false;
		}
	}

	public void getParamHelp(String param) {
		System.out.println("------------------------------------------------------------------------");
		switch(param) {
		case "-private" :
			System.out.println("The private key of your wallet.");
			break;
		case "-receiver" :
			System.out.println("The public key of the receiver of the transaction.");
			break;
		case "-amount" :
			System.out.println("The different amounts of each currency. Must match the number of the currencies in the next parameter. Ex: 100 80 will create a transaction with a 100 of currency A and 80 of currency B");
			break;
		case "-currency" :
			System.out.println("The 3 digit currency codes of the different currencies. Must match the number of amounts in the previous parameter. Ex: USD YEN will create a transaction with A USD and B YEN.");
			break;
		default :
			System.out.println("Error: Invalid Parameter");
			break;
		}
		System.out.println("------------------------------------------------------------------------");
	}
	
	public String[] getAmounts() {
		return this.amounts;
	}
	
	public String[] getCurrencies() {
		return this.currency;
	}

}
