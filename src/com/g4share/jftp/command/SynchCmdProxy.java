package com.g4share.jftp.command;

import com.g4share.jftp.data.ConnectionParams;
import com.g4share.jftp.data.FileNode;

public class SynchCmdProxy extends CmdProxy{
	public SynchCmdProxy(Cmd cmd){
		super(cmd);
	}

	@Override
	public String getConnectedUser() {
		return connectedUser; 
	}

	@Override
	public String connect(ConnectionParams params) {
		connectedUser = cmd.connect(params);	
		notifyConnected(connectedUser);
		return connectedUser;
	}

	@Override
	public void disconnect() {
		connectedUser = null;
		notifyDisconnected();		
	}

	@Override
	public FileNode getFileSystem(String path) {
		if (connectedUser == null) return null;
		
		FileNode node = cmd.getFileSystem(path);
		notifyFileSystemGot(path, node);
		return node;
	}
}
