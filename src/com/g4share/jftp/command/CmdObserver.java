package com.g4share.jftp.command;

import com.g4share.jftp.command.fs.FSObserver;
import com.g4share.jftp.data.FileNode;

public interface CmdObserver extends FSObserver {
	public void disconnected();
	public void connected(String userId);
	
	@Override
	public void fileSystemGot(String path, FileNode node);
}
