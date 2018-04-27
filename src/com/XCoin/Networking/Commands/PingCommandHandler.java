package com.XCoin.Networking.Commands;

public class PingCommandHandler extends Command{
	
	@Override
    public byte[] execute(String[] args) {
		return "pong".getBytes();
	}
}
