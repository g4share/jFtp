package com.g4share.jftp.command;


import com.g4share.jftp.command.fs.FileSystemStore;
import com.g4share.jftp.data.ConnectionParams;
import com.g4share.jftp.data.FileNode;

public interface Cmd extends FileSystemStore {	
	public String getConnectedUser();
	public String connect(ConnectionParams params);
	public void disconnect();
	
	@Override
	public FileNode getFileSystem(String path);
}
