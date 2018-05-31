package com.XCoin.Networking.Commands;

import com.XCoin.Util.ByteArrayKey;

public abstract class Command {

	public byte[] ID;
	
    public abstract byte[] recieve(ByteArrayKey args);
    
    public abstract byte[] send(ByteArrayKey args);

    //If leading byte is 0xFF, send, if 0x00 recieve
	public abstract byte[] handle(ByteArrayKey args);
	
}

