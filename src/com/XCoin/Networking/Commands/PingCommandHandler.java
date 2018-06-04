package com.XCoin.Networking.Commands;

import java.math.BigInteger;
import java.util.Random;

import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.ByteUtil;

public class PingCommandHandler extends Command {

	private BigInteger bInt;
	
	public byte[] recieve(ByteArrayKey args) {
		System.out.println(args.toString());
		return null;
	}

	public byte[] send(ByteArrayKey args) {
		bInt = BigInteger.valueOf(new Random().nextLong());
		System.out.println("Sending Int: " + bInt.toString() );
		return ByteUtil.concat(new ByteArrayKey((byte) 0xFF).toByteArray(), new ByteArrayKey((byte) 0x01).toByteArray(), ByteUtil.bigIntegerToBytes(bInt));
	}
	
	private byte[] check(ByteArrayKey args) {
		return null;
	}

	@Override
	public byte[] handle(ByteArrayKey args) {
		System.out.println("Test");
		if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x00))) {
			System.out.println("Sending Ping");
			return send(args); 
		} else if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x01))) {
			System.out.println("Recieved Ping");
			return recieve(args);
		} else if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x02))) {
			return check(args);
		}
	
		return null;
	}

	

}
