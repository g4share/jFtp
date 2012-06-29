package com.g4share.jftp.ui.control.list;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.FilesStore;
import com.g4share.jftp.ui.control.FilesStoreFactory;

@SuppressWarnings("serial")
public class FilePanel extends JScrollPane {
	FileTableModel model = new FileTableModel();
	FileTable table;
	FilesStoreFactory storeFactory;
	
	public FilePanel(FilesStoreFactory storeFactory){
		super(new FileTable(storeFactory), 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		setBorder(BorderFactory.createTitledBorder(""));
		
		table = (FileTable) getViewport().getView();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setModel(model);
		this.storeFactory = storeFactory;
		//setEnabled(false);
	}
	
	public FilesStore getFilesStore(){
		return model;
	}
	
	public void setFileSystem(FileNode fileNode){
		int ct = 0;
		if (fileNode == null){
			invalidateSystems(null);
			return;
		}
		
		FileNode[] children = fileNode.getChildren();
		if (children == null || children.length == 0){
			invalidateSystems(null);
			return;
		}
		for (FileNode file : fileNode.getChildren()){
			if (!file.isFolder()) ct++;
		}
		
		int i = 0;
		FileNode[] files = new FileNode[ct];
		for (FileNode file : fileNode.getChildren()){
			if (!file.isFolder()) files[i++] = file;
		}
				
		
		invalidateSystems(files);		
	}

	private void invalidateSystems(FileNode[] files ){
		model.setFileNodes(files);
		
		if (storeFactory == null) return;
		FilesStore filesStore = storeFactory.get();
		if (filesStore == null) return;
		filesStore.refresh();
		
	}
}
