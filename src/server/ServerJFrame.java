package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ServerJFrame extends JFrame implements ActionListener{
	JButton start = new JButton("Æô¶¯"); 
	JButton stop = new JButton("Í£Ö¹");
	
	public ServerJFrame() {
		init();
		this.setVisible(true);
	}

	private void init() {
		this.setTitle("·þÎñÆ÷");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
