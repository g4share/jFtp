package com.g4share.jftp.command;

public abstract class CmdProxy extends CmdObservable implements Cmd {
	protected Cmd cmd;
	protected String connectedUser;
	
	public CmdProxy(Cmd cmd){
		this.cmd = cmd;
	}
}
