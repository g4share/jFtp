package com.g4share.jftp.ui.control;

import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.g4share.jftp.command.Cmd;
import com.g4share.jftp.command.CmdListener;
import com.g4share.jftp.data.FileNode;


@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements ControlsStorage {
	private Label lbStatus = new Label();
	
	public StatusPanel(Cmd cmd){
		cmd.addListener(new StatusListener());
		
		configure();
		addControls();		
	}
	
	@Override
	public void configure() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void addControls() {
		add(new Label("Status: "));
		add(lbStatus);
	}
	

	private class StatusListener implements CmdListener{
		@Override
		public void disconnected() {
			lbStatus.setText("disconected");
			lbStatus.revalidate();
		}

		@Override
		public void connected(String userId) {
			lbStatus.setText(userId == null
					? "disconected - connection refused."
					: "connected (" + userId + ")");
			
			lbStatus.revalidate();
		}

		@Override
		public void fileSystemGot(String path, FileNode node) {}		
	}
}
