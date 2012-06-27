package com.g4share.jftp.ui.control;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.g4share.jftp.command.Cmd;
import com.g4share.jftp.command.CmdListener;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.tree.FilePanel;

@SuppressWarnings("serial")
public class FileSystemPanel extends JPanel implements ControlsStorage {
	private Cmd cmd;
	private CmdListener fsListener = new FileSystemListener();
	
	private FilePanel localSystem;
	private FilePanel remoteSystem;
	
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
		localSystem = new FilePanel("Local System:");
		remoteSystem = new FilePanel(new NodeExpandeable(){
			@Override
			public void expand(String path){
				cmd.getFileSystem(path);
			}
		}, "Remote System:");

		add(localSystem);
		add(remoteSystem);
		add(new Label("Hello"));
		add(new Label("World"));
	}
	
	private class FileSystemListener implements CmdListener{
		@Override
		public void disconnected() {
			remoteSystem.renewTreeModel();
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
			remoteSystem.setFileSystem(path, node);
		}
	}
}
