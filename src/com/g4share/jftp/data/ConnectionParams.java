package com.g4share.jftp.data;

public class ConnectionParams {
	private String host;
	private int port;
	private String userName;
	private String password;
	
	public ConnectionParams(String host,
			int port,
			String userName,
			String password){
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}
