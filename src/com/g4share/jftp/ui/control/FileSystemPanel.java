package com.g4share.jftp.ui.control;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import com.g4share.jftp.command.Cmd;
import com.g4share.jftp.command.CmdListener;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.tree.FilePanel;
import com.g4share.jftp.ui.control.tree.FileTreeNode;

@SuppressWarnings("serial")
public class FileSystemPanel extends JPanel implements ControlsStorage {
	private Cmd cmd;
	private CmdListener fsListener = new FileSystemListener();
	
	private FilePanel localSystem = new FilePanel("Local System:");
	private FilePanel remoteSystem = new FilePanel("Remote System:");
	
	public FileSystemPanel(Cmd cmd){
		this.cmd = cmd;
		this.cmd.addListener(fsListener);
		
		configure();
		addControls();
		
		fsListener.disconnected();
	}
	
	@Override
	public void configure() {
		setLayout(new GridLayout(2, 2));
		setBorder(BorderFactory.createLoweredBevelBorder());		
	}

	@Override
	public void addControls() {
		add(localSystem);
		add(remoteSystem);
		add(new Label("Hello"));
		add(new Label("World"));
	}
	
	private void addBorderedComponent(JComponent component, String caption){
		component.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(component, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createTitledBorder(caption));
		add(scrollPane);		
	}
	
	private class remoteSystemExpansionListener implements TreeExpansionListener  {
		@Override
		public void treeCollapsed(TreeExpansionEvent event) {}

		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			FileTreeNode expanedNode = (FileTreeNode)event.getPath().getLastPathComponent();
			if (expanedNode == null) return;
			cmd.getFileSystem(expanedNode.getFileNode().getName()); //TODO: calculate path 
		}		
	}
	
	private class FileSystemListener implements CmdListener{
		@Override
		public void disconnected() {
			remoteSystem.setFileSystem(null, new FileNode("/", true, 0), true);
			remoteSystem.setEnabled(false);
		}

		@Override
		public void connected(String userId) {
			if (userId == null) return;
			
			cmd.getFileSystem("/");
			remoteSystem.setEnabled(true);
		}

		@Override
		public void fileSystemGot(String path, FileNode node) {
			//DefaultMutableTreeNode treeNode = searchNode(m_searchText.getText()); 
			remoteSystem.setFileSystem(null, node, true);
		}
	}
}
