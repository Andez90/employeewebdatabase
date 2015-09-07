package uppgift.c;

import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener{
	private Server server;
	private JTextArea serverLogg = new JTextArea("Server Logg!\n", 40, 30);
	private JTextArea httpLogg = new JTextArea("HTTP Logg!\n", 40, 30);
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");
	private JButton brows = new JButton("Brows");
	private JLabel pickCatalog = new JLabel("Catalog");
	private JLabel pickPort = new JLabel("port");
	private JTextField getCatalog = new JTextField(14);
	private JTextField getPort = new JTextField("80", 7);
	private JScrollPane scrollServer = new JScrollPane(serverLogg);
	private JScrollPane scrollHttp = new JScrollPane(httpLogg);
	private JFileChooser catalog;
	
	public GUI(){
		setSize(800, 800);
		setLayout(new FlowLayout());
		add(pickCatalog);
		add(getCatalog);
		getCatalog.setEditable(false);
		add(brows);
		brows.addActionListener(this);
		add(pickPort);
		add(getPort);
		add(start);
		start.addActionListener(this);
		add(stop);
		stop.setEnabled(true);
		add(serverLogg);
		add(scrollServer);
		serverLogg.setEditable(false);
		add(httpLogg);
		add(scrollHttp);
		httpLogg.setEditable(false);
		setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter(){
		      public void windowClosing(WindowEvent e){
		        System.exit(1);
		      }
		});
	}
	
	public void appendServer(String text) {
	    serverLogg.append(text);
	}
	
	public void appendHttp(String text) {
	    httpLogg.append(text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == brows){
			catalog = new JFileChooser();
			catalog.setCurrentDirectory(new java.io.File("."));
			catalog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			catalog.setAcceptAllFileFilterUsed(false);
			if (catalog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				getCatalog.setText(""+ catalog.getCurrentDirectory());
			}
			else{
				appendServer("Pleas select a folder!");
			}
		}
		if (e.getSource() == start){
			try{
				int port = Integer.parseInt(getPort.getText());
				String cataloge = getCatalog.getText();
				server = new Server(port, cataloge, this);
			}
			catch(NumberFormatException nfe){
				appendServer("Pleas enter a valid number!");
				return;
			}
			getPort.setEditable(false);
			start.setEnabled(false);
			brows.setEnabled(false);
			stop.setEnabled(true);
			stop.addActionListener(this);
		}
		if (e.getSource() == stop){
			appendServer("connection was stoped!");
			getPort.setEditable(true);
			start.setEnabled(true);
			brows.setEnabled(true);
			stop.setEnabled(false);
		}
	}

}
