package com.XCoin.Core;

import java.math.BigInteger;
import java.security.spec.ECPoint;
import java.util.Arrays;

public class ByteUtil {

	/* Converts ecPoint to a bytearray, java.security.spec.ECPoint not to be confused with bouncey castle ecpoint.*/
    public static byte[] ecPointToBytes( ECPoint input ){
    		System.out.println(input);
    		BigInteger qx = input.getAffineX();
        BigInteger qy = input.getAffineY();
        byte[] qyBytes = bigIntegerToBytes(qy);
        byte[] qxBytes = bigIntegerToBytes(qx);
        byte[] xlengthBytes = new byte[1]; //info bit
        
        //fix length issues
        xlengthBytes[0] = (byte) qxBytes.length;

        byte[] buffer = concatenateBytes(xlengthBytes,bigIntegerToBytes(qx));
        buffer = concatenateBytes(buffer,bigIntegerToBytes(qy));
        System.out.println(buffer.length);
        return buffer;        
    }
    
    /* converts a BigInteger to bytes, useful for hashing or serialization */
    public static byte[] bigIntegerToBytes(BigInteger input) {
        byte[] buffer = input.toByteArray();
        /*if (buffer[0] == 0) {
            byte[] bufferFixed = new byte[buffer.length - 1];
            System.arraycopy(buffer, 1, bufferFixed, 0, bufferFixed.length);
            buffer = bufferFixed;
        }*/
        return buffer;
    }
    
    /* concats two byte arrays [] into one in the order "ab". */
    public static byte[] concatenateBytes(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length); 
        return result;
    }
    
    /* converts bytes back into Big Integer */
    public static BigInteger bytesToBigInteger(byte[] input){
        return new BigInteger(input);
    }
    
    /* Converts byte array back into an ecpoint */
    public static ECPoint bytesToECPoint(byte[] input ){
        int midpoint = (int) input[0];
        BigInteger qx = bytesToBigInteger(Arrays.copyOfRange(input,1, midpoint+1));
        BigInteger qy = bytesToBigInteger(Arrays.copyOfRange(input,midpoint+1,input.length));
        System.out.println(new ECPoint(qx,qy));
        return new ECPoint(qx,qy);
    }
}
