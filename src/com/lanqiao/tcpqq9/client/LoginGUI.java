package com.lanqiao.tcpqq9.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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
import javax.swing.plaf.DimensionUIResource;
/**
 * @Description: ��¼����
 * @author forward
 * @date 2017��4��29�� ����9:14:34
 * @version V1.0
 */
@SuppressWarnings("serial")
public class LoginGUI extends JFrame implements ActionListener {
	// ���ӣ�ȡ����ť
	JButton loginBtn;
	JButton cancelBtn;

	// �û���
	JTextField textName;
	// �Ա�ѡ��ť
	JRadioButton radioMan, radioWoman, radioSecurity;
	// ��ѡ��ť��
	String sex;
	ButtonGroup gb;
	// ������IP���˿�
	JTextField textAddress;
	JTextField textPort;

	public LoginGUI() {
		init();
		this.setVisible(true);
		// ���ùرպ����ֹͣ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		} else if (radioWoman.isSelected()) {
			sex = radioWoman.getText();
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

			if (inAddress.equals(ChatUtil.ADDRESS)
					&& outPort.equals(String.valueOf(ChatUtil.PORT))) {// �����ȷ,
				// ���ص�ǰ����
				this.setVisible(false);

				// ��ʾ�������
				User u = new User(name, sex);
				System.out.println("�û���" + name + "�Ա�" + sex);
				new ChatRoomGUI(u);
				// System.out.println("1����¼�ɹ��������������");

			} else {// �������ȷ
				// ��ʾ��ʾ��
				JOptionPane.showMessageDialog(this, "��������ַ��˿ڲ���ȷ,����������",
						"����ʧ��", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LoginGUI();
	}

	private void init() {
		this.setTitle("��½����");
		this.setBounds(300, 300, 300, 300);

		// ���ܷŴ���С
		this.setResizable(false);

		// ��ȡ��Ļ��С
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// �ô��ھ�����ʾ
		this.setLocation(screenSize.width / 2 - 400 / 2,
				screenSize.height / 2 - 300 / 2);

		// ��ȡ�����ϣ�����������ÿ����壬������λ��
		JPanel northPanel = getNorthPanel();
		JPanel southPanel = getSouthPanel();
		JPanel eastPanel = getEastPanel();
		JPanel westPanel = getWestPanel();
		JPanel centerPanel = getCenterPanel();
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);

		loginBtn.addActionListener(this);

		// ����͸��
		northPanel.setOpaque(false);
		southPanel.setOpaque(false);
		westPanel.setOpaque(false);
		centerPanel.setOpaque(false);

	}

	// �����м����
	private JPanel getCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// �����û�����Ϊ���ɱ༭�Ŀͻ��˱���������ַ
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		String address = localhost.getHostAddress();
		// String name = localhost.getHostName();

		textName = new JTextField();

		textName.setText(address);
		textName.setEditable(false);
		textName.setPreferredSize(new DimensionUIResource(150, 25));

		// �����Ա�ѡ
		JPanel jpanel = new JPanel();// ����һ���������Ա�ѡ��ť

		this.radioMan = new JRadioButton("��");
		this.radioWoman = new JRadioButton("Ů");
		this.radioSecurity = new JRadioButton("����");

		jpanel.setPreferredSize(new DimensionUIResource(150, 25));
		gb = new ButtonGroup();

		jpanel.add(radioMan);
		jpanel.add(radioWoman);
		jpanel.add(radioSecurity);

		this.gb.add(radioMan);
		this.gb.add(radioWoman);
		this.gb.add(radioSecurity);

		// ���÷������ı���
		this.textAddress = new JTextField();
		textAddress.setText(ChatUtil.ADDRESS);
		textAddress.setEditable(true);
		textAddress.setPreferredSize(new DimensionUIResource(150, 25));

		// ���ö˿��ı���
		this.textPort = new JTextField();
		textPort.setEditable(true);
		textPort.setText(String.valueOf(ChatUtil.PORT));
		textPort.setPreferredSize(new DimensionUIResource(150, 25));

		centerPanel.add(textName);
		centerPanel.add(jpanel);
		centerPanel.add(textAddress);
		centerPanel.add(textPort);
		return centerPanel;
	}

	// ���������
	private JPanel getWestPanel() {
		JPanel westPanel = new JPanel();

		westPanel.setPreferredSize(new Dimension(80, 0));
		// westPanel.setBackground(Color.RED);
		westPanel.setLayout(new FlowLayout());

		// �����ֱ�����û������Ա𡢷�����IP���˿ںű�ǩ
		JLabel label0 = new JLabel("�û�����");
		label0.setPreferredSize(new Dimension(70, 30));

		JLabel label1 = new JLabel("�Ա�");
		label1.setPreferredSize(new Dimension(70, 25));

		JLabel label2 = new JLabel("������IP��");
		label2.setPreferredSize(new Dimension(70, 25));

		JLabel label3 = new JLabel("�˿ںţ�");
		label3.setPreferredSize(new Dimension(70, 25));

		westPanel.add(label0);
		westPanel.add(label1);
		westPanel.add(label2);
		westPanel.add(label3);

		westPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		return westPanel;
	}

	// ���ö����
	private JPanel getEastPanel() {
		JPanel eastPanel = new JPanel();
		return eastPanel;
	}

	// ���������
	private JPanel getSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(0, 80));
		southPanel.setBackground(Color.white);

		// �������2����ť�����Ӻ�ȡ����ť
		loginBtn = new JButton("����");
		cancelBtn = new JButton("ȡ��");
		loginBtn.setPreferredSize(new Dimension(60, 30));
		cancelBtn.setPreferredSize(new Dimension(60, 30));
		southPanel.add(loginBtn);
		southPanel.add(cancelBtn);

		return southPanel;
	}

	// ���ñ����
	private JPanel getNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(0, 50));
		northPanel.setBackground(Color.WHITE);
		return northPanel;
	}
}
