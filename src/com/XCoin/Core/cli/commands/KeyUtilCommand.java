
package com.XCoin.Core.cli.commands;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;

import com.XCoin.Util.KeyUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * This class 
 */
public class KeyUtilCommand implements Command {

	File file;
	BufferedReader f;
	Scanner in;

	@Override
	public String getHelp() {
		return  "cmd: key-util \n" +
				"- description: A tool for creating and/or extracting keys \n" +
				"- usage: key-util param [situational...] \n"+
				"- param: 'generate' [-private] [-save], 'pem' location [-private], 'info', '-help', '-params' \n"+
				"------------------------------------------------------------------------";
	}

	@Override
	public String[] getParams() {
		return new String[]{ "-help", "-params", "generate", "pem", "info", "-get"};
	}

	@Override
	
	public void run(String[] args){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		//String os = System.getProperty("os.name");
		String homeDir = System.getProperty("user.home");
		file = new File(homeDir + "/Desktop/" + "keypair.txt");
		if(!file.exists()) {
			System.out.println("File does not exist");
			try {
				file.createNewFile();
				System.out.println("Created new file");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			f = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		if(!Arrays.asList(getParams()).contains(args[0]) ){
			System.out.println("- " + "ERROR ! unknown parameters...");
			System.out.println("- " + Arrays.toString(getParams()));
			return;
		}
		if(args[0].equals("generate")){
			PrivateKey privateKey = null;
			PublicKey publicKey = null;
			KeyPair keys = null;




			if(args[1].equals("-private")) {
				
				privateKey = KeyUtil.stringToPrivateKey(args[1]);
				//publicKey = KeyUtil.privateKeyToPublicKey((ECPrivateKey)privateKey);
			}else{
				keys = KeyUtil.GenerateKeyPair();
				privateKey = keys.getPrivate();
				publicKey = keys.getPublic();
			}

			System.out.println("- " + "--- [XCoin KEY PAIR] ---");
			//converting key pairs to string:
			String priv = KeyUtil.privateKeyToString((ECPrivateKey) privateKey);
			String pub = KeyUtil.publicKeyToString((ECPublicKey) publicKey);
			//converting string back to keys:
			ECPrivateKey privkey = KeyUtil.stringToPrivateKey(priv);
			ECPublicKey pubkey = KeyUtil.stringToPublicKey(pub);
			//converting key to address:
			String address = KeyUtil.publicKeyToAddress(pubkey);
			// String private = KeyUtil.privateKeyToString(privkey);


			System.out.println("- " +  "Raw-Private-Key: " + KeyUtil.privateKeyToString(privkey));
			System.out.println("- " +  "Raw-Public-Key:  " + KeyUtil.publicKeyToString(pubkey));
			System.out.println("- " +  "Address:         " + KeyUtil.publicKeyToAddress(pubkey));
			System.out.println("- " + "------------------------");
			if(args[1].equals("-save")) {
				boolean proceed = false;
				boolean blank = true;
				in = new Scanner(f);
				if(in.hasNextLine()) {
					System.out.print("- Alert! You already have a keypair. To retrieve it, please type key-util -get .If you would like to override please confirm(y/n): ");
					Scanner user = new Scanner(System.in);
					String choice = user.next();
					if(choice.charAt(0) == 'y') {
						System.out.println("Overriding");
						proceed = true;
						blank = false;
					}
				}
				try{
					if(proceed || blank) {
						PrintWriter out = new PrintWriter(file);
						out.println("--- [XCoin KEY PAIR] ---");
						out.println("Raw-Private-Key: " + priv);
						out.println("Raw-Public-Key:  " + pub);
						out.println("Address:         " + address);
						out.println("------------------------");
						out.close();
						System.out.println("The file has been saved at " + file);
					}
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}else if(args[0].equals("-help")){
			System.out.println("- " + getHelp());
		}else if(args[0].equals("-get")) {
			in = new Scanner(f);
			System.out.println("--- [XCoin KEY PAIR] ---");
			while(in.hasNextLine()) {
				String curLine = in.nextLine();
				if(curLine.contains("Raw") || curLine.contains("Address")) {
					System.out.println(curLine);
				}
			}
			System.out.println("------------------------");

		}else {
			System.out.println("- " + "Sorry param not yet implemented");
		}
	}
}
