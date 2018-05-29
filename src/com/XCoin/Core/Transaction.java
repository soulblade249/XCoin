package com.XCoin.Core;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.security.Key;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import com.XCoin.Util.ByteUtil;
import com.XCoin.Util.HashUtil;
import com.XCoin.Util.KeyUtil;


public class Transaction {
	
	private final byte[] hash;
	private final byte[] nonce;
	private final byte[] sender;
	private final byte[] receiver;
	private final  byte[] signature;
	private final byte[] networkId;
	/* First byte is amount of different currency type, leading bytes will be currencies involved(firstByte(currencyType), secondByte(amount of bytes needed to hold the amount), thirdByte(amount)). */
	private byte[] data;
	
	public Transaction(byte[] nonce, byte[] sender, byte[] receiver, byte[] signature, byte[] networkid, byte[] data) {
		this.data = data;
		this.nonce = nonce;
		this.sender = sender;
		this.receiver = receiver;
		this.signature = signature;
		this.networkId = networkid;
		this.hash = getHash();
	}
	
	private byte[] getHash() {
		String hashS = new String(ByteUtil.concat(nonce, sender));
		return HashUtil.applySHA256(hashS.getBytes());
	}
	
	private String hashToString(byte []nonce, byte[]sender) {
		String hashS = new String(ByteUtil.concat(nonce, sender));
		return hashS;
	}
	
	public Byte getLeadingByte() {
		return data[0];
	}
	
	@Override
	public String toString() {
		String hash = "", nonce = "", sender = "", receiver = "", sig = "", net = "", data = "";
		try {
			nonce = new String(this.nonce, "UTF-8");
			sender = new String(this.sender, "UTF-8");
			receiver = new String(this.receiver, "UTF-8");
			sig = new String(this.signature, "UTF-8");
			net = new String(this.networkId, "UTF-8");
			data = new String(this.data, "UTF-8");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Hash: " + hashToString(this.nonce, this.sender) + " | Nonce: " + nonce + " | Sender: " + sender + " | Receiver: " + receiver + " | Signature Id: " + sig + " | Network Id: " + net + " | Data: " + data;
	}
	
}

