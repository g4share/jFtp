package com.g4share.jftp.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileNode implements Comparable<FileNode> {
	private String name;
	private long size;
	private boolean isFolder;
	private final List<FileNode> children = new ArrayList<FileNode>();
	
	public FileNode(String name, boolean isFolder, long size){
		this.name = name;
		this.isFolder = isFolder;
		this.size = size;
	}
	
	public void addChild(FileNode child){
		children.add(child);
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isFolder(){
		return isFolder;
	}

	public long getSize(){
		return size;
	}
	
	public FileNode[] getChildren(){
		if (children.size() == 0) return null;
		FileNode[] nodes = (FileNode[]) children.toArray(new FileNode[0]);
		Arrays.sort(nodes);
		return nodes;
	}

	@Override
	public int compareTo(FileNode compareTo) {
		if (isFolder != compareTo.isFolder) return isFolder ? -1 : 1;
		return name.compareTo(compareTo.name);
	}
}
