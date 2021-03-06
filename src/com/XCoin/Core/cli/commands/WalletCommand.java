/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.XCoin.Core.cli.commands;

import com.XCoin.Core.Wallet;
import com.XCoin.Core.cli.Commander;
import com.XCoin.Core.cli.Main;
import com.XCoin.Util.KeyUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import org.bouncycastle.util.encoders.Hex;

import java.security.interfaces.ECPublicKey;

/**
 *
 * @author student
 */
public class WalletCommand implements Command{

	private Wallet userWallet;

	private File file;

	private 	BufferedReader f;


	@Override
	public String getHelp() {
		return "cmd: wallet \n" +
				"- description: A tool for creating a wallet \n" +
				"- usage: key-util param [situational...] \n"+
				"- param: 'create' [-private], 'retrieve' [-private], 'info', '-help', '-params' \n"+
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
		String homeDir = System.getProperty("user.home");
		file = new File(homeDir + "/Desktop/" + "wallets.txt");
		if(!file.exists()) {
			System.out.println("File does not exist");
			try {
				file.createNewFile();
				System.out.println("Created new file");
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			PrintWriter out = null;
			try {
				out = new PrintWriter(new FileWriter(file, true));
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println(userWallet.fileToString());
			out.close();
			Main.wallets.add(userWallet);
			Commander.repeat = false;
		}else if(args[0].equals("retrieve")) {
			for(Wallet w : Main.wallets) {
				if(KeyUtil.privateKeyToString(w.getPrivate()).equals(args[2])) {
					userWallet = w;
					System.out.println("- Wallet imported");
					Scanner subMenuScanner = new Scanner(System.in);
					int choice = 0;
					while(choice != 5) {
						System.out.println("Wallet Options");
						System.out.println("1. Get Private Key");
						System.out.println("2. Get Public Key");
						System.out.println("3. Get Address");
						System.out.println("4. Get Bal");
						System.out.println("5. Quit");
						System.out.print("Choice: ");
						choice = subMenuScanner.nextInt();
						switch(choice) {
						case 1:
							System.out.println("Private Key: " + KeyUtil.privateKeyToString(userWallet.getPrivate()));
							break;
						case 2:
							System.out.println("Public Key: " + KeyUtil.publicKeyToString(userWallet.getPublic()));
							break;
						case 3:
							System.out.println("Address: " + Hex.toHexString(userWallet.getAddress()));
							break;
						case 4:
							System.out.println("Balance: " + userWallet.getBal());
							break;
						case 5:
							break;
						default:
							System.out.println("Incorrect Choice");
							break;
						}
						if(choice == 5) {
							break;
						}
					}
				}
			}
			System.out.println("Error: Invalid private key");
			Commander.repeat = false;
		}else if(args[0].equals("-help")) {
			System.out.println("- " + getHelp());
			Commander.repeat = false;
		}else if(args[0].equals("-params")){
			getParams();
			Commander.repeat = false;
		}else {
			System.out.println("- Not Supported");
		}
	}

}
