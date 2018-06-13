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

import org.bouncycastle.util.encoders.Hex;

import com.XCoin.Util.ByteUtil;
import com.XCoin.Util.HashUtil;
import com.XCoin.Util.KeyUtil;


public class Transaction {
	
	private final byte[] hash;
	private final byte[] sender;
	private final byte[] receiver;
	private final  byte[] signature;
	private final byte[] networkId;
	/* First byte is amount of different currency type, leading bytes will be currencies involved(firstByte(currencyType), secondByte(amount of bytes needed to hold the amount), thirdByte(amount)). */
	private byte[] data;
	
	public Transaction(byte[] sender, byte[] receiver, byte[] signature, byte[] networkid, byte[] data) {
		this.data = data;
		this.sender = sender;
		this.receiver = receiver;
		this.signature = signature;
		this.networkId = networkid;
		System.out.println("Created Stuff, Hash");
		this.hash = getHash(this.sender, this.receiver, this.signature, this.networkId, this.data);
		System.out.println("Created Hash");
	}
	
	public Transaction(byte[] hash, byte[] sender, byte[] receiver, byte[] signature, byte[] networkid, byte[] data) {
		this.data = data;
		this.sender = sender;
		this.receiver = receiver;
		this.signature = signature;
		this.networkId = networkid;
		this.hash = hash;
	}
	
	
	private byte[] getHash(byte[] ... data) {
		byte[] temp = null;
		temp = ByteUtil.merge(data);
		return HashUtil.applySHA256(temp);
	}
	
	private String hashToString(byte[] data) {
		return Hex.toHexString(data);
	}
	
	public Byte getLeadingByte() {
		return data[0];
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	public byte[] getSender() {
		return this.sender;
	}
	
	public byte[] getReceiver() {
		return this.receiver;
	}
	
	@Override
	public String toString() {
		String hash = "", sender = "", receiver = "", sig = "", net = "", data = "";
		try {
			sender = new String(this.sender, "UTF-8");
			receiver = new String(this.receiver, "UTF-8");
			sig = new String(this.signature, "UTF-8");
			net = new String(this.networkId, "UTF-8");
			data = new String(this.data, "UTF-8");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Hash: " + hashToString(getHash(this.sender, this.receiver, this.signature, this.networkId, this.data)) + "| Sender: " + sender + " | Receiver: " + receiver + " | Signature Id: " + sig + " | Network Id: " + net.toString() + " | Data: " + data;
	}
	
	public byte[] toByteArray() {
		return ByteUtil.concat(hash, sender, receiver, signature, networkId, data);
	}
}

