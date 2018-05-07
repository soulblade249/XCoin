/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.XCoin.Core.cli.commands;

import com.XCoin.Core.Wallet;
import com.XCoin.Core.cli.Main;
import com.XCoin.Util.KeyUtil;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class WalletCommand implements Command{

	private Wallet userWallet;

	@Override
	public String getHelp() {
		return "cmd: wallet \n" +
				"- description: A tool for creating a wallet \n" +
				"- usage: key-util param [situational...] \n"+
				"- param: 'create' [-private], 'retrieve' [-private], 'get' , 'info', '-help', '-params' \n"+
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[]{ "-help", "private", "create", "info", "-get", "retrieve"};
	}

	@Override
	public void run(String[] args) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		if(!Arrays.asList(getParams()).contains(args[0]) ){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
		}

		for(String s : args) {
			System.out.println("Args: " + s);
		}
		
		if(args[0].equals("create")) {
			if(args.length > 2 && args[1].equals("-private")) {
				try {
					userWallet = new Wallet(KeyUtil.stringToPrivateKey(args[2]));
					System.out.println("- Wallet Created");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				userWallet = new Wallet();
				System.out.println("- Wallet Created");
			}
			Main.wallets.put(userWallet, userWallet.getAdress());
		}else if(args[0].equals("retrieve")) {
			if(args.length > 2 && args[1].equals("-private")) {
				for(Map.Entry<Wallet, byte[]> w : Main.wallets.entrySet()) {
					if(w.getKey().getPrivate().equals(KeyUtil.stringToPrivateKey(args[2]))) {
						Wallet wallet = w.getKey();
						System.out.println(wallet.toString());
					}
				}
			}else {
				System.out.println("- Error: cannot retrieve without a private key. Would you like to create a wallet?(y/n): ");
				Scanner in = new Scanner(System.in);
				char choice = in.next().toLowerCase().charAt(0);
				if(choice == 'y') {
					run(new String[] {"create"});
				}else if(choice == 'n') {
					getHelp();
				}else {
					System.out.println("- Error: Invalid Choice");
				}
			}
		}else if(args[0].equals("-help")) {
			System.out.println("- " + getHelp());
		}else {
			System.out.println("- Not Supported");
		}
	}

}
