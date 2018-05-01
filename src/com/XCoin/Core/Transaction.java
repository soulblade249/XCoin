package com.XCoin.Core;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import com.XCoin.Util.ByteUtil;
import com.XCoin.Util.HashUtil;


public class Transaction {
	
	private final byte[] hash;
	private final byte[] nonce;
	private final byte[] sender;
	private final byte[] receiver;
	private final byte[] signature;
	private final byte[] networkId;
	/* First byte is amount of different currency type, leading bytes will be currencies involved(firstByte(currencyType), secondByte(amount of bytes needed to hold the amount), thirdByte(amount)). */
	private final byte[] data;
	
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
		return HashUtil.applySHA256(ByteUtil.concat(nonce, sender, receiver, signature, networkId));
	}
	
	private byte getLeadingByte() {
		return data[0];
	}
	
	public static void main(String args[]) {
		byte[] data = "1".getBytes(;
		Transaction test = new Transaction("test".getBytes(), "jules".getBytes(), "steven".getBytes(), "ghy".getBytes(), "main".getBytes(), "123".getBytes());
		String output = new String(new byte[] {test.getLeadingByte()});
		System.out.println("Leading Byte: " + output);
	}
	
}

