package com.XCoin.Networking.Commands;

import com.XCoin.Util.ByteArrayKey;

public abstract class Command {

    //If leading byte is 0xFF, send, if 0x00 recieve
	public abstract byte[] handle(ByteArrayKey args);
	
}

