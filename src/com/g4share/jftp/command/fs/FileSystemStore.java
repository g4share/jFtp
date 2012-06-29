package com.g4share.jftp.command.fs;

import com.g4share.jftp.data.FileNode;

public interface FileSystemStore {
	public FileNode getFileSystem(String path);
}