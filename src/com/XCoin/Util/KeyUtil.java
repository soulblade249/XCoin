package com.XCoin.Util;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;

import org.bouncycastle.util.encoders.Hex;


public class KeyUtil {
	
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
		byte[] privKeyBytes = KeyUtil.encodeECPrivateKey(privateKey);
	    return Hex.toHexString(privKeyBytes);
	}
	
	public static String publicKeyToString(ECPublicKey publicKey){
		SetupEC();
		byte[] pubKeyBytes = KeyUtil.encodeECPublicKey(publicKey);
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
	
	public static ECPublicKey getPublicKey(final ECPrivateKey pk) throws GeneralSecurityException {
        final ECParameterSpec params = pk.getParams();
        final ECPoint w = scalmult(params.getCurve(), pk.getParams().getGenerator(), pk.getS());
        final KeyFactory kg = KeyFactory.getInstance("EC");
        return (ECPublicKey)kg.generatePublic (new ECPublicKeySpec (w, params));
    }
	
	private static BigInteger BIG2 = BigInteger.valueOf(2);
    private static BigInteger BIG3 = BigInteger.valueOf(3);
    
	public static ECPoint scalmult   (final EllipticCurve curve, final ECPoint g, final BigInteger kin) {
	    final ECField         field    = curve.getField();
	    if(!(field instanceof ECFieldFp)) throw new UnsupportedOperationException(field.getClass().getCanonicalName());
	    final BigInteger p = ((ECFieldFp)field).getP();
	    final BigInteger a = curve.getA();
	    ECPoint R = ECPoint.POINT_INFINITY;
	    BigInteger k = kin.mod(p);
	    final int length = k.bitLength();
	    final byte[] binarray = new byte[length];
	    for(int i=0;i<=length-1;i++){
	        binarray[i] = k.mod(BIG2).byteValue();
	        k = k.shiftRight(1);
	    }
	    for(int i = length-1;i >= 0;i--){
	        R = doublePoint(p, a, R);
	        if(binarray[i]== 1) R = addPoint(p, a, R, g);
	    }
	    return R;
	}
	
	private static ECPoint doublePoint(final BigInteger p, final BigInteger a, final ECPoint R) {
	    if (R.equals(ECPoint.POINT_INFINITY)) return R;
	    BigInteger slope = (R.getAffineX().pow(2)).multiply(BIG3 );
	    slope = slope.add(a);
	    slope = slope.multiply((R.getAffineY().multiply(BIG2)).modInverse(p));
	    final BigInteger Xout = slope.pow(2).subtract(R.getAffineX().multiply(BIG2)).mod(p);
	    final BigInteger Yout = (R.getAffineY().negate()).add(slope.multiply(R.getAffineX().subtract(Xout))).mod(p);
	    return new ECPoint(Xout, Yout);
	}

	    private static ECPoint addPoint   (final BigInteger p, final BigInteger a, final ECPoint r, final ECPoint g) {
	    if (r.equals(ECPoint.POINT_INFINITY)) return g;
	    if (g.equals(ECPoint.POINT_INFINITY)) return r;
	    if (r==g || r.equals(g)) return doublePoint(p, a, r);
	    final BigInteger gX    = g.getAffineX();
	    final BigInteger sY    = g.getAffineY();
	    final BigInteger rX    = r.getAffineX();
	    final BigInteger rY    = r.getAffineY();
	    final BigInteger slope = (rY.subtract(sY)).multiply(rX.subtract(gX).modInverse(p)).mod(p);
	    final BigInteger Xout  = (slope.modPow(BIG2, p).subtract(rX)).subtract(gX).mod(p);
	    BigInteger Yout =   sY.negate().mod(p);
	    Yout = Yout.add(slope.multiply(gX.subtract(Xout))).mod(p);
	    return new ECPoint(Xout, Yout);
	}

}
