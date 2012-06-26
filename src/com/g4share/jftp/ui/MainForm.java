package com.g4share.jftp.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.g4share.jftp.command.Cmd;
import com.g4share.jftp.command.CmdListener;
import com.g4share.jftp.ui.control.ConnectionParamsPanel;
import com.g4share.jftp.ui.control.StatusPanel;

@SuppressWarnings("serial")
public final class MainForm extends JCommonForm {
	private StatusPanel statusPanel;
	private ConnectionParamsPanel connectionParamsPanel;
	
	public MainForm(Cmd cmd){
		cmd.addListener(new FtpCommandListener());
		statusPanel = new StatusPanel();
		connectionParamsPanel = new ConnectionParamsPanel(cmd);
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
        basic.add(getOtherPanel());        
        basic.add(statusPanel, BorderLayout.SOUTH);
	}
	
	private Component getOtherPanel() {
		JPanel panel = new JPanel();
		return panel;
	}
	
	private class FtpCommandListener implements CmdListener{
		@Override
		public void disconnected() {
			statusPanel.setStatus("disconected");
			connectionParamsPanel.disconnected();
		}

		@Override
		public void connected(String userId) {
			if (userId == null){
				statusPanel.setStatus("disconected - connection refused.");
				return;
			}
			statusPanel.setStatus("connected (" + userId + ")");		
			connectionParamsPanel.connected();
		}		
	}
}
