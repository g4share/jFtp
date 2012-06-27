package com.g4share.jftp.ui.control.tree;


import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.NodeExpandeable;

@SuppressWarnings("serial")
public class FilePanel extends JScrollPane {
	private NodeExpandeable nodeExpand;	
	
	private JTree tree = null; 

	public FilePanel(String caption){
		super(new JTree(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		setBorder(BorderFactory.createTitledBorder(caption));
		
		tree = (JTree) getViewport().getView();
		renewTreeModel();
		setEnabled(false);
	}
	
	public void renewTreeModel(){		
		tree.setModel(
				new DefaultTreeModel(
						new FileTreeNode(
								new FileNode("/", true, 0))));
		
		tree.collapsePath(new TreePath(tree.getModel().getRoot()));
	}
	
	public FilePanel(NodeExpandeable nodeExpand, String caption){		
		this(caption);
		
		this.nodeExpand = nodeExpand;
		if (nodeExpand != null) 
			tree.addTreeExpansionListener(new SystemExpansionListener());
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
		if (!path.startsWith("/")) return null;
		
		FileTreeNode root = (FileTreeNode) tree.getModel().getRoot(); 
		if (path.equals("/")) {
			return root;
		}
		
		String[] parts = path.split("/");
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
	
	private class SystemExpansionListener implements TreeExpansionListener  {
		@Override
		public void treeCollapsed(TreeExpansionEvent event) {}

		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			FileTreeNode currentNode = (FileTreeNode)event.getPath().getLastPathComponent();
			if (currentNode == null) return; 	
			
			StringBuilder sb = new StringBuilder(currentNode.getFileNode().getName());
			while (true) {
				FileTreeNode tempNode = (FileTreeNode) currentNode.getParent();
				if (tempNode == null) break;
				sb.insert(0, "/").insert(0, tempNode.getFileNode().getName());
				currentNode = tempNode;
			}

			//if not first node is expanded
			if (sb.length() > 1) sb.delete(0, 1);
			nodeExpand.expand(sb.toString()); 
		}		
	}
}
