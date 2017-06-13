package com.lanqiao.tcpqq6;

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
	// ��ǩ��������ַ���˿�
	JLabel labName, labAaddress, labPart;
	// ��Ӧ��ǩ����������ַ���˿ڵ��ı���
	JTextField textName, textAddress, textPort;
	// �Ա�ѡ��ť
	JRadioButton radioMan, radiowoman, radioSecurity;
	// ��ѡ��ť��
	ButtonGroup gb;
	// ���ӣ��Ͽ���ť
	JButton butConnet, butBrock;
	String sex;

	public void init() {

		this.setTitle("��¼����");
		this.setSize(350, 200);
		this.setResizable(false);// ���ò����ԷŴ�
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

	// ���p4���ã�Ҳ���ǰ�p1,p2,p3���õ�p4����
	private void setPanel4() {
		p4 = new JPanel(new GridLayout(3, 1));
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
	}

	// ���p3����
	private void setPanel3() {
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		butConnet = new JButton("����");
		butBrock = new JButton("�Ͽ�");
		p3.add(butConnet);
		p3.add(butBrock);
	}

	// ���p2����
	private void setPanel2() {
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labAaddress = new JLabel("��ַ��");
		textAddress = new JTextField(10);
		textAddress.setText(ChatUtil.ADDRESS);

		labPart = new JLabel("�˿�");
		textPort = new JTextField(10);
		textPort.setText(String.valueOf(ChatUtil.PORT));

		p2.add(labAaddress);
		p2.add(textAddress);
		p2.add(labPart);
		p2.add(textPort);
	}

	// ���p1����
	private void setPanel1() {
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labName = new JLabel("������");
		textName = new JTextField(10);

		gb = new ButtonGroup();
		radioMan = new JRadioButton("��");
		radiowoman = new JRadioButton("Ů");
		radioSecurity = new JRadioButton("����");

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
			JOptionPane.showMessageDialog(this, "����д����");
			return;
		}
		if (radioMan.isSelected()) {
			sex = radioMan.getText();
		} else if (radiowoman.isSelected()) {
			sex = radioMan.getText();
		} else if (radioSecurity.isSelected()) {
			sex = radioMan.getText();
		} else {
			JOptionPane.showMessageDialog(this, "��ѡ���Ա�");
			return;// ֱ�ӷ���
		}

		// ���ص�ǰ����
		this.setVisible(false);

		// ��ʾ�������
		User u = new User(name, sex);
		ChatFrame chatFrame = new ChatFrame(u);
		// chatFrame.box.addItem(name+"("+sex+")");

		/*
		 * //��ʾĳ�˽��������� chatFrame.setTitle(textName.getText()+"������������");
		 */

		try {
			// ��ȡ��½�����ַ�Ͷ˿�
			String inAddress = textAddress.getText();
			String outPort = textPort.getText();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
