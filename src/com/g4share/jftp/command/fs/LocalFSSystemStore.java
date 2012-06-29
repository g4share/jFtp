package com.g4share.jftp.command.fs;

import java.io.File;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.utils.Utils;

public class LocalFSSystemStore extends FSObservable<FSObserver> implements FileSystemStore {
	@Override
	public FileNode getFileSystem(String path) {
		FileNode node = path.equals(Utils.ROOT_FOLDER) && isWindows()
				? getRoots()
				: getFolder(path);
				
		notifyFileSystemGot(path, node);
		return node;
	}
	
	private static boolean isWindows() {		 
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
	
	private FileNode getFolder(String path){
		FileNode node = new FileNode(path, true, 0);
		
	    File[] children = new File(path).listFiles();
	    if (children != null) {
	        for (File child : children) {
	        	node.addChild(new FileNode(child.getName(), child.isDirectory(), child.length()));
	        }
	    }
				
		return node;		
	}
	
	private FileNode getRoots(){
		FileNode rootNode = new FileNode(Utils.ROOT_FOLDER, true, 0);
		File[] roots = File.listRoots();
		for(int i = 0; i< roots.length;i++){
			rootNode.addChild(new FileNode(roots[i].toString(), true, 0));
		}
		
		return rootNode;
	}
}
