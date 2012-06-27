package com.g4share.jftp.command;

import com.g4share.jftp.data.FileNode;

public interface CmdListener {
	public void disconnected();
	public void connected(String userId);
	
	public void fileSystemGot(String path, FileNode node);
}
