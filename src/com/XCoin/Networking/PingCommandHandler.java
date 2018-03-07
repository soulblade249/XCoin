package com.XCoin.Networking;

class PingCommandHandler extends Command{
	
	@Override
    public String execute(String[] args) {
		return "pong";
	}
}
