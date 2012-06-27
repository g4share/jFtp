package com.g4share.jftp.ui.control.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.g4share.jftp.data.FileNode;

@SuppressWarnings("serial")
public class FileTreeNode extends DefaultMutableTreeNode {
	private FileNode fileNode;
	
	public FileTreeNode(FileNode fileNode){
		this.fileNode = fileNode;
	}
	
	public FileNode getFileNode(){
		return fileNode;
	}

	@Override
	public boolean isLeaf(){
		return !fileNode.isFolder();
	}
	    
	@Override
	public String toString() {
		return fileNode.getName(); 
	}
   
}
