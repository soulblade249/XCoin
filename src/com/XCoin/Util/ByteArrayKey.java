package com.XCoin.Util;

import java.util.Arrays;

public final class ByteArrayKey
{
    private final byte[] data;

    public ByteArrayKey(byte[] data)
    {
        if (data == null)
        {
            throw new NullPointerException();
        }
        this.data = data;
    }
    
    public ByteArrayKey(byte data) {
    		this.data = new byte[1];
    		this.data[0] = data;
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
}
