package com.XCoin.Core;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

import com.XCoin.Core.cli.Main;
import com.XCoin.Util.KeyUtil;

import java.math.BigInteger;

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
	private long balance;
	
	/**
	 * The id of the wallet
	 */
	private long walletId;
	
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
		this.walletId = Main.wallets.size();
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
		this.walletId = Main.wallets.size();
	}
	
	/**
	 * Gets the private key of the wallet
         * @return the private key
	 */
	public ECPrivateKey getPrivate() {
		return this.privateKey;
	}
	
	/**
	 * Returns the address of the wallet
         * @return the address
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
	
	/**
	 * Gets the id of the wallet
	 * @return the id of the wallet
	 */
	public long getId() {
		return this.walletId;
	}
	
	/**
	 * To String for a file
	 */
	public String fileToString() {
		return "Priv: " + KeyUtil.privateKeyToString(this.privateKey);
	}
	
	@Override
	public String toString() {
		return "Priv: " + KeyUtil.privateKeyToString(this.privateKey) + " \n" + "Pub: " + KeyUtil.publicKeyToString(this.publicKey) + " \n" + "Bal: "+ this.balance + " \n"+ "Id: " + this.walletId;
	}
}

