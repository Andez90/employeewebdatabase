package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutWindow extends JFrame implements ActionListener{
	private JButton close = new JButton ("Stäng");
	private JLabel aboutLabel = new JLabel (about());
	
	public void aboutWindow (){
		setTitle ("Click the bugg game");
		setSize (400, 400);
		setLayout (new FlowLayout ());
		add (aboutLabel);
		add (close);
		close.addActionListener (this);
		setVisible (true);
	}
	
	public String about(){
		String about = "En spelrunda är 60 sekudner och det gäller att trycka på så många buggar som möjligt \n för varje bugg man lyckas klicka på får man 1 poäng. \n om man lyckas komma in på 10 i topp listan när tiden är ute får man skriva in sitt namn och kommer att läggas till på listan. \n Develper Anders Johansson";
		return about;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource () == close){
			dispose ();
		}
	}
}
