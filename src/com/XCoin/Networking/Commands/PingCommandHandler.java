package com.XCoin.Networking.Commands;

public class PingCommandHandler extends Command{
	
	@Override
    public String execute(String[] args) {
		return "pong";
	}
}
