package com.g4share.jftp.command;

public interface CmdListener {
	public void disconnected();
	public void connected(String userId);
}
