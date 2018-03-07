package com.XCoin.Core;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.HashMap;
//import com.google.gson.GsonBuilder;
import java.util.Map;

public class BlockChain {
	
	private static ArrayList<Block> blockchain = new ArrayList<Block>();
	private static ArrayList<Transaction> mempool = new ArrayList<Transaction>();
	
	private static int difficulty = 3;
	private static float minimumTransaction = 0.1f;
	public static Wallet walletA;
	public static Wallet walletB;

	
	public static void main(String[] args) throws IOException {	
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		//Import StringUtil
		StringUtil su = new StringUtil();

		//Create wallets:
		walletA = new Wallet();
		walletB = new Wallet();		
		Wallet coinbase = new Wallet();
		
		System.out.println();
		System.out.println(su.getStringFromKey(walletA.privateKey));
		//System.out.println(su.getStringFromKey(walletA.publicKey));

		
		//Encrypt String Password 
		String pwd = "Password";
		System.out.println(su.applyECDSASig(walletA.privateKey, pwd));
		System.out.println(su.verifyECDSASig(walletA.publicKey, pwd, su.applyECDSASig(walletA.privateKey, pwd)));
		
		//isChainValid();
	}
	
	public static boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("#Current Hashes are equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("#Previous Hashes are equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
		}	
		System.out.println("Blockchain is valid");
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}

