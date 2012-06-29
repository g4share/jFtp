package com.g4share.jftp.command.fs;

import com.g4share.jftp.data.FileNode;

public interface FSObserver {
	public void fileSystemGot(String path, FileNode node);
}
