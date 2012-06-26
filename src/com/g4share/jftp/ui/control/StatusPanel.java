package com.g4share.jftp.ui.control;

import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements ControlsStorage {
	private Label lbStatus = new Label();
	
	public StatusPanel(){
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
	
	public void setStatus(String statusText){
		lbStatus.setText(statusText);
		lbStatus.revalidate();
	}
}
