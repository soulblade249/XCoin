package com.XCoin.Networking.Commands;

import java.math.BigInteger;
import java.util.Random;

import com.XCoin.Core.Transaction;
import com.XCoin.Util.ByteArrayKey;
import com.XCoin.Util.ByteUtil;

public class TransactionCommandHandler extends CommandHandler {

	public byte[] recieve(ByteArrayKey data) {
		Transaction t = new Transaction(data.subSet(0, 31), data.subSet(32, 63), data.subSet(64, 81), data.subSet(82, 82), data.subSet(83, 98), data.subSet(99, data.toByteArray().length-1));
		System.out.println(t);
		return null;
	}

	public byte[] send(byte[] data) {
		return data;
	}
	
	private void check(byte[] data) {
		
	}
	
	@Override
	public byte[] handle(ByteArrayKey args) {
		if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x00))) {
			System.out.println("Sending Transaction");
			return send(args.subSet(2, args.toByteArray().length-1)); 
		} else if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x01))) {
			System.out.println("Recieved Transaction");
			return recieve(new ByteArrayKey(args.subSet(2, args.toByteArray().length-1)));
		} else if(new ByteArrayKey(args.toByteArray()[1]).equals(new ByteArrayKey((byte) 0x02))) {
		  check(args.subSet(2, args.toByteArray().length-1));
		}
	
		return null;
	}

}
