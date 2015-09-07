package uppgift.b;

import java.io.*;

public class Messages implements Serializable{
	static final int loggIn = 0, loggOut = 1, messages = 2;
	private int type;
	private String message;
	
	public Messages(int type, String message){
		this.type = type;
		this.message = message;
	}
	
	public int getType(){
		return type;
	}
	
	public String getMessage(){
		return message;
	}
}
