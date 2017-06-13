package com.lanqiao.udpqq1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
/*import javax.swing.JOptionPane;*/
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MainChatFrame extends JFrame implements ActionListener {

	JPanel p1, p2, p3, p4, p5, p6, p7;
	// �����ҵ����ϣ����£��һ�������
	JScrollPane js1, js2, js3;
	// Ⱥ������˽������
	JTextArea area1, area2;
	// "��"��ǩ
	JLabel lab;
	// ˽�������б���Ͽ�
	JComboBox<String> box;
	// ˽�ĸ�ѡ��
	JCheckBox checkBox;
	// ������Ϣ�ı���
	JTextField text;
	// ���ͺ�ˢ�°�ť
	JButton but1, but2;
	// �����ҵ��б���б�ģ��
	JList<String> list;
	DefaultListModel<String> listModel;
	// �û���Ϣ���������մӵ�¼�ﴫ�����û�����Ϣ
	User u;

	public MainChatFrame(User u) {
		// ��ȡ���û�����Ϣ
		System.out.println("2���û���Ϣ������ChatFrame(User u)���ˣ�" + u.toString());
		this.u = u;

		init();

		// Ϊ���ͺ�ˢ�°�ť���¼�������
		but2.addActionListener(this);
		but1.addActionListener(this);

		// ��ʼ���͸�������
		// sendMessage();

		// �˳�������ģ��
		final String name = u.getName();
		this.addWindowListener(new WindowAdapter() {

		});

	}

	/**
	 * ��ʼ���͸�������
	 */
	/*
	 * public void sendMessage() { new UDPClientSocket(this);
	 * 
	 * }
	 */

	DatagramSocket ds;
	InetAddress ia;
	DatagramPacket outdp;
	DatagramPacket indp;
	@Override
	public void actionPerformed(ActionEvent e) {
		// 1�������ͻ������ݱ��׽��ֶ���
		// 2����ʼ������Ϣ��ָ��������
		try {
			ds = new DatagramSocket();
			ia = InetAddress.getByName("10.170.249.135");
			// while(true){
			// 3����Ϣ����
			// �������������Ϣ
			String name = this.u.getName() + "������������";
			byte[] buf = name.getBytes();
			outdp = new DatagramPacket(buf, buf.length, ia, 2000);

			ds.send(outdp);

			// �ӷ�����������Ϣ
			indp = new DatagramPacket(buf, buf.length);
			ds.receive(indp);
			String receive = new String(indp.getData(), 0, indp.getLength());
			this.area1.append(receive + "\n");
			// }
		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	/**
	 * ���������Ҵ���
	 */
	public void init() {
		this.setTitle("��" + u.getName() + "��" + "������������");

		setNorthWestP1();
		setSouthWestP2();
		setSouthP3();
		setPanel4();
		setPanel5();
		setPanel6();
		setPanel7();

		this.setResizable(false);
		this.setSize(550, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		// this.pack();
	}

	/**
	 * ������p5,p6������õ�p7ȥ
	 */
	private void setPanel7() {
		p7 = new JPanel();
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.add(p5);
		p7.add(p6);

		this.getContentPane().add(p7);
	}

	/**
	 * p6������ã��������ߺ����б�
	 */
	private void setPanel6() {
		p6 = new JPanel();
		p6.setLayout(new BorderLayout());
		listModel = new DefaultListModel<String>();

		listModel.addElement("AAAAA");
		listModel.addElement("BBBBB");
		listModel.addElement("CCCCC");

		list = new JList<String>(listModel);
		list.setVisibleRowCount(18);
		list.setFixedCellHeight(24);
		list.setFixedCellWidth(100);

		js3 = new JScrollPane(list);
		js3.setBorder(new TitledBorder("�����б�"));
		p6.add(js3, BorderLayout.NORTH);
		but2 = new JButton("ˢ��");
		p6.add(but2, BorderLayout.SOUTH);
	}

	/**
	 * p5������ã���p1��p4���õ�p5�����ȥ
	 */
	private void setPanel5() {
		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p1, BorderLayout.NORTH);
		p5.add(p4, BorderLayout.SOUTH);
	}

	/**
	 * p4������ã���p2,p3���õ�p4�����ȥ
	 */
	private void setPanel4() {
		p4 = new JPanel();
		p4.setLayout(new GridLayout(2, 1));
		p4.add(p2);
		p4.add(p3);
	}

	/**
	 * p3������ã�������Ϣ���Ϳ�
	 */
	private void setSouthP3() {
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		text = new JTextField(30);
		but1 = new JButton("����");
		p3.add(text);
		p3.add(but1);
	}

	/**
	 * p2������ã�����˽��
	 */
	private void setSouthWestP2() {
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		lab = new JLabel("��");
		box = new JComboBox<String>();
		box.addItem("������");
		checkBox = new JCheckBox();
		checkBox.setText("˽��");
		p2.add(lab);
		p2.add(box);
		p2.add(checkBox);
	}

	/**
	 * p1������ã�����������Ƶ����˽�ĺ�Ⱥ��
	 */
	private void setNorthWestP1() {
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 1));

		area1 = new JTextArea(10, 10);
		area1.setEditable(false);
		js1 = new JScrollPane(area1);
		js1.setBorder(new TitledBorder("������Ƶ��"));// ���û������߿����
		p1.add(js1);

		area2 = new JTextArea(10, 10);
		area2.setEditable(false);
		js2 = new JScrollPane(area2);
		js2.setBorder(new TitledBorder("������Ƶ��"));
		p1.add(js2);
	}
}
