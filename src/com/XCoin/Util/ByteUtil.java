package com.XCoin.Util;

import java.math.BigInteger;
import java.security.spec.ECPoint;
import java.util.Arrays;

public class ByteUtil {

	/* Converts ecPoint to a bytearray, java.security.spec.ECPoint not to be confused with bouncey castle ecpoint.*/
    public static byte[] ecPointToBytes( ECPoint input ){
    		BigInteger qx = input.getAffineX();
        BigInteger qy = input.getAffineY();
        byte[] qyBytes = bigIntegerToBytes(qy);
        byte[] qxBytes = bigIntegerToBytes(qx);
        byte[] xlengthBytes = new byte[1]; //info bit
        
        //fix length issues
        xlengthBytes[0] = (byte) qxBytes.length;

        byte[] buffer = concatenateBytes(xlengthBytes,bigIntegerToBytes(qx));
        buffer = concatenateBytes(buffer,bigIntegerToBytes(qy));
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
        return new ECPoint(qx,qy);
    }

    /**
     * @param arrays - arrays to merge
     * @return - merged array
     */
    public static byte[] merge(byte[]... arrays)
    {
        int arrCount = 0;
        int count = 0;
        for (byte[] array: arrays)
        {
            arrCount++;
            count += array.length;
        }

        // Create new array and copy all array contents
        byte[] mergedArray = new byte[count];
        int start = 0;
        for (byte[] array: arrays) {
            System.arraycopy(array, 0, mergedArray, start, array.length);
            start += array.length;
        }
        return mergedArray;
    }

    /**
     * @param valueA - not null
     * @param valueB - not null
     * @return true - if the valueA is less than valueB is zero
     */
    public static boolean isLessThan(BigInteger valueA, BigInteger valueB){
        return valueA.compareTo(valueB) < 0;
    }

    /**
     * The regular {@link BigInteger#toByteArray()} method isn't quite what we often need:
     * it appends a leading zero to indicate that the number is positive and may need padding.
     *
     * @param b the integer to format into a byte array
     * @param numBytes the desired size of the resulting byte array
     * @return numBytes byte long array.
     */
    public static byte[] bigIntegerToBytes(BigInteger b, int numBytes) {
        if (b == null)
            return null;
        byte[] bytes = new byte[numBytes];
        byte[] biBytes = b.toByteArray();
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    public static byte[] concat(byte[]...arrays)
    {
        // Determine the length of the result array
        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            totalLength += arrays[i].length;
        }

        // create the result array
        byte[] result = new byte[totalLength];

        // copy the source arrays into the result array
        int currentIndex = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
            currentIndex += arrays[i].length;
        }

        return result;
    }
}
