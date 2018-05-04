package com.XCoin.Core;
import java.security.GeneralSecurityException;
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

import com.XCoin.Util.KeyUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Wallet{
	/**
	 * The private key of the wallet
	 */
	private ECPrivateKey privateKey;
	/**
	 * The public key of the wallet
	 */
	private ECPublicKey publicKey;
	/**
	 * The address of the wallet
	 */
	private byte[] adress;
	/**
	 * The balance of the wallet
	 */
	private BigInteger balance;
	
	/**
	 * Default constructor with no arguments
	 */
	public Wallet() {
		/*
		 * Create the key pair and the different keys
		 */
		KeyPair keys = null;
		PrivateKey priv = null;
		PublicKey pub = null;

		/*
		 * Generate the keys and get private and public ones 
		 */
		keys = KeyUtil.GenerateKeyPair();
		priv = keys.getPrivate();
		pub = keys.getPublic();
		String privateK = KeyUtil.privateKeyToString((ECPrivateKey) priv);
		String pubK = KeyUtil.publicKeyToString((ECPublicKey) pub);
		this.privateKey = KeyUtil.stringToPrivateKey(privateK);
		this.publicKey = KeyUtil.stringToPublicKey(pubK);
		this.adress = (KeyUtil.publicKeyToAddress(publicKey)).getBytes();
		this.balance = 0;
	}
	
	/**
	 * Creates a new wallet
	 * @param privKey the private key of the wallet
	 * @throws GeneralSecurityException 
	 */
	
	public Wallet(ECPrivateKey privKey) throws GeneralSecurityException {
		this.privateKey = privKey;
		this.publicKey = KeyUtil.getPublicKey(privKey);
		this.adress = (KeyUtil.publicKeyToAddress(this.publicKey)).getBytes();
		this.balance = 0;
	}
	/**
	 * Creates a new wallet
	 * @param privKey the private key of the wallet
	 * @param bal the balance
	 * @throws GeneralSecurityException 
	 */
	public Wallet(ECPrivateKey privKey, long bal) throws GeneralSecurityException {
		this.privateKey = privKey;
		this.publicKey = KeyUtil.getPublicKey(privKey);;
		this.adress = (KeyUtil.publicKeyToAddress(this.publicKey)).getBytes();
		this.balance = bal;
	}
	/**
	 * Gets the private key of the wallet
	 */
	public ECPrivateKey getPrivate() {
		return this.privateKey;
	}
	
	/**
	 * Returns the address of the wallet
	 */
	public byte[] getAdress() {
		return this.adress;
	}
	
	/**
	 * Returns the balance
	 */
	public long getBal() {
		return this.balance;
	}
	
	/**
	 * Adds funds to the wallet
	 * @param amount to add
	 */
	public void addFunds(long amount) {
		this.balance += amount;
	}
	
	/**
	 * Removes funds from the wallet
	 * @param amount to remove
	 */
	public void removeFunds(long amount) {
		this.balance -= amount;
	}
	
}

