package com.XCoin.Core;
import java.util.ArrayList;
import java.util.Date;

import com.XCoin.Util.KeyUtil;

public class Block {
	
	public static String hash;
	public static String previousHash; 
	public static String merkleRoot;
	public static long timeStamp; //as number of milliseconds since 1/1/1970.
	public static int nonce;
	private static ArrayList<Transaction> transactionTable = new ArrayList<Transaction>();
	
	//Block Constructor.  
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	
	//Calculate new hash based on blocks contents
	public static String calculateHash() {
		String calculatedhash = KeyUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleRoot
				);
		return calculatedhash;
	}
	
	public static boolean hasTransaction(int size) {
		if(size > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void addTransaction(Transaction t) {
		transactionTable.add(t);
	}
	
	public static int getTransactionTableSize() {
		return transactionTable.size();	
	}
	
	public static Transaction getTransaction(int a) {
		return transactionTable.get(a);
	}
}
