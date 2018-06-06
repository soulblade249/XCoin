package com.XCoin.Networking.Commands;

import java.math.BigInteger;
import java.util.Random;

import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.ByteUtil;

public class PingCommandHandler extends Command {

	private BigInteger bInt;
	
	public byte[] recieve(byte[] data) {
		System.out.println(ByteUtil.bytesToBigInteger(data));
		return ByteUtil.concat(new ByteArrayKey((byte) 0xFF).toByteArray(), new ByteArrayKey((byte) 0x01).toByteArray(), data);
	}

	public byte[] send(byte[] data) {
		bInt = BigInteger.valueOf(new Random().nextLong());
		System.out.println("Sending Int: " + bInt.toString() );
		System.out.println(ByteUtil.bigIntegerToBytes(bInt).length);
		return ByteUtil.concat(new ByteArrayKey((byte) 0xFF).toByteArray(), new ByteArrayKey((byte) 0x01).toByteArray(), ByteUtil.bigIntegerToBytes(bInt));
	}
	
	private byte[] check(byte[] data) {
		return null;
	}

	@Override
	public byte[] handle(ByteArrayKey args) {
		System.out.println("Test");
		if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x00))) {
			System.out.println("Sending Ping");
			return send(args.subSet(2, args.toByteArray().length)); 
		} else if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x01))) {
			System.out.println("Recieved Ping");
			return recieve(args.subSet(2, args.toByteArray().length));
		} else if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x02))) {
			//return check(args);
		}
	
		return null;
	}

	

}
