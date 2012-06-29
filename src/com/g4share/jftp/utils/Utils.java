package com.g4share.jftp.utils;

import java.awt.Container;
import javax.swing.JOptionPane;

public class Utils {
	public final static String ROOT_FOLDER = "/";
	
	public static void showDialog(Container container, int options, String title, String mesage){
		JOptionPane.showMessageDialog(container,
			    mesage,
			    title,
			    options);
	}
}
