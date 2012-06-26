package com.g4share.jftp.command;

import java.util.ArrayList;
import java.util.List;

import com.g4share.jftp.data.ConnectionParams;

public class FakedCmd implements Cmd {
	List<ConnectionParams> validConnections = new ArrayList<ConnectionParams>();	
	private List<CmdListener> listeners = new ArrayList<CmdListener>();
	
	private String connectedUser = null;
	
	public FakedCmd(){
		validConnections.add(new ConnectionParams("localhost", 21, "gm", "gh"));
		validConnections.add(new ConnectionParams("localhost", 21, "sergiu", "glavatchi"));
	}
	
	public void addListener(CmdListener listener){
		listeners.add(listener);
	}
	
	@Override
	public void connect(ConnectionParams params){
		disconnect();
    
		for(ConnectionParams param : validConnections){
			if (param.getHost().equals(params.getHost())
					&& param.getPort() == params.getPort()
					&& param.getUserName().equals(params.getUserName())
					&& param.getPassword().equals(params.getPassword())){

				connectedUser = params.getUserName();
				break;
			}
		}
    	
		for(CmdListener listener : listeners){
			listener.connected(connectedUser);
		}				
		return;				
	}

	@Override
	public void disconnect() {
		connectedUser = null;
		for(CmdListener listener : listeners){
			listener.disconnected();
		}
	}
}
