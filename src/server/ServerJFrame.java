package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ServerJFrame extends JFrame implements ActionListener{
	JButton start = new JButton("����"); 
	JButton stop = new JButton("ֹͣ");
	
	public ServerJFrame() {
		init();
		this.setVisible(true);
	}

	private void init() {
		this.setTitle("������");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
