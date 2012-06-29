package com.g4share.jftp.command;

import com.g4share.jftp.command.fs.FSObservable;
import com.g4share.jftp.data.FileNode;

public abstract class CmdObservable extends FSObservable<CmdObserver> {
	public void notifyDisconnected(){
		for (CmdObserver observer : observers){
			observer.disconnected();
		}
	}

	public void notifyConnected(String userId) {
		for (CmdObserver observer : observers){
			observer.connected(userId);
		}
	}

	public void notifyFileSystemGot(String path, FileNode node) {
		for (CmdObserver observer : observers){
			observer.fileSystemGot(path, node);
		}
	}
}
