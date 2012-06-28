package com.g4share.jftp.ui.control;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.g4share.jftp.command.CmdObserver;
import com.g4share.jftp.command.CmdProxy;
import com.g4share.jftp.command.fs.FSObserver;
import com.g4share.jftp.command.fs.LocalFSSystemStore;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.ui.control.list.FilePanel;
import com.g4share.jftp.ui.control.tree.TreePanel;
import com.g4share.jftp.utils.Utils;

@SuppressWarnings("serial")
public class FileSystemPanel extends JPanel implements ControlsStorage {
	private CmdProxy cmdProxy;
	private LocalFSSystemStore localSystemStore = new LocalFSSystemStore();
	
	private CmdObserver remoteObserver = new RemoteSystemObserver();
	
	private TreePanel localSystemTree;
	private TreePanel remoteSystemTree;
	
	private FilePanel localSystemPanel;
	private FilePanel remoteSystemPanel;

	public FileSystemPanel(CmdProxy cmdProxy){
		this.cmdProxy = cmdProxy;
		this.cmdProxy.addObserver(remoteObserver);
		this.localSystemStore.addObserver(new LocalSystemObserver());
		
		configure();
		addControls();
		
		remoteObserver.disconnected();
	}
	
	@Override
	public void configure() {
		setLayout(new GridLayout(2, 2));
		setBorder(BorderFactory.createLoweredBevelBorder());		
	}

	@Override
	public void addControls() {
		localSystemTree = new TreePanel(localSystemStore, "Local System:");
		remoteSystemTree = new TreePanel(cmdProxy, "Remote System:");
		add(localSystemTree);
		add(remoteSystemTree);

		localSystemPanel = new FilePanel(new FilesStoreFactory() {			
			@Override
			public FilesStore get() {
				return remoteSystemPanel.getFilesStore();
			}
		});
		remoteSystemPanel = new FilePanel(new FilesStoreFactory() {			
			@Override
			public FilesStore get() {
				return localSystemPanel.getFilesStore();
			}
		});
		
		add(localSystemPanel);
		add(remoteSystemPanel);

		cmdProxy.getFileSystem(Utils.ROOT_FOLDER);
		
		localSystemTree.setEnabled(true);
	}
	
	private class LocalSystemObserver implements FSObserver {
		@Override
		public void fileSystemGot(String path, FileNode node) { 
			localSystemTree.setFileSystem(path, node);
			localSystemPanel.setFileSystem(node);
		}
	}
	
	private class RemoteSystemObserver implements CmdObserver{
		@Override
		public void disconnected() {
			remoteSystemTree.renewTreeModel();
			remoteSystemTree.setEnabled(false);
		}

		@Override
		public void connected(String userId) {
			if (userId == null) return;
			
			cmdProxy.getFileSystem(Utils.ROOT_FOLDER);
			remoteSystemTree.setEnabled(true);
		}

		@Override
		public void fileSystemGot(String path, FileNode node) { 
			remoteSystemTree.setFileSystem(path, node);
			remoteSystemPanel.setFileSystem(node);
		}
	}
}
