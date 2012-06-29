package com.g4share.jftp.ui.control.list;

import javax.swing.table.AbstractTableModel;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.FilesStore;

@SuppressWarnings("serial")
public class FileTableModel extends AbstractTableModel implements FilesStore {
	final String[] columnNames = {
			"Name",
            "Size"};

	private FileNode[] files;
	
	public void setFileNodes(FileNode[] files){
		this.files = files;			
		fireTableDataChanged();
	}

	public FileNode getNode(int index){
		if (files == null 
				|| index >= files.length) return null;
		
		return files[index];
	}

	@Override
	public FileNode searchFileNode(FileNode node2Search){
		if (files == null || files.length == 0) return null;
		for (FileNode node : files){
			if (node.getName().equals(node2Search.getName())) 
				return node; 
		}
		return null;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
      
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return files == null ? 0 : files.length;
	}

	@Override
	public String getValueAt(int row, int column) {
		if (row > getRowCount()) return null;
		FileNode currentNode = files[row];
		return column == 0 ? currentNode.getName() : String.valueOf(currentNode.getSize());
	}

	@Override
	public void refresh() {
		fireTableDataChanged();
	}
}