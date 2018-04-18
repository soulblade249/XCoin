package com.XCoin.Core;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import Util.ECDSAUtil.ECKey;
import org.bouncycastle.util.encoders.Hex;

import Util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Wallet{
	
	ECKey keypair;
	public String address;
	public int balance;
	
	public Wallet() {
		keypair = new ECKey();
	}

	public void importWallet(byte[] privKey) {
		keypair = ECKey.fromPrivate(privKey);
	}
	
	public int getBalance() {
		//returns the balance of the user
		return balance;
	}
	
	public void removeFunds(int amount) {
		balance = balance - amount;
	}
	
	public void addFunds(int amount) {
		balance = balance + amount;
	}
}

