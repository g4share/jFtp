package com.g4share.jftp.command;


import com.g4share.jftp.data.ConnectionParams;

public interface Cmd {
	public void addListener(CmdListener listener);
	
	public String getConnectedUser();
	public void connect(ConnectionParams params);
	public void disconnect();
	
	public void getFileSystem(String path);
}
