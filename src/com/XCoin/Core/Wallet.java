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

import org.bouncycastle.util.encoders.Hex;

import Util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Wallet{
	
	public ECPrivateKey privateKey;
	public ECPublicKey publicKey;
	public String address;
	public int balance;
	
	public Wallet() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider	
		generateKeyPair();
	}
		
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256 
	        KeyPair keyPair = StringUtil.GenerateKeyPair();
            privateKey = (ECPrivateKey) keyPair.getPrivate();
            publicKey = (ECPublicKey) keyPair.getPublic();
            //converting key to address:
            address = StringUtil.publicKeyToAddress(publicKey);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
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

