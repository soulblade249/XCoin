package com.XCoin.Core;
import Util.ECDSAUtil.ECKey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static Util.ByteUtil.concat;
import static Util.HashUtil.applySHA256;


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
	
	public Transaction (int value, String receiver, String sender, long t, byte[] privKey) {
		this.value = value;
		this.timeStamp = t;
		this.reciever = receiver;
		this.sender = sender;
		signTransaction(privKey);
	}

	private void signTransaction(byte[] privKey) {
        ECKey keypair = ECKey.fromPrivate(privKey);
        String toSign = Integer.toString(value) + "," +
                Long.toString(timeStamp) + "," +
                reciever + "," +
                sender;
        ECKey.ECDSASignature sig = keypair.sign(applySHA256(toSign.getBytes()));
        this.r = sig.r.toByteArray();
        this.s = sig.s.toByteArray();
        this.v = sig.v;
    }

    public boolean checkSign() {
        String toSign = Integer.toString(value) + "," +
                Long.toString(timeStamp) + "," +
                reciever + "," +
                sender;
        ECKey.ECDSASignature sig = ECKey.ECDSASignature.fromComponents(r,s,v);
        try{
            return ECKey.verify(applySHA256(applySHA256(toSign.getBytes())),sig, ECKey.signatureToKeyBytes(applySHA256(applySHA256(toSign.getBytes())),sig));
        } catch(Exception e) {
            return false;
        }
    }
}
