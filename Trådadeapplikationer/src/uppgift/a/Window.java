package uppgift.a;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Window extends JFrame implements ActionListener{
	private PortScanner ps;
	private JTextField minInput = new JTextField ("1", 5);
	private JTextField maxInput = new JTextField ("65535", 5);
	private JButton look = new JButton("look!");
	private JButton stop = new JButton("stop!");
	private JLabel portList = new JLabel();
	private JScrollPane scroll;
	
	public Window(){
		setSize (900, 600);
		setLayout(new FlowLayout());
		add(minInput);
		add(maxInput);
		add(look);
		add(portList);
		look.addActionListener(this);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setVisible (true);
	}
	
	public void getList(int minValue, int maxValue){
		this.add(portList);
		scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(700,500));
		add(scroll);
		revalidate ();
		repaint ();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == look){
			try{
				String valueMin = minInput.getText();
				String valueMax = maxInput.getText();
				int minValue = Integer.parseInt(valueMin);
				int maxValue = Integer.parseInt(valueMax);
				ps = new PortScanner(minValue, maxValue);
				ps.execute();
				remove(look);
				add(stop);
				stop.addActionListener(this);
				repaint();
				revalidate();
			}
			catch(NumberFormatException nfe){
				portList.setText("Pleas enter a valid number!");
				repaint();
				revalidate();
			}
		}
		else if (e.getSource() == stop){
			if (ps != null)
				ps.cancel(true);
				remove(stop);
				add(look);
				portList.setText("Scanning canseled");
				repaint();
				revalidate();
		}
	}

	class PortScanner extends SwingWorker<String, String>{
		private final int max = 65535;
		private final int min = 1;
		private int value, valueMax;
		private String host;
		private Socket socket;
		
		public PortScanner (int minValue, int maxValue){
			this.value = minValue;
			this.valueMax = maxValue;
			this.host = "localhost";
		}
	
		@Override
		protected String doInBackground() throws Exception {
			DefaultListModel defaultList = new DefaultListModel();
			JList list = new JList(defaultList);
			while (!isCancelled() && value <= valueMax){
				Socket socket = null;
			    try {
			        socket = new Socket("localhost", value);
			    } catch (IOException e) {
			    	defaultList.addElement("Port: " + value + " is available!");
			    	publish("Port: " + value + " is available!");
			    } finally {
			        if( socket != null){
			            try {
			                socket.close();
			            } catch (IOException e) {
			                portList.setText("can't close conection!");
			            }
			        }
			    }
				value++;
			}
			scroll = new JScrollPane(list);
			scroll.setPreferredSize(new Dimension(700,500));
			add(scroll);
			revalidate ();
			repaint ();
			return null;
		}
		
		@Override
		protected void done(){
			try{
				remove(stop);
				add(look);
				portList.setText("Scanning completed");
				repaint();
				revalidate();
			}
			catch(Exception ex){
				portList.setText("Was not abel to complete scan!");
				repaint();
				revalidate();
			}
		}
		
		@Override
		protected void process(java.util.List<String> mellan){
			portList.setText(mellan.get(mellan.size()-1));
			repaint();
			revalidate();
		}
	}
}