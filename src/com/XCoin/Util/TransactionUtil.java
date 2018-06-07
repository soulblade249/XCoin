package com.XCoin.Util;

import java.util.ArrayList;

import com.XCoin.Core.Wallet;

public class TransactionUtil {

	public static int getIndex(byte[] data, Byte find, int prevIndex) {
		for(int a = prevIndex; a < data.length; a++) {
			Byte b = data[a];
			if(b.equals(find)) {
				return a;
			}
		}
		return 0;
	}
	
	public static ArrayList<String> extrapolateCodes(String data) {
		String[] test = new String[data.length()];
		ArrayList<String> toReturn = new ArrayList<String>();
		test = data.split("");
		for(String c : test) {
			if(!c.contains("[0-9]+")) {
				toReturn.add(c);
			}
		}
		return toReturn;
	}
	
	public static ArrayList<String> extrapolateNums(String data) {
		String[] test = new String[data.length()];
		ArrayList<String> toReturn = new ArrayList<String>();
		test = data.split("");
		for(String c : test) {
			if(c.contains("[0-9]+")) {
				toReturn.add(c);
			}
		}
		return toReturn;
	}
	
	public static boolean hasBalance(Wallet w) {
		
	}
	
}
