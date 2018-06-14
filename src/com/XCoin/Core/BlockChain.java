package com.XCoin.Core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.bouncycastle.util.encoders.Hex;

import com.XCoin.Core.cli.Main;
//import java.util.Base64;
import com.XCoin.GUI.MinerGui;
import com.XCoin.Util.KeyUtil;
import com.XCoin.Util.TransactionUtil;

public class BlockChain{

	private static ArrayList<Block> blockchain = new ArrayList<Block>();
	private static ArrayList<Transaction> mempool = new ArrayList<Transaction>();
	private static HashMap<String, Integer> decodedTransactions = new HashMap<String, Integer>();
	public static boolean bMining;
	private static int difficulty = 5;
	private static float minimumTransaction = 0.1f;
	private static Block newBlock;
	private static Wallet senderWallet;
	private static Wallet receiverWallet;

	public static void main(String[] args) throws IOException {	
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider	
	}
	
	public BlockChain() {
		Block genesisBlock = null;
		if(blockchain.size() == 0) {
			genesisBlock = new Block("0");
			blockchain.add(genesisBlock);
		}
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
		mempool.add(t);
	}

	public static void mine(MinerGui gui) {
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
				gui.displayText("Block Mined: " + newBlock.hash + " Time: " + newBlock.timeStamp);
			}
			newBlock.getTransactions();
		}
		System.out.println("Stopping mining");
	}

	public static void onTerminate() throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("blockChain.txt"));
		for(Block b : blockchain) {
			out.println("Block Hash: " + b.hash + " Time: " + b.timeStamp);
		}
		out.close();
	}

	public static void processTransactions() throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter("wallets.dat"));
		String retrievedData = "";
		int size = Block.getTransactionTableSize()-1;
		System.out.println("Size: " + size);
		for(Block c : blockchain) {
			System.out.println("We have a block");
			while(c.hasTransaction(size)) {
				System.out.println("Block has Transaction");
				Transaction t = c.getTransaction(size);
				System.out.println(t.toString());
				byte[] data = t.getData();
				for(int a = 0; a < data.length; a++) {
					Byte b = data[a];
					System.out.println(b);
					if(b.equals("|".getBytes()[0])) {
						a++;
						int endIndex = TransactionUtil.getIndex(data, "|".getBytes()[0], a);
						while(a != endIndex && a < data.length) {
							retrievedData += new String(new byte[] { data[a] });
							a++;
						}
						retrievedData += "|";
						a--;
					}
				}	
				for(Wallet w : Main.wallets) {
					System.out.println("Checking Wallets");
					String wAddress = Hex.toHexString(w.getAddress());
					String senderAdress = Hex.toHexString(t.getSender());
					String receiverAdress = Hex.toHexString(t.getReceiver());
					if(wAddress.equals(senderAdress)) {
						senderWallet = w;
						System.out.println("Sender");
					}else if(wAddress.equals(receiverAdress)) {
						receiverWallet = w;
						System.out.println("Receiver");
					}
					senderWallet = Main.testWallet;
					String[] part = retrievedData.replace("|", " ").split(" ");		
					for(int a = 0; a < part.length/2 ; a++) {
						System.out.println("Calling has balance with senderWallet: " + senderWallet + " partA: " + part[a]);
						System.out.println("Receiver Wallet: " + receiverWallet + " partA: " + part[a]);
						if(TransactionUtil.hasBalance(senderWallet, part[a])) {
							senderWallet.removeFunds(part[a], Long.parseLong(part[a+1]));
							receiverWallet.addFunds(part[a], Long.parseLong(part[a+1]));
							a++;
						}else {
							System.out.println("Error: Sender has none of the currency: " + part[a]);
						}
					}
					size--;
				}
			}
			for(Wallet w : Main.wallets) { //Print the Wallets
				out.println(w);
			}
			System.out.println("Sender Wallet now has balance: " + senderWallet.getBal());
			System.out.println("Receiver Wallet now has balance: " + receiverWallet.getBal());
			out.close();
		}
	}

	public static void propagateWallet() throws FileNotFoundException, GeneralSecurityException { //Propagation of the wallets is not working as intented
		String homeDir = System.getProperty("user.home");
		File file = new File(homeDir + "/Desktop/" + "wallets.txt");
		BufferedReader f = new BufferedReader(new FileReader(file));
		Scanner in = new Scanner(f);
		while(in.hasNextLine()) {
			String line = in.nextLine();
			int index = line.indexOf(" ");
			String privateKe = line.substring(index + 1, line.length());
			System.out.println("Current Private Key: " + privateKe);
			Wallet w = new Wallet(KeyUtil.stringToPrivateKey(privateKe), true);
			Main.wallets.add(w);
		}
	}

	public static ArrayList<Transaction> getMemPool() {
		return mempool;
	}
}