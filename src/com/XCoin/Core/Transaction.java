package com.XCoin.Core;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class Transaction {

	private byte[] blockHash;
	private int blockHeight;
	public String sender;
	public String reciever;
	private String hash;
	private int nonce;
	public long timeStamp;
	private int index;
	public int value;
	private byte[] r;
	private byte[] s;
	private byte v; 
	
	public Transaction (int value, String reciever, String sender, long time, byte[] key) {
		this.value = value;
		this.timeStamp = time;
		this.reciever = reciever;
		this.sender = sender;
		signTransaction(key);
	}
	
	private void signTransaction(byte[] privKey) {
	    KeyPair keypair = KeyPair.fromPrivate(privKey);
	    String toSign = Integer.toString(value) + "," + Long.toString(timeStamp) + "," + reciever + "," + sender;
	    ECKey.ECDSASignature sig = keypair.sign(applySHA256(toSign.getBytes()));
        this.r = sig.r.toByteArray();
        this.s = sig.s.toByteArray();
        this.v = sig.v;
	}
}
