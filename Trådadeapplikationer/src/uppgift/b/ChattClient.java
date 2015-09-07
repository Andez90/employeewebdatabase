package uppgift.b;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import uppgift.b.ChattServer.ManageConnections;
import uppgift.b.ChattServer.ManageConnections.ClientThread;

public class ChattClient extends JFrame implements ActionListener, WindowListener{
	private JButton connect = new JButton("Connect");
	private JButton disconnect = new JButton("Disconnect");
	private JButton send = new JButton("Send");
	private JLabel connectionStatus = new JLabel("Connectionstatus");
	private JLabel setHost = new JLabel("Ange vilken host du vill ansluta till!");
	private JLabel setPort = new JLabel("Ange vilken port du vill använda!");
	private JLabel setName = new JLabel("Ange vilket namn du vill använda!");
	private JTextField getHost = new JTextField("localhost", 15);
	private JTextField getPort = new JTextField("25000", 15);
	private JTextField getName = new JTextField("Username", 15);
	private JTextField getMessage = new JTextField(40);
	private JTextArea messages = new JTextArea("Chatt Logg!\n", 40, 35);
	private JScrollPane scrollMessages = new JScrollPane(messages);
	private JTextArea online = new JTextArea("Online List!\n", 40, 20);
	private JScrollPane scrollOnline = new JScrollPane(online);
	private boolean connected;
	private ManageConnections mc;
	private int defaultPort;
	private String defaultHost;
	static ChattClient cc;
	
	public static void main(String [] args){
		cc = new ChattClient("localhost", 25000);
	}
	
	public ChattClient(String host, int port){
		defaultHost = host;
		defaultPort = port;
		setSize (800, 800);
		setLayout(new FlowLayout());
		add(setHost);
		add(getHost);
		add(setPort);
		add(getPort);
		add(setName);
		add(getName);
		add(connect);
		add(connectionStatus);
		connect.addActionListener(this);
		addWindowListener(this);
		setVisible (true);
	}
	
	public void append(String msg){
		messages.append(msg);
		messages.setCaretPosition(messages.getText().length()-1);
	}
	
	void connectionFailed(){
		dispose();
		cc = new ChattClient(defaultHost, defaultPort);
		connected = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connect){
			try{
				String host = getHost.getText();
				int port = Integer.parseInt(getPort.getText());
				String name = getName.getText();
				mc = new ManageConnections(host, port, name, this);
				if(!mc.start())
					return;
				connectionStatus.setText("connecting to " + host + " on port " + port + " as " + name);
				remove(setHost);
				remove(getHost);
				remove(setPort);
				remove(getPort);
				remove(setName);
				remove(getName);
				remove(connect);
				remove(connectionStatus);
				add(messages);
				add(scrollMessages);
				messages.setEditable(false);
				add(online);
				add(scrollOnline);
				online.setEditable(false);
				add(getMessage);
				add(send);
				send.addActionListener(this);
				add(disconnect);
				disconnect.addActionListener(this);
				mc.send(new Messages(Messages.loggIn, ""));
				repaint();
				revalidate();
			}
			catch(NumberFormatException nfe){
				connectionStatus.setText("Pleas enter a valid number");
			}
		}
		if (e.getSource() == disconnect){
			mc.send(new Messages(Messages.loggOut, ""));
			dispose();
			cc = new ChattClient("localhost", 25000);
		}
		if (e.getSource() == send){
			mc.send(new Messages(Messages.messages, getMessage.getText()));
			getMessage.setText("");
			return;
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
	
	class ManageConnections{
		private ObjectInputStream ois;
		private ObjectOutputStream ous;
		private Socket socket;
		private String host;
		private int port;
		private String userName;
		private ChattClient cc;
		
		
		public ManageConnections(String host, int port, String name){
			this(host, port, name, null);
		}
		
		public ManageConnections(String host, int port, String name, ChattClient cc){
			this.cc = cc;
			this.host = host;
			this.port = port;
			this.userName = name;
		}
		
		public boolean start(){
			try{
				socket = new Socket(host, port);
			}
			catch(Exception ex){
				display("Error connectiong to the server: " + ex);
				return false;
			}
			String msg = "Connected to: " + socket.getInetAddress() + " at port: " + socket.getPort();
			display(msg);
			try{
				ois = new ObjectInputStream(socket.getInputStream());
				ous = new ObjectOutputStream(socket.getOutputStream());
			}
			catch(IOException ioE){
				display("Error connectiong to server: " + ioE);
				return false;
			}
			new ListenFromServer().start();
			try{
				ous.writeObject(userName);
			}
			catch(IOException ioE){
				display("Error logging in: " + ioE);
				stop();
				return false;
			}
			return true;
		}
		
		public void display(String msg){
			cc.append(msg + "\n");
		}
		
		public void send(Messages msg){
			try{
				ous.writeObject(msg);
			}
			catch(IOException ioE){
				display("Error sending message: " + ioE);
			}
		}
		
		public void stop(){
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
			cc = new ChattClient("localhost", 25000);
		}
		
		class ListenFromServer extends Thread {
			public void run(){
				while(true){
					try{
						Messages msg = (Messages) ois.readObject();
						switch(msg.getType()){
						case Messages.messages:
							cc.append(msg.getMessage());
							break;
						case Messages.loggOut:
							cc.online.setText("Online List!" + "\n");
							cc.append(msg.getMessage());
							break;
						case Messages.loggIn:
							String[] result = msg.getMessage().split(":");
							String userName = result[0];
							cc.online.append(userName + "\n");
							cc.online.setCaretPosition(cc.online.getText().length()-1);
							cc.append(msg.getMessage());
							break;
						}
					}
					catch(IOException ioE){
						display("Server disconected: " + ioE);
						break;
					}
					catch(ClassNotFoundException e) {
					}
				}
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
