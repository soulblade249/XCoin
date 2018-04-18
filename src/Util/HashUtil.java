package Util;

import java.security.MessageDigest;

public class HashUtil {

	//Applies Sha256 to a string and returns the result. 
	public static byte[] applySHA256(byte[] input){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input);
	        
			return hash;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
