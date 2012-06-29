package com.g4share.jftp.ui.control;

import com.g4share.jftp.data.FileNode;

public interface FilesStore {
	public FileNode searchFileNode(FileNode node2Search);
	public void refresh();
}
