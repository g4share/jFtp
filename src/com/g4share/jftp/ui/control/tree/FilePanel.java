package com.g4share.jftp.ui.control.tree;


import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.g4share.jftp.data.FileNode;

@SuppressWarnings("serial")
public class FilePanel extends JScrollPane {
	
	private JTree tree = null; 
	
	public FilePanel(String caption){
		super(new JTree(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		setBorder(BorderFactory.createTitledBorder(caption));
		
		tree = (JTree) getViewport().getView();
	}
	
	public void setFileSystem(DefaultMutableTreeNode parentTreeNode, FileNode fileNode, boolean remote){
		TreeNode treeNode = createNodeTree(fileNode);
		if (parentTreeNode == null){
			tree.setModel(new DefaultTreeModel(treeNode));
		}
		parentTreeNode.add(new FileTreeNode(fileNode));
	    if (fileNode.getChildren() == null){
	    	tree.collapsePath(new TreePath(treeNode));
	    }	    
	}
	
	private TreeNode createNodeTree(FileNode node) {
		DefaultMutableTreeNode root = new FileTreeNode(node);
        FileNode[] children = node.getChildren();
        if (children != null){
        	for(FileNode child : children){
        		if (!child.isFolder()) continue;
        		root.add(new FileTreeNode(child));
        	}
        }
        return root;
	}
}
