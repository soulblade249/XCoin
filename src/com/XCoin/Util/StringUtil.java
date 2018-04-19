package com.XCoin.Util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;

import org.bouncycastle.util.encoders.Hex;


public class StringUtil {
	
	private static KeyFactory kf;
    private static KeyPairGenerator kg;
    private static ECGenParameterSpec ecSpecGen;
    private static ECParameterSpec ecSpec;
    private static BigInteger ecN;
    private static BigInteger ecH;
    private static byte[] ecSeed;
    private static boolean isSetup = false;
	
	public static void SetupEC(){
       
		if(isSetup) return;
        try {       
            ecSpecGen = new ECGenParameterSpec("secp256r1");
            kf = KeyFactory.getInstance("ECDSA", "BC");
            kg = KeyPairGenerator.getInstance("ECDSA","BC");
            kg.initialize(ecSpecGen);
            KeyPair testPair = kg.generateKeyPair();
            ECPublicKey setupKey = (ECPublicKey) testPair.getPublic();
            ecSpec = setupKey.getParams();
            isSetup = true;      
        } catch (Exception e) {
            //TODO notify...
            System.err.println(e);
        }
    }
	
	//Applies Sha256 to a string and returns the result. 
	public static String applySha256(String input){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//Applies ECDSA Signature and returns the result ( as bytes ).
	public static byte[] applyECDSASig(PrivateKey privateKey, byte[] input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			dsa.update(input);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}
	
	//Verifies a String signature 
	public static boolean verifyECDSASig(PublicKey publicKey, byte[] data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data);
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] encodeECPublicKey(ECPublicKey publicKey){
        return ByteUtil.ecPointToBytes(publicKey.getW());
    }
    
    public static byte[] encodeECPrivateKey(ECPrivateKey privateKey){
        return ByteUtil.bigIntegerToBytes(privateKey.getS());
    }
	
	public static ECPublicKey stringToPublicKey(String input){
        byte[] pubKeyBytes = Hex.decode(input);
        return decodeECPublicKey(pubKeyBytes);
    }
    
    public static ECPrivateKey stringToPrivateKey(String input){
        byte[] privKeyBytes = Hex.decode(input);
        return decodeECPrivateKey(privKeyBytes);
    }
	
    public static ECPublicKey decodeECPublicKey(byte[] q){
        SetupEC();
        try {
            ECPoint point = ByteUtil.bytesToECPoint(q);
            ECPublicKeySpec pubSpec = new ECPublicKeySpec (point, ecSpec);
            return (ECPublicKey) kf.generatePublic(pubSpec);
        } catch (Exception e) {
            System.out.println("publickey decode failed");
            System.err.println(e);
            return null;
        }
    }
    
    public static ECPrivateKey decodeECPrivateKey(byte[] input){
        SetupEC();
        try {
            BigInteger s = ByteUtil.bytesToBigInteger(input);
            ECPrivateKeySpec privSpec = new ECPrivateKeySpec(s,ecSpec);
            return (ECPrivateKey) kf.generatePrivate(privSpec);
        } catch (Exception e) {
            System.out.println("privatekey decode failed");
            System.err.println(e);
            return null;
        }
    }
    
    public static String privateKeyToString(ECPrivateKey privateKey){
		SetupEC();
		byte[] privKeyBytes = StringUtil.encodeECPrivateKey(privateKey);
	    return Hex.toHexString(privKeyBytes);
	}
	
	public static String publicKeyToString(ECPublicKey publicKey){
		SetupEC();
		byte[] pubKeyBytes = StringUtil.encodeECPublicKey(publicKey);
        return Hex.toHexString(pubKeyBytes);
    }
    
	 public static String publicKeyToAddress(ECPublicKey publicKey){
	        byte[] pubKeyBytes = encodeECPublicKey(publicKey);
	        byte[] pubKeyHash = HashUtil.applySHA256(pubKeyBytes);
	        StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
	        for (int i = 0; i < pubKeyHash.length; i++) {
	            String hex = Integer.toHexString(0xff & pubKeyHash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }
    
	//Returns difficulty string target, to compare to hash. eg difficulty of 5 will return "00000"  
	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}

	public static KeyPair GenerateKeyPair(){
        SetupEC();
        try {
            kg = KeyPairGenerator.getInstance("ECDSA","BC");
            kg.initialize(ecSpec);
            KeyPair newKeyPair = kg.generateKeyPair();
            return newKeyPair;
        } catch (Exception e) {
            //TODO notify...
            System.err.println(e);
            return null;
        }
    }

}
