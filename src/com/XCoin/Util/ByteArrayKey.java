package com.XCoin.Util;

import java.util.Arrays;

public final class ByteArrayKey
{
    private final byte[] data;
    
    public ByteArrayKey(byte... data) {
    		if(data == null) {
    			throw new NullPointerException();
    		}
    		this.data = data;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof ByteArrayKey))
        {
            return false;
        }
        return Arrays.equals(data, ((ByteArrayKey)other).data);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(data);
    }
    
    public byte[] toByteArray(){
        return data;
    }
    
    public byte[] subSet(int a, int b) {
    		System.out.println("B" + b + "A" + a);
		byte[] temp = new byte[b-a+1];
		try {
    			for(int i = 0; i < b-a+1; i++) {
    				temp[i] = data[i+a];
    			}
    			return temp;
    		} catch (Exception e){
    			e.printStackTrace();
    		}
		return null;
    }
}
