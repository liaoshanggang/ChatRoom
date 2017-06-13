package com.lanqiao.udpqq1;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ChatLoginFrame extends JFrame implements ActionListener {
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
	// ��ȡ��ѡ��ť�ϵ��Ա�
	String sex;

	/**
	 * ��½�����ʼ��
	 */
	public void init() {

		this.setTitle("��¼����");
		this.setSize(350, 200);
		this.setResizable(false);// ���ò����Ե�����С
		// ���ô��������ָ�������λ�á�����Ϊnull���洰�ڽ�������Ļ������
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public ChatLoginFrame() {
		init();
		// ���p1,p2,p3,p4������
		setPanel1();

		setPanel2();

		setPanel3();

		setPanel4();

		// Ϊ���Ӱ�ť���¼�������
		butConnet.addActionListener(this);

		this.getContentPane().add(p4);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new ChatLoginFrame();// ��½���濪ʼ
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ��֤�û��Ƿ���д��Ϣ�����û����д��������ʾ�Ի��򲢷���
		// �������д�����ȡ�û�������������Ա��ֵ
		String name = textName.getText();
		if ("".equals(textName.getText())) {
			JOptionPane.showMessageDialog(this, "����д����");
			return;
		}

		if (radioMan.isSelected()) {
			sex = radioMan.getText();
		} else if (radiowoman.isSelected()) {
			sex = radiowoman.getText();
		} else if (radioSecurity.isSelected()) {
			sex = radioSecurity.getText();
		} else {
			JOptionPane.showMessageDialog(this, "��ѡ���Ա�");
			return;// ֱ�ӷ���
		}

		// ����������ַ�Ͷ˿��Ƿ���ȷ
		try {
			// ��ȡ��½�����ַ�Ͷ˿�
			String inAddress = textAddress.getText();
			String outPort = textPort.getText();
			if (inAddress.equals(FlagUtil.ADDRESS)
					&& outPort.equals(String.valueOf(FlagUtil.PORT))) {

				// ���ص�ǰ����
				this.setVisible(false);

				// ��ʾ�������
				User u = new User(name, sex);
				System.out
						.println("1��LoginFrame actionPerformed(ActionEvent e):"
								+ u.toString());
				new MainChatFrame(u);
				// System.out.println("1����¼�ɹ��������������");

			} else {
				// ��ʾ��ʾ��
				JOptionPane.showMessageDialog(this, "��������ַ��˿ڲ���ȷ,����������",
						"����ʧ��", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * ���p4���ã�Ҳ���ǰ�p1,p2,p3���õ�p4����
	 */
	private void setPanel4() {
		p4 = new JPanel(new GridLayout(3, 1));
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
	}

	/**
	 * ���p3���ã�����������ӣ��Ͽ���ť
	 */
	private void setPanel3() {
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		butConnet = new JButton("����");
		butBrock = new JButton("�Ͽ�");
		p3.add(butConnet);
		p3.add(butBrock);
	}

	/**
	 * ���p2���ã�������е�ַ���˿ڱ�ǩ���ı���
	 */
	private void setPanel2() {
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labAaddress = new JLabel("��ַ��");
		textAddress = new JTextField(10);
		textAddress.setText(FlagUtil.ADDRESS);

		labPart = new JLabel("�˿�");
		textPort = new JTextField(10);
		textPort.setText(String.valueOf(FlagUtil.PORT));

		p2.add(labAaddress);
		p2.add(textAddress);
		p2.add(labPart);
		p2.add(textPort);
	}

	/**
	 * ���p1���ã���������û��ĳ�ʼ��Ϣ���������Ա�
	 */
	private void setPanel1() {
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labName = new JLabel("������");
		textName = new JTextField(10);
		
		// �����û���Ϊ���ص�ַ
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		System.out.println("localhost: " + localhost.getHostAddress());
		System.out.println("localhost: " + localhost.getHostName());
		
		String address = localhost.getHostAddress();
		//String name = localhost.getHostName();
		textName.setText(address);
		textName.setEditable(false);
		
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

}
