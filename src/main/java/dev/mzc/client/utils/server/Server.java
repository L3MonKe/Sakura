package dev.mzc.client.utils.server;

public enum Server {
	HYPIXEL("hypixel"), MINEMEN("minemen");
	
	private final String address;
	
	private Server(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}
