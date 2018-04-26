
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
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * This class 
 */
public class KeyUtilCommand implements Command {
    
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
		return new String[]{ "-help", "-params", "generate", "pem", "info"};
	}

	@Override
	public void run(String[] args){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		if( !Arrays.asList(getParams()).contains(args[0]) ){
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
                        File file = new File("keypair.txt");
                        if(args[1].equals("-save")) {
                           // File outputFile = new File("%appdata%/XCoin/KeyPair/keypair.txt");
//                            outputFile.getParentFile().mkdirs();
                            try{
                             PrintWriter out = new PrintWriter(file);
                               out.println("--- [XCoin KEY PAIR] ---");
                               out.println("Raw-Private-Key: " + priv);
                               out.println("Raw-Public-Key:  " + pub);
                               out.println("Address:         " + address);
                               out.println("------------------------");
                               out.close();
                            }catch(FileNotFoundException e) {
                                e.printStackTrace();
                            }
			}else if(args[1].equals("-get")) {
                            try {
                                BufferedReader f = new BufferedReader(new FileReader(file));
                                Scanner in = new Scanner(f);
                                
                            }catch(FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            
                        }
		}else if(args[0].equals("-help")){
			System.out.println("- " + getHelp());
		}else {
			System.out.println("- " + "Sorry param not yet implemented");
		}
	}

}
