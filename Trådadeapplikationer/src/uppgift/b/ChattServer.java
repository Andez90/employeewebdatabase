package uppgift.b;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;

public class ChattServer extends JFrame implements ActionListener, WindowListener{
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");
	private static JTextArea connectionStatus = new JTextArea(20, 45);
	private JLabel setPort = new JLabel("Ange vilken port du vill att Servern skall använda!");
	private JTextField getPort = new JTextField("25000", 5);
	private ManageConnections mc;
	private JScrollPane scroll = new JScrollPane(connectionStatus);
	
	public static void main (String [] args){
		ChattServer cs = new ChattServer(25000);
	}
		
	public ChattServer(int port){
		setLayout(new FlowLayout());
		mc = null;
		add(connectionStatus);
		connectionStatus.setEditable(false);
		add(scroll);
		add(setPort);
		add(getPort);
		add(start);
		start.addActionListener(this);
		addWindowListener(this);
		setSize(600, 500);
		setVisible(true);
	}
	
	
	static void append (String str){
		connectionStatus.append(str);
		connectionStatus.setCaretPosition(connectionStatus.getText().length()-1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start){
			int port;
			try{
				port = Integer.parseInt(getPort.getText());
				mc = new ManageConnections(port);
			}
			catch(NumberFormatException nfe){
				append("Pleas enter a valid number");
			}
			new ServerRunning().start();
			remove(start);
			add(stop);
			stop.addActionListener(this);
			getPort.setEditable(false);
			repaint();
			revalidate();
		}
		
		else if (e.getSource() == stop){
			mc.stop();
			mc = null;
			getPort.setEditable(true);
			remove(stop);
			add(start);
			getPort.setEditable(true);
			repaint();
			revalidate();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		if(mc != null) {
			try {
				mc.stop();
			}
			catch(Exception eClose) {
			}
			mc = null;
		}
		dispose();
		System.exit(0);
	}
	
	class ServerRunning extends Thread {
		public void run() {
			mc.start();
			getPort.setEditable(true);
			append("Server crashed\n");
			mc = null;
		}
	}
	
	static class ManageConnections{
		private static int uniqId;
		private ArrayList <ClientThread> all;
		private int port;
		private boolean keepRunning;
		private ChattServer cs;
		
		
		public ManageConnections(int port){
			this(port, null);
		}
		
		public ManageConnections(int port, ChattServer cs){
			this.cs = cs;
			this.port = port;
			all = new ArrayList<ClientThread>();
		}
		
		public void start(){
			keepRunning = true;
			try{
				ServerSocket ss = new ServerSocket(port);
				while(keepRunning){
					display("Server waiting for clients to connect on port: " + port + "!");
					Socket socket = ss.accept();
					if(!keepRunning)
						break;
					ClientThread ct = new ClientThread(socket);
					all.add(ct);
					ct.start();
				}
				try{
					ss.close();
					for(int i = 0; i < all.size(); i++){
						ClientThread ct = all.get(i);
						try{
							ct.ois.close();
							ct.ous.close();
							ct.socket.close();
						}
						catch(IOException ioE){
							String msg = "Error: " + ioE;
							display(msg);
						}
					}
				}
				catch(Exception e){
					String msg = "Exception closing Server and Client: " + e;
					display(msg);
				}
			}
			catch (IOException e){
				String msg = "Exception on ServerSocket: " + e;
				display(msg);
			}
		}
		
		public void stop(){
			keepRunning = false;
			try{
				new Socket ("localhost", port);
			}
			catch(IOException ioE){
				display("Error " + ioE);
			}
		}
		
		public void display(String msg){
			append(msg + "\n");
		}
		
		public synchronized void send(String msg, int type){
			Messages message = new Messages(type, msg + "\n");
			append(message.getMessage());
			
			for(int i = all.size(); --i >= 0;){
				ClientThread ct = all.get(i);
				if(!ct.write(message)){
					all.remove(i);
					display(ct.username + " has disconected!");
				}
			}
		}
		
		synchronized void remove(int id){
			for(int i = 0; i < all.size(); i++){
				ClientThread ct = all.get(i);
				if(ct.id == id){
					all.remove(id);
					return;
				}
			}
		}
		
		class ClientThread extends Thread {
			ArrayList <String> onlineList = new ArrayList<String>();
			Socket socket;
			ObjectInputStream ois;
			ObjectOutputStream ous;
			int id;
			String username;
			Messages m;
			
			ClientThread(Socket socket){
				id = ++uniqId;
				this.socket = socket;
				try{
					ous = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
					username = (String) ois.readObject();
					display(username + " has entered the chat!");
					onlineList.add(username);
				}
				catch(IOException e){
					display("Error creating input/output stream: " + e);
					return;
				}
				catch (ClassNotFoundException e){
				}
			}
			
			public void run(){
				boolean keepRunning = true;
				while(keepRunning){
					try {
						m = (Messages) ois.readObject();
					}
					catch (IOException e) {
						display("Exception reading stream from: " + username + " " + e);
						break;				
					}
					catch(ClassNotFoundException e) {
						break;
					}
					String sentMessage = m.getMessage();
					switch(m.getType()){
					case Messages.messages:
						send(username + ": " + sentMessage, 2);
						break;
					case Messages.loggOut:
						send(username + ": Left the chat!", 1);
						all.remove(this);
						keepRunning = false;
					case Messages.loggIn:
						for(int i = 0; i < all.size(); ++i) {
							ClientThread ct = all.get(i);
							send(ct.username + ": Entered the chat!", 0);
						}
						break;
					}
				}
				remove(id);
				close();
			}
			
			public void close(){
				try{
					if(ous != null)
						ous.close();
				}
				catch(Exception e){
				}
				try{
					if(ois != null)
						ois.close();
				}
				catch(Exception e){
				}
				try{
					if(socket != null)
						socket.close();
				}
				catch(Exception e){
				}
			}
			
			public boolean write(Messages msg){
				if(!socket.isConnected()){
					close();
					return false;
				}
				try{
					ous.writeObject(msg);
				}
				catch(IOException ioE){
					display("Error sending to client: " + username);
					display(ioE.toString());
				}
				return true;
			}
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
