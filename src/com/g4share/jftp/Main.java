package com.g4share.jftp;

import java.lang.reflect.InvocationTargetException;
import javax.swing.UIManager;
import com.g4share.jftp.command.SynchCmdProxy;
import com.g4share.jftp.command.FakedCmd;
import com.g4share.jftp.ui.MainForm;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;

public class Main {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {	
		try{
			PlasticXPLookAndFeel.setCurrentTheme(new DesertBlue());
			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
		}catch(Exception e){
			e.printStackTrace();
		}
		new MainForm(new SynchCmdProxy(new FakedCmd())).showIt();
	}
}
