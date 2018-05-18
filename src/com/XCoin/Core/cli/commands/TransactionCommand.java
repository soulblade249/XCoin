package com.XCoin.Core.cli.commands;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Arrays;

import com.XCoin.Core.Transaction;
import com.XCoin.Core.Wallet;
import com.XCoin.Util.ByteUtil;
import com.XCoin.Util.KeyUtil;

public class TransactionCommand implements Command{

	private byte[] currencies;
	private byte[] data;
	
	@Override
	public String getHelp() {
		return  "cmd: transaction \n" +
				"- description: A tool for creating transactions, if more info is needed about parameter, please type 'transaction help paramName' \n" +
				"- usage: transaction param [situational...] \n"+
				"- param: 'create' [-private] [-receiver] [-amount] [-currency], 'accept' [-privateKey], 'help' [-param] \n"+
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

		if(args[0].equals("create")) {
			if(args.length > 2 && args[1].equals("-private")) {
				if(args.length > 4 && args[3].equals("-receiver")) {
					if(args.length > 6 && args[5].equals("-amount")) {
						if(args.length > 8 && args[7].equals("-currency")) {
							if(args[2].length() != 0) {
								/*
								 * TODO:
								 * Data is not working, hashing returns non unicode characters
								 * Data:
								 * 	Needs to include amount of the currencies.
								 */
								Wallet w = null;
								try {
									w = new Wallet(KeyUtil.stringToPrivateKey(args[2]));
								} catch (GeneralSecurityException e) {
									e.printStackTrace();
								}
								if(args.length > 8) {
									data = args[6].getBytes();
									for(int i = 8; i < args.length; i++) {
										System.out.println("Currency: " + args[i]);
										currencies = args[i].getBytes();
										data = ByteUtil.concat(data, " ".getBytes(), currencies);
									}
								}
								try {
									String dataCur = new String(data, "UTF-8");
									System.out.println(dataCur);
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
								System.out.println("Transaction Created");
								Transaction t = new Transaction(Long.toString(w.getId()).getBytes(), w.getAdress(), args[4].getBytes(), "transactionCommand".getBytes(), "main".getBytes(), data);
								System.out.println(t.toString());
							}
						}
					}
				}
			}
		}
	}

	public void getParamHelp(String param) {

	}

}
