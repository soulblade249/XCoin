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
	public static boolean bMining;
	private static int difficulty = 3;
	private static float minimumTransaction = 0.1f;
	private static Block newBlock;
	
	public static void main(String[] args) throws IOException {	
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider	
	}
	
	private void loadChain() {
		
	}
	
	private void saveChain() {
		
	}
	
	public static boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i=1; i < blockchain.size(); i++) {
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("#Current Hashes are equal");
				return false;
			}
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("#Previous Hashes are equal");
				return false;
			}
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
		}	
		//System.out.println("Blockchain is valid");
		return true;
	}
	
	public static void addTransaction(Transaction t) {
		
	}

	public static void mine() {
		Block genesisBlock = new Block("0");
		blockchain.add(genesisBlock);
		bMining = true;
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(bMining) {
			newBlock = new Block(blockchain.get(0).hash);
			while(!newBlock.hash.substring( 0, difficulty).equals(target)) {
				newBlock.nonce ++;
				newBlock.hash = Block.calculateHash();
				if(!bMining) {
					break;
				}
			}
			if(newBlock.hash.substring(0, difficulty).equals(target)) {
				blockchain.add(newBlock);
				System.out.println("Block found: " + newBlock.hash + "!!!");
			}
		}
		System.out.println("Stopping mining");
	}
}

