package com.g4share.jftp.ui;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.g4share.jftp.ui.control.ControlsStorage;

@SuppressWarnings("serial")
public abstract class JCommonForm extends JFrame implements ControlsStorage {
	public abstract void configure();
	public abstract void addControls();
	
	public JCommonForm showIt() throws InvocationTargetException, InterruptedException{
		configure();
		addControls();
		
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				setVisible(true);				
			}
		});
		return this;
	}
}
