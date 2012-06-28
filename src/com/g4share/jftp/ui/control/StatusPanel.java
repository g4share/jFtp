package com.g4share.jftp.ui.control;

import java.awt.FlowLayout;
import java.awt.Label;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.g4share.jftp.command.CmdObserver;
import com.g4share.jftp.command.CmdProxy;
import com.g4share.jftp.data.FileNode;


@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements ControlsStorage {
	private Label lbStatus = new Label();
	
	public StatusPanel(CmdProxy cmdProxy){
		cmdProxy.addObserver(new StatusObserver());
		
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
	

	private class StatusObserver implements CmdObserver{
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
