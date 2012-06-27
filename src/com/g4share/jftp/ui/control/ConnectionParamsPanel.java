package com.g4share.jftp.ui.control;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.g4share.jftp.command.Cmd;
import com.g4share.jftp.command.CmdListener;
import com.g4share.jftp.data.ConnectionParams;
import com.g4share.jftp.data.FileNode;
import com.g4share.jftp.utils.UIUtils;

@SuppressWarnings("serial")
public class ConnectionParamsPanel  extends JPanel implements ControlsStorage {
	private Cmd cmd;
	
	private JTextField tfHost;
	private JTextField tfPort;
	private JTextField tfUser;
	private JPasswordField tfPassword;
	private JButton btConnect;
	
	public ConnectionParamsPanel(Cmd cmd){
		this.cmd = cmd;
		this.cmd.addListener(new ConnectionListener());
		
		configure();
		addControls();
	}
	
	@Override
	public void configure() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder("Connection details"));		
	}

	@Override
	public void addControls() {		
		add(new Label("Host: "));
		tfHost = new JTextField("localhost", 10);
		add(tfHost);

		add(new Label("Port: "));
		tfPort = new JTextField("21", 2);
		add(tfPort);

		add(new Label("User: "));
		tfUser = new JTextField("gm", 10);
		add(tfUser);

		
		tfPassword = new JPasswordField("gh", 10);
		add(tfPassword);
		
		btConnect = new JButton("Connect");
		btConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (cmd.getConnectedUser() != null){
					cmd.disconnect();
					return;
				}
				int port;
				try{
					port = Integer.parseInt(tfPort.getText());					
				}
			    catch (NumberFormatException nfe) {
			    	UIUtils.showDialog(ConnectionParamsPanel.this.getParent(),
			    			JOptionPane.ERROR_MESSAGE, 
			    			"Parse error", 
			    			"Port parsing error.");
			    	
			    	tfPort.setText("21");
			    	tfPort.requestFocusInWindow();
			    	return;
			    }
								
				ConnectionParams params = new ConnectionParams(
						tfHost.getText(),
						port, 
						tfUser.getText(), 
						new String(tfPassword.getPassword()));
				
				cmd.connect(params);
			}
	       });
        add(btConnect);
	}
	
	private class ConnectionListener implements CmdListener{
		@Override
		public void disconnected() {
			tfHost.setEnabled(true);
			tfPort.setEnabled(true);
			tfUser.setEnabled(true);
			tfPassword.setEnabled(true);
			btConnect.setText("Connect");
		}

		@Override
		public void connected(String userId) {
			if (userId == null) return;
			
			tfHost.setEnabled(false);
			tfPort.setEnabled(false);
			tfUser.setEnabled(false);
			tfPassword.setText("");
			tfPassword.setEnabled(false);
			btConnect.setText("Disconnect");
		}

		@Override
		public void fileSystemGot(String path, FileNode node) {}		
	}
}
