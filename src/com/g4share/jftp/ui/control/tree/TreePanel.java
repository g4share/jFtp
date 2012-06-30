package com.g4share.jftp.ui.control.tree;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.g4share.jftp.command.fs.FileSystemStore;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.utils.Utils;

@SuppressWarnings("serial")
public class TreePanel extends JScrollPane {
	private FileSystemStore fsStore;
	
	private JTree tree = null; 

	public TreePanel(String caption){
		super(new JTree(), 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		setBorder(BorderFactory.createTitledBorder(caption));
		
		tree = (JTree) getViewport().getView();
		renewTreeModel();
		setEnabled(false);
	}
	
	public void renewTreeModel(){		
		tree.setModel(
				new DefaultTreeModel(
						new FileTreeNode(
								new FileNode(Utils.ROOT_FOLDER, true, 0))));
		
		tree.collapsePath(new TreePath(tree.getModel().getRoot()));
	}
	
	public TreePanel(FileSystemStore fsStore, String caption){		
		this(caption);
		
		this.fsStore = fsStore;
		if (fsStore != null) {
			tree.addTreeSelectionListener(new SystemSelectionListener());
		}
	}
	
	public void setFileSystem(String path, FileNode fileNode){
		FileTreeNode parentNode = searchNode(path);
		if (parentNode == null || fileNode == null) return;
		
		parentNode.removeAllChildren();
		addChildrenToParent(parentNode, fileNode);
		
	    ((DefaultTreeModel)tree.getModel()).reload(parentNode);
	}
	

	private FileTreeNode searchNode(String path) {
		if (path == null) return null;
		if (!path.startsWith(Utils.ROOT_FOLDER)) return null;
		
		FileTreeNode root = (FileTreeNode) tree.getModel().getRoot(); 
		if (path.equals(Utils.ROOT_FOLDER)) {
			return root;
		}
		
		String[] parts = path.split(Utils.ROOT_FOLDER);
		//i = 1 because thirst should be empty (path starts with "/")
		for (int i = 1; i < parts.length; i++){
			FileTreeNode node = searchNode(root, parts[i]);
			if (node == null) return null;
			root = node;
		}
		return root;
	}
	
	private FileTreeNode searchNode(FileTreeNode parent, String childName){
		for(int i = 0; i < parent.getChildCount(); i++){
			FileTreeNode node = (FileTreeNode)parent.getChildAt(i);
			if (node.getFileNode().getName().equals(childName)) return node;
		}
		
		return null;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		if (tree == null) return;
		tree.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled(){	
		if (tree == null) return false;
		return tree.isEnabled();
	}

	private void addChildrenToParent(FileTreeNode parentNode, FileNode node) {
        FileNode[] children = node.getChildren();
        if (children != null){
        	for(FileNode child : children){
        		if (!child.isFolder()) continue;
        		parentNode.add(new FileTreeNode(child));
        	}
        }
	}
	
	private class SystemSelectionListener implements TreeSelectionListener {
		@Override
	    public void valueChanged(TreeSelectionEvent e) {
	    	FileTreeNode currentNode = (FileTreeNode)tree.getLastSelectedPathComponent();
	        if (currentNode == null) return;
	        
			StringBuilder sb = new StringBuilder(currentNode.getFileNode().getName());
			while (true) {
				FileTreeNode tempNode = (FileTreeNode) currentNode.getParent();
				if (tempNode == null) break;
				
				sb.insert(0, Utils.ROOT_FOLDER).insert(0, tempNode.getFileNode().getName());
				currentNode = tempNode;
			}

			//if not first node is expanded
			if (sb.length() > 1) sb.delete(0, 1);
			fsStore.getFileSystem(sb.toString());
	    }
	}
}
