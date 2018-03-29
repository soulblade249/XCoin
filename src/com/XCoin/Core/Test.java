package com.XCoin.Core;
import java.io.IOException;
import java.security.Security;

import com.XCoin.Networking.Peer;
import com.XCoin.Networking.Peer2Peer;

public class Test {
	public static void main(String [] args) throws IOException {
		
		BlockChain bc = new BlockChain();
		bc.mine();
		
	}
}
