package com.g4share.jftp.ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.g4share.jftp.command.Cmd;
import com.g4share.jftp.ui.control.ConnectionParamsPanel;
import com.g4share.jftp.ui.control.FileSystemPanel;
import com.g4share.jftp.ui.control.StatusPanel;

@SuppressWarnings("serial")
public final class MainForm extends JCommonForm {
	private StatusPanel statusPanel;
	private ConnectionParamsPanel connectionParamsPanel;
	private FileSystemPanel fileSystemPanel;
	
	public MainForm(Cmd cmd){			
		connectionParamsPanel = new ConnectionParamsPanel(cmd);
		fileSystemPanel = new FileSystemPanel(cmd);
		statusPanel = new StatusPanel(cmd);
	}

	@Override
	public void configure() {
		setAlwaysOnTop(true);
		setTitle("java ftp client.");
		setBounds(new Rectangle(10, 10, 800, 600));        
	}

	@Override
	public void addControls() {
		JPanel basic = new JPanel();
        basic.setLayout(new BorderLayout());
        add(basic);
        
        basic.add(connectionParamsPanel, BorderLayout.NORTH);
        basic.add(fileSystemPanel, BorderLayout.CENTER);
        basic.add(statusPanel, BorderLayout.SOUTH);
	}
}
