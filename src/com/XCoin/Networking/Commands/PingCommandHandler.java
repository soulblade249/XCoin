package com.XCoin.Networking.Commands;

import java.math.BigInteger;
import java.util.Random;

import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.ByteUtil;

public class PingCommandHandler extends Command {

	private BigInteger bInt;
	@Override
	public byte[] recieve(ByteArrayKey args) {
		System.out.println(args.toString());
		return null;
	}

	@Override
	public byte[] send(ByteArrayKey args) {
		bInt = new BigInteger("0");
		bInt = BigInteger.valueOf(new Random().nextLong());
		System.out.println("Sending Int: " + bInt.toString() );
		return ByteUtil.bigIntegerToBytes(bInt);
	}

	@Override
	public byte[] handle(ByteArrayKey args) {
		System.out.println("Test");
		if(args.equals(new ByteArrayKey((byte) 0xFF))) {
			System.out.println("Sending Ping");
			return send(args); 
		} else if(args.equals(new ByteArrayKey((byte) 0x00))) {
			System.out.println("Recieved Ping");
			return recieve(args);
		}
	
		return null;
	}

}
