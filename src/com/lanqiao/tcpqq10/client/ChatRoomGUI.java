package com.lanqiao.tcpqq10.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * @Description: �����ҽ���
 * @author forward
 * @date 2017��4��29�� ����9:15:21
 * @version V1.0
 */
@SuppressWarnings("serial")
public class ChatRoomGUI extends JFrame implements ActionListener {

	JPanel p1, p2, p3, p4, p5, p6, p7;
	// �����ҵ����ϣ����£��һ�������
	JScrollPane js1, js2, js3;
	// Ⱥ������˽������
	JTextArea pubTextArea, priTextArea;
	// "��"��ǩ
	JLabel lab;
	// ˽�������б���Ͽ�
	JComboBox<String> box;
	// ˽�ĸ�ѡ��
	JCheckBox checkBox;
	// ������Ϣ�ı���
	JTextField sendText;
	// ���ͺ�ˢ�°�ť
	JButton sendBtn; 
//	JButton refreshBtn;
	// �����ҵ��б���б�ģ��
	JList<String> list;
	DefaultListModel<String> listModel;
	// �û���Ϣ���������մӵ�¼�ﴫ�����û�����Ϣ
	User u;

	public ChatRoomGUI(User u) {
		// ��ȡ���û�����Ϣ
		System.out.println("2���û���Ϣ������ChatFrame(User u)���ˣ�" + u.toString());
		
		this.u = u;
		init();

		// Ϊ���ͺ�ˢ�°�ť���¼�������
		sendBtn.addActionListener(this);
		System.out.println("sendBtn.addActionListener(this)");
		//refreshBtn.addActionListener(this);

		// ���ӷ�����
		getSocket();

		System.out.println("this.addWindowListener(new WindowAdapter() {");
		// �˳�������ģ��
		final String name = u.getName();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					PrintWriter pw = new PrintWriter(ClientSocket.socket
							.getOutputStream());
					pw.println(ChatUtil.CLOSE_ROOM);
					System.out.println(ChatUtil.CLOSE_ROOM);
					pw.flush();
					String str = "��" + name + "��" + "�˳�����������";
					pw.println(str);// ĳĳ����������
					System.out.println(str);
					pw.flush();
					// pw.println(ChatUtil.REFRESH);
					// pw.flush();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});

	}

	/**
	 * ����������
	 */
	public void getSocket() {
		new ClientSocket(this);

		//����������ģ��
		PrintWriter out = null;
		try {
			out = new PrintWriter(ClientSocket.socket.getOutputStream());

			out.println(ChatUtil.FRIEND_LIST);// �����б��ʶ��
			System.out.println(ChatUtil.FRIEND_LIST);
			out.flush();// ˢ��
			
			String str = u.getName() + ":" + u.getSex();
			out.println(str);// ĳĳ:�Ա�
			System.out.println(str);
			out.flush();// ˢ��

			out.println(ChatUtil.OPEN_ROOM);// �����ұ�ʶ��
			System.out.println(ChatUtil.OPEN_ROOM);
			out.flush();
			str = "��" + u.getName() + "��" + "������������";
			out.println(str);// ĳĳ����������
			System.out.println(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ChatRoomGUI actionPerformed(ActionEvent e)");
		//Ⱥ�ĺ�˽��ģ��
		// Ⱥ�ĺ�˽�Ĵ����¼�
		if (e.getActionCommand().equals("����")) {
			try {
				PrintWriter pw = new PrintWriter(
						ClientSocket.socket.getOutputStream());
				if (checkBox.isSelected()) {// ���˽�ĸ�ѡ��ѡ�����͸���Ӧ�û�˽����Ϣ
					pw.println(ChatUtil.PRIVATE_CHAT);
					System.out.println(ChatUtil.PRIVATE_CHAT);
					pw.flush();
					
					
					String str = "��" + this.u.getName() + "���ԡ�"
							+ this.box.getSelectedItem() + "��˵��"
							+ sendText.getText();
					pw.println(str);
					pw.flush();
					
					System.out.println(str);

				} else {// ���˽�ĸ�ѡ��δѡ�����͸������û�Ⱥ����Ϣ
					pw.println(ChatUtil.PUBLIC_CHAT);
					System.out.println(ChatUtil.PUBLIC_CHAT);
					pw.flush();

					String str = "��" + this.u.getName() + "��˵��"
							+ sendText.getText();
					pw.println(str);
					System.out.println(str);
					pw.flush();
				}
				
				//����������Ϳ���Ϊ��
				ChatRoomGUI.this.sendText.setText("");
				
			} catch (IOException e1) {
				// �쳣��ʾ��
				// JOptionPane.showMessageDialog(this, "�����쳣",
				// "��������",JOptionPane.INFORMATION_MESSAGE);

				e1.printStackTrace();
			}
			
			System.out.println("action����");
		}
		//ˢ��ģ��
		// ˢ�´����¼�
		if (e.getActionCommand().equals("ˢ��")) {
			try {

				PrintWriter pw = new PrintWriter(
						ClientSocket.socket.getOutputStream());
				pw.println(ChatUtil.REFRESH);
				pw.flush();

			} catch (IOException e1) {
				// JOptionPane.showMessageDialog(this, "ˢ���쳣",
				// "ˢ������",JOptionPane.INFORMATION_MESSAGE);
				e1.printStackTrace();
			}
		}
	}

	/**
	 * ���������Ҵ���
	 */
	public void init() {
		System.out.println("����ChatRoomGUI init()");
		this.setTitle("��" + u.getName() + "��" + "������������");

		//p1������ã���������Ƶ����˽�ĺ�Ⱥ��
		setNorthWestP1();
		//p2������ã�����˽��
		setSouthWestP2();
		//p3������ã�������Ϣ���Ϳ�
		setSouthP3();
		//p4������ã���p2,p3���õ�p4�����ȥ
		setPanel4();
		//p5������ã���p1��p4���õ�p5�����ȥ
		setPanel5();
		//p6������ã��������ߺ����б�
		setPanel6();
		//������p5,p6������õ�p7ȥ
		setPanel7();

		this.setResizable(false);
		this.setSize(600, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		System.out.println("��ʼ�����");
	}

	/**
	 * ������p5,p6������õ�p7ȥ
	 */
	private void setPanel7() {
		System.out.println("setPanel7()");
		p7 = new JPanel();
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.setBackground(Color.CYAN);
		p7.add(p5);
		p7.add(p6);

		this.getContentPane().add(p7);
	}

	/**
	 * p6������ã��������ߺ����б�
	 */
	private void setPanel6() {
		System.out.println("setPanel6()");
		p6 = new JPanel();
		p6.setLayout(new BorderLayout());
		listModel = new DefaultListModel<String>();
		// listModel.addElement("����");
		// listModel.addElement("����");
		// listModel.addElement("����");

		list = new JList<String>(listModel);
		list.setVisibleRowCount(18);
		list.setFixedCellHeight(25);
		list.setFixedCellWidth(150);
		list.setBackground(Color.PINK);
		
		js3 = new JScrollPane(list);
		js3.setBackground(Color.MAGENTA);
		js3.setBorder(new TitledBorder("�����б�"));
		p6.add(js3, BorderLayout.NORTH);
//		refreshBtn = new JButton("ˢ��");
//		p6.add(refreshBtn, BorderLayout.SOUTH);
	}

	/**
	 * p5������ã���p1��p4���õ�p5�����ȥ
	 */
	private void setPanel5() {
		System.out.println("setPanel5()");
		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.setBackground(Color.MAGENTA);
		p5.add(p1, BorderLayout.NORTH);
		p5.add(p4, BorderLayout.SOUTH);
	}

	/**
	 * p4������ã���p2,p3���õ�p4�����ȥ
	 */
	private void setPanel4() {
		System.out.println("setPanel4()");
		p4 = new JPanel();
		p4.setLayout(new GridLayout(2, 1));
		p4.setBackground(Color.MAGENTA);
		p4.add(p2);
		p4.add(p3);
	}

	/**
	 * p3������ã�������Ϣ���Ϳ�
	 */
	private void setSouthP3() {
		System.out.println("setSouthP3()");
		sendText = new JTextField(30);
		sendText.setBackground(Color.WHITE);
		sendBtn = new JButton("����");
		
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(Color.MAGENTA);
		p3.add(sendText);
		p3.add(sendBtn);
	}

	/**
	 * p2������ã�����˽��
	 */
	private void setSouthWestP2() {
		System.out.println("setSouthWestP2()");
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		lab = new JLabel("��");
		box = new JComboBox<String>();
		box.addItem("������");
		checkBox = new JCheckBox();
		checkBox.setText("˽��");
		p2.setBackground(Color.magenta);
		p2.add(lab);
		p2.add(box);
		p2.add(checkBox);
	}

	/**
	 * p1������ã���������Ƶ����˽�ĺ�Ⱥ��
	 */
	private void setNorthWestP1() {
		System.out.println("setNorthWestP1()");
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 1));

		pubTextArea = new JTextArea(10, 10);
		pubTextArea.setEditable(false);
		pubTextArea.setBackground(Color.PINK);
		js1 = new JScrollPane(pubTextArea);
		js1.setBorder(new TitledBorder("Ⱥ��Ƶ��"));// ���û������߿����
		js1.setBackground(Color.MAGENTA);
		p1.add(js1);

		priTextArea = new JTextArea(10, 10);
		priTextArea.setEditable(false);
		priTextArea.setBackground(Color.PINK);
		js2 = new JScrollPane(priTextArea);
		js2.setBorder(new TitledBorder("˽��Ƶ��"));
		js2.setBackground(Color.MAGENTA);
		p1.add(js2);
	}
}
