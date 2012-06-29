package com.g4share.jftp.ui.control.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DropMode;
import javax.swing.JTable;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.FilesStore;
import com.g4share.jftp.ui.control.FilesStoreFactory;

@SuppressWarnings("serial")
public class FileTable extends JTable {
	private Border currentRowBorder = BorderFactory.createLineBorder(Color.BLUE, 1);

	private FilesStoreFactory storeFactory;
	
	public FileTable(FilesStoreFactory storeFactory){
		this.storeFactory = storeFactory;
		
		setDragEnabled(true);
		setDropMode(DropMode.INSERT_ROWS); 
	}
	
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		JComponent c = (JComponent)super.prepareRenderer(renderer, row, col);
		c.setForeground(Color.BLACK);
		
		if (isCellSelected(row, col))
			c.setBorder(currentRowBorder);

		if (storeFactory == null
			|| !(getModel() instanceof FileTableModel)) return c;
		
		FilesStore filesStore = storeFactory.get();
		if (filesStore == null) {
			c.setBackground(Color.GREEN);
			return c;
		}
		
		FileTableModel model = (FileTableModel)getModel();
		int modelRow = convertRowIndexToModel(row);
		FileNode currentNode = model.getNode(modelRow);
		if (currentNode == null) {
			c.setBackground(Color.GREEN);
			return c;
		}
		
		FileNode otherNode = filesStore.searchFileNode(currentNode);
		if (otherNode == null){
			c.setBackground(Color.WHITE);
			return c;
		}
		
		if (!currentNode.equals(otherNode)){
			c.setBackground(Color.RED);
			return c;
		}	
		c.setBackground(Color.GREEN);
        return c;  
    }
}
