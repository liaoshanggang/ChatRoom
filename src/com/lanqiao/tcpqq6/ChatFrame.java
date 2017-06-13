package com.lanqiao.tcpqq6;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ChatFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel p1, p2, p3, p4, p5, p6, p7;
	JScrollPane js1, js2, js3;
	JTextArea area1, area2;
	JLabel lab;
	JComboBox<String> box;// ˽�������б�
	JCheckBox checkBox;
	JTextField text;
	JButton but1, but2;
	JList<String> list;
	DefaultListModel<String> listModel;
	User u;

	public void init() {
		this.setTitle(u.getName() + "������������");

		// p1������ã�����������Ƶ����˽�ĺ͹���
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

		// p2������ã�����˽��
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

		// p3������ã�������Ϣ���Ϳ�
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		text = new JTextField(30);
		but1 = new JButton("����");
		p3.add(text);
		p3.add(but1);

		// p4������ã���p2,p3���õ�p4�����ȥ
		p4 = new JPanel();
		p4.setLayout(new GridLayout(2, 1));
		p4.add(p2);
		p4.add(p3);

		// p5������ã���p1��p4���õ�p5�����ȥ
		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p1, BorderLayout.NORTH);
		p5.add(p4, BorderLayout.SOUTH);

		// p6������ã����ú����б�
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

		// ������p5,p6������õ�p7ȥ
		p7 = new JPanel();
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.add(p5);
		p7.add(p6);

		this.getContentPane().add(p7);

		this.setResizable(false);
		this.setSize(550, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		// this.pack();
	}

	public ChatFrame(User u) {
		this.u = u;
		final String name = u.getName();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					PrintWriter pw = new PrintWriter(ClientSocket.socket
							.getOutputStream());
					pw.println(ChatUtil.CLOSE_ROOM);
					pw.flush();
					pw.println(name + "�˳�����������");
					pw.flush();
					/*
					 * pw.println(ChatUtil.REFRESH); pw.flush();
					 */
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		init();
		but2.addActionListener(this);
		but1.addActionListener(this);
		// ���ӷ�����
		getSocket();
	}

	public void getSocket() {
		ClientSocket cs = new ClientSocket(this);
		PrintWriter out = null;
		try {
			out = new PrintWriter(cs.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(ChatUtil.FRIEND_LIST);// 1008611
		out.flush();// ˢ��
		out.println(u.getName() + ":" + u.getSex());// ����:�Ա�
		out.flush();
		out.println(ChatUtil.OPEN_ROOM);
		out.flush();
		out.println(u.getName() + "������������");
		out.flush();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("����")) {

			try {
				PrintWriter pw = new PrintWriter(
						ClientSocket.socket.getOutputStream());
				if (checkBox.isSelected()) {
					
					
					pw.println(ChatUtil.PRIVATE_CHAT);
					pw.flush();
					pw.println(this.u.getName() + "��"+this.box.getSelectedItem()+"˵��" + text.getText());
					pw.flush();
				} else {
					pw.println(ChatUtil.PUBLIC_CHAT);
					pw.flush();
					pw.println(this.u.getName() + "˵��" + text.getText());
					pw.flush();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		if (e.getActionCommand().equals("ˢ��")) {
			try {
				PrintWriter pw = new PrintWriter(
						ClientSocket.socket.getOutputStream());
				pw.println(ChatUtil.REFRESH);
				pw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
