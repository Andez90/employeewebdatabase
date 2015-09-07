package uppgift.c;

import java.io.*;
import java.net.*;

public class Server extends Thread{
	private GUI gui;
	private int port;
	private String cataloge;

	public Server(int port, String cataloge, GUI gui) {
		this.gui = gui;
		this.port = port;
		this.cataloge = cataloge;
		this.start();
	}
	
	private synchronized void display(String messageToAppend){
		gui.appendServer(messageToAppend);
	}
	
	private synchronized void displayHttp(String messageToAppend){
		gui.appendHttp(messageToAppend);
	}
	
	public void run(){
		ServerSocket ss = null;
		try{
			ss = new ServerSocket(port);
			display("Server started listening to port: " + port + "\n");
		}
		catch(Exception e){
			display("Error binding to server!\n");
		}
		display("Ready and waiting for requests!\n");
		while(true){
			try{
				Socket connection = ss.accept();
				InetAddress client = connection.getInetAddress();
				display(client.getHostName() + " connected to server!\n");
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader input = new BufferedReader(isr);
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());
				httpHandler(input, output);
			}
			catch(Exception e){
				display("Error\n");
			}
		}
	}
	
	private void httpHandler(BufferedReader input, DataOutputStream output){
		int method = 0;
		String http = new String();
		String path = new String();
		String file = new String();
		String user = new String();
		try{
			String temp = input.readLine();
			String temp2 = temp;
			temp.toUpperCase();
			if(temp.startsWith("GET")){
				displayHttp(temp2 + "\n");
				method = 1;
			}
			if(temp.startsWith("HEAD")){
				displayHttp(temp2 + "\n");
				method = 2;
			}
			if(method == 0){
				try{
					displayHttp(temp2 + "\n");
					output.writeBytes(httpHeade(501, 0));
					output.close();
					return;
				}
				catch(Exception e){
					display("Error not suporting sending!\n");
				}
			}
			int begin = 0;
			int end = 0;
			for(int a = 0; a < temp2.length(); a++){
				if(temp2.charAt(a) == ' ' && begin != 0){
					end = a;
					break;
				}
				if(temp2.charAt(a) == ' ' && begin == 0){
					begin = a;
				}
			}
			path = cataloge + temp2.substring(begin+2, end);
		}
		catch(Exception e){
			display("Error suporting sending!\n");
		}
		display("Client requested: " + path + "\n");
		FileInputStream requestedFile = null;
		try{
			requestedFile = new FileInputStream(path);
		}
		catch(Exception e){
			display("Error open file\n");
		}
		try{
			int type = 0;
			if(path.endsWith(".jpg") || path.endsWith(".jpeg")){
				type = 1;
			}
			if(path.endsWith(".gif")){
				type = 2;
			}
			if(path.endsWith(".zip")){
				type = 3;
			}
			output.writeBytes(httpHeade(200, 5));
			if(method == 1){
				while(true){
					int read = requestedFile.read();
					if(read == -1){
						break;
					}
					output.write(read);
				}
			}
			output.close();
			requestedFile.close();
		}
		catch(Exception e){
			display("Errors!");
		}
	}
	
	private String httpHeade(int returnCode, int fileType){
		String code = "HTTP/1.0 ";
		switch(returnCode){
		case 200:
			code = code + "200 OK";
			break;
		case 400:
			code = code + "400 Bad Request";
			break;
		case 403:
			code = code + "403 Forbidden";
			break;
		case 404:
			code = code + "404 Not Found";
			break;
		case 500:
			code = code + "500 Internal Server Error";
			break;
		case 501:
			code = code + "501 Not Implemented";
			break;
		}
		code = code + "\r\n";
		code = code + "Connection: close\r\n";
		code = code + "Server: SimpleHTTPServer\r\n";
	    switch(fileType){
	    case 0:
	    	break;
	    case 1:
	    	code = code + "Content-Type: image/jpeg\r\n";
	    	break;
	    case 2:
	    	code = code + "Content-Type: image/gif\r\n";
	    	break;
	    case 3:
	    	code = code + "Content-Type: application/x-zip-compressed\r\n";
	    	break;
	    default:
	    	code = code + "Content-Type: text/html\r\n";
	    	break;
	    }
	    code = code + "\r\n";
		return code;
	}
}
