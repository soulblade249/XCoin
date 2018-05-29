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
	private String[] currency;
	private String[] amounts;
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
								String datArgs = "";
								for(int i = 0; i < currency.length; i++) {
									datArgs += amounts[i] + currency[i];
								}
								System.out.println("DatArgs: " + datArgs);
								Wallet w = null;
								try {
									w = new Wallet(KeyUtil.stringToPrivateKey(args[2]));
								} catch (GeneralSecurityException e) {
									e.printStackTrace();
								}
								Transaction t = new Transaction(Long.toString(w.getId()).getBytes(), w.getAdress(), args[4].getBytes(), "transactionCommand".getBytes(), "main".getBytes(), datArgs.getBytes());
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
