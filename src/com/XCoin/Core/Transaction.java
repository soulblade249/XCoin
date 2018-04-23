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
	
	public Transaction (int v, String r, String s, long t) {
		this.value = v;
		this.timeStamp = t;
		this.reciever = r;
		this.sender = s;
	}
}
