package com.g4share.jftp.command.fs;

import java.util.ArrayList;
import java.util.List;
import com.g4share.jftp.data.FileNode;

public abstract class FSObservable<T extends FSObserver> {
	protected List<T> observers = new ArrayList<T>();
	
	public final void addObserver(T observer){
		observers.add(observer);
	}
	
	public final void deleteObserver(T observer){
		observers.add(observer);
	}
	
	public final void deleteObservers(){
		observers.clear();
	}

	public final int countObservers(){
		return observers.size();
	}

	public void notifyFileSystemGot(String path, FileNode node) {
		for (FSObserver observer : observers){
			observer.fileSystemGot(path, node);
		}
	}
}
