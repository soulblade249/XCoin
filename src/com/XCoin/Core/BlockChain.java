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
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
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
			Block.getTransactions();
		}
		//System.out.println("Stopping mining");
	}

	public static void onTerminate() throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("blockChain.txt"));
		for(Block b : blockchain) {
			out.println("Block Hash: " + b.hash + " Time: " + b.timeStamp);
		}
		out.close();
	}

	public static void processTransactions() throws IOException {
		boolean noFunds = false;
		PrintWriter out = new PrintWriter(new FileWriter("wallets.dat"));
		String retrievedData = "";
		int size = Block.getTransactionTableSize()-1, senderIndex = 0, receiverIndex = 0;
		System.out.println("Size: " + size);
		for(Block c : blockchain) {
			//System.out.println("We have a block");
			while(Block.hasTransaction(size) && noFunds == false) {
				//System.out.println("Block has Transaction");
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
				int walletIndex = 0;
				for(Wallet w : Main.wallets) {
					String wHex = Hex.toHexString(w.getAddress());
					byte[] walletAddress = wHex.getBytes();
					if(Arrays.equals(walletAddress, t.getSender())) {
						senderWallet = w;
						senderIndex = walletIndex;
					}else if(Arrays.equals(walletAddress, t.getReceiver())) {
						receiverWallet = w;
						receiverIndex = walletIndex;
					}
					walletIndex++;
				}
				senderWallet = Main.testWallet;
				String[] part = retrievedData.replace("|", " ").split(" ");
				for(int a = 0; a < part.length/2; a += 2) {
					if(senderWallet.hasFunds(part[a], Long.parseLong(part[a+1]))) {
						System.out.println("Sender Wallet bfr: " + senderWallet.getBal() + " now removing " + part[a+1] + " " + part[a]);
						senderWallet.removeFunds(part[a], Long.parseLong(part[a+1]));
						System.out.println("Sender Wallet After: " + senderWallet.getBal());
						System.out.println("Receiver Wallet bfr: " + receiverWallet.getBal() + " now adding " + part[a+1] + " " + part[a]);
						receiverWallet.addFunds(part[a], Long.parseLong(part[a+1]));
						System.out.println("ReceiverWallet After: " + receiverWallet.getBal());
					}else {
						System.out.println("Error: User does not have enough funds");
						noFunds = true;
					}
				}
				Main.wallets.set(senderIndex, senderWallet);
				Main.wallets.set(receiverIndex, receiverWallet);
				size--;
			}
		}
		for(Wallet w : Main.wallets) { //Print the Wallets
			out.println(w);
		}
		out.close();
		System.exit(1);
	}

	public static void propagateWallet() throws FileNotFoundException, GeneralSecurityException {
		boolean priv = false, pub = false, bal1 = false, id1 = false;
		File file = new File("wallets.dat");
		BufferedReader f = new BufferedReader(new FileReader(file));
		Scanner in = new Scanner(f);
		ECPrivateKey privateKe = null;
		ECPublicKey pubKey = null;
		int id = 0;
		HashMap<String, Long> balMap = new HashMap<String, Long>();
		while(in.hasNextLine()) {
			String line = in.nextLine();
			if(line.contains("Priv: ")) {
				int index = line.indexOf(" ");
				String privateKey = line.substring(index+1, line.length());
				privateKe = KeyUtil.stringToPrivateKey(privateKey);
				priv = true;
			}else if(line.contains("Pub: ")) {
				int index = line.indexOf(" ");
				String publicKey = line.substring(index+1, line.length());
				pubKey = KeyUtil.stringToPublicKey(publicKey);
				pub = true;
			}else if(line.contains("Bal: ")) {
				int index = line.indexOf(" ");
				String output = line.substring(index+1, line.length());
				output = output.replace("{", "");
				output = output.replace("}", "");
				output = output.replace(",", "");
				output = output.replace("=", "-");
				String[] balArray = output.split(" ");
				for(String bal : balArray) {
					String[] balStuff = bal.split("-");
					balMap.put(balStuff[0], Long.parseLong(balStuff[1]));
				}
				bal1 = true;
			}else if(line.contains("Id: ")) {
				int index = line.indexOf(" ");
				id = Integer.parseInt(line.substring(index+1, line.length()));
				id1 = true;
			}
			if(priv && pub && bal1 && id1) {
				Wallet w = new Wallet(privateKe, pubKey, balMap, id);
				Main.wallets.add(w);
				priv = false;
				pub = false;
				bal1 = false;
				id1 = false;
				privateKe = null;
				pubKey = null;
				id = 0;
				balMap = new HashMap<String, Long>();
			}
		}
	}

	public static ArrayList<Transaction> getMemPool() {
		return mempool;
	}
}