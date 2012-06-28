package com.g4share.jftp.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.g4share.jftp.data.ConnectionParams;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.utils.Utils;

public class FakedCmd implements Cmd {
	private String connectedUser = null;
	
	List<ConnectionParams> validConnections = new ArrayList<ConnectionParams>();		
	private Map<String, Set<FileNode>> files = new HashMap<String, Set<FileNode>>();
	
	public FakedCmd(){
		validConnections.add(new ConnectionParams("localhost", 21, "gm", "gh"));
		validConnections.add(new ConnectionParams("127.0.0.1", 21, "gm", "gh"));
		validConnections.add(new ConnectionParams("localhost", 21, "sergiu", "glavatchi"));
		validConnections.add(new ConnectionParams("127.0.0.1", 21, "sergiu", "glavatchi"));
		
		Set<FileNode> nodes;
		
		nodes = new HashSet<FileNode>();
		nodes.add(new FileNode("fld1", true, 0));
		nodes.add(new FileNode("fld2", true, 0));
		nodes.add(new FileNode("fld3", true, 0));
		nodes.add(new FileNode("file", false, 20));
		nodes.add(new FileNode("data.xml", false, 373));
		nodes.add(new FileNode("jFtp.zip", false, 400));
		files.put("/", nodes);
		
		nodes = new HashSet<FileNode>();
		nodes.add(new FileNode("fld21", true, 0));
		nodes.add(new FileNode("file21", false, 30));
		nodes.add(new FileNode("file22", false, 40));
		files.put("/fld2", nodes);

		nodes = new HashSet<FileNode>();
		nodes.add(new FileNode("fld211", true, 0));
		files.put("/fld2/fld21", nodes);
}
	
	@Override
	public String getConnectedUser(){
		return connectedUser;
	}
	
	@Override
	public String connect(ConnectionParams params){
		disconnect();
    
		for(ConnectionParams param : validConnections){
			if (param.getHost().equals(params.getHost())
					&& param.getPort() == params.getPort()
					&& param.getUserName().equals(params.getUserName())
					&& param.getPassword().equals(params.getPassword())){

				connectedUser = params.getUserName();
				break;
			}
		}
    	
		return connectedUser;				
	}

	@Override
	public void disconnect() {
		connectedUser = null;
	}

	@Override
	public FileNode getFileSystem(String path) {
		if (connectedUser == null) return null;
		
		if (!files.containsKey(path)) return null;
		
		String fileName = path;
		if (!path.equals(Utils.ROOT_FOLDER)){
			String[] fileParts = path.split(Utils.ROOT_FOLDER);
			fileName = fileParts[fileParts.length - 1];
		}

		FileNode root = new FileNode(fileName, true, 0);
		for(FileNode child : files.get(path)){
			root.addChild(child);
		}
		 
		return root;
	}
}
