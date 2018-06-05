package com.XCoin.Util;

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
	
}
