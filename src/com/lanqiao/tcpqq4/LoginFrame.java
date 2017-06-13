package com.lanqiao.tcpqq4;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.lanqiao.tcpqq3.ChatUtil;

public class LoginFrame extends JFrame implements ActionListener {
	JPanel p1, p2, p3, p4;
	// 标签姓名，地�?，端�?
	JLabel labName, labAaddress, labPart;
	// 对应标签的姓名，地址，端口的文本�?
	JTextField textName, textAddress, textPort;
	// 性别单�?�按�?
	JRadioButton radioMan, radiowoman, radioSecurity;
	// 单�?�按钮组
	ButtonGroup gb;
	// 连接，断�?按钮
	JButton butConnet, butBrock;
	String sex;

	public void init() {

		this.setTitle("登录界面");
		this.setSize(350, 200);
		this.setResizable(false);// 设置不可以放�?
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public LoginFrame() {
		init();
		setPanel1();

		setPanel2();

		setPanel3();

		setPanel4();

		butConnet.addActionListener(this);
		this.getContentPane().add(p4);
		this.setVisible(true);

	}

	// 面板p4设置，也就是把p1,p2,p3设置到p4里面
	private void setPanel4() {
		p4 = new JPanel(new GridLayout(3, 1));
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
	}

	// 面板p3设置
	private void setPanel3() {
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		butConnet = new JButton("连接");
		butBrock = new JButton("断开");
		p3.add(butConnet);
		p3.add(butBrock);
	}

	// 面板p2设置
	private void setPanel2() {
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labAaddress = new JLabel("地址�?");
		textAddress = new JTextField(10);
		textAddress.setText(ChatUtil.ADDRESS);

		labPart = new JLabel("端口");
		textPort = new JTextField(10);
		textPort.setText(String.valueOf(ChatUtil.PORT));

		p2.add(labAaddress);
		p2.add(textAddress);
		p2.add(labPart);
		p2.add(textPort);
	}

	// 面板p1设置
	private void setPanel1() {
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labName = new JLabel("姓名�?");
		textName = new JTextField(10);

		gb = new ButtonGroup();
		radioMan = new JRadioButton("�?");
		radiowoman = new JRadioButton("�?");
		radioSecurity = new JRadioButton("保密");

		gb.add(radioMan);
		gb.add(radiowoman);
		gb.add(radioSecurity);

		p1.add(labName);
		p1.add(textName);
		p1.add(radioMan);
		p1.add(radiowoman);
		p1.add(radioSecurity);
	}

	public static void main(String[] args) {
		LoginFrame lf = new LoginFrame();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = textName.getText();
		if ("".equals(textName.getText())) {
			JOptionPane.showMessageDialog(this, "请填写姓�?");
			return;
		}
		if (radioMan.isSelected()) {
			sex = radioMan.getText();
		} else if (radiowoman.isSelected()) {
			sex = radioMan.getText();
		} else if (radioSecurity.isSelected()) {
			sex = radioMan.getText();
		} else {
			JOptionPane.showMessageDialog(this, "请�?�择性别");
			return;// 直接返回
		}

		// 隐藏当前界面
		this.setVisible(false);

		// 显示聊天界面
		User u = new User(name, sex);
		ChatFrame chatFrame = new ChatFrame(u);
		// chatFrame.box.addItem(name+"("+sex+")");

		/*
		 * //显示某人进入聊天�? chatFrame.setTitle(textName.getText()+"进入了聊天室");
		 */

		try {
			// 获取登陆界面地址和端�?
			String inAddress = textAddress.getText();
			String outPort = textPort.getText();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
