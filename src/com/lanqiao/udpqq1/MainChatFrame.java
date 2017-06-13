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
	// 聊天室的左上，左下，右滑动窗格
	JScrollPane js1, js2, js3;
	// 群聊区域，私聊区域
	JTextArea area1, area2;
	// "对"标签
	JLabel lab;
	// 私聊下拉列表组合框
	JComboBox<String> box;
	// 私聊复选框
	JCheckBox checkBox;
	// 发送消息文本框
	JTextField text;
	// 发送和刷新按钮
	JButton but1, but2;
	// 聊天室的列表和列表模型
	JList<String> list;
	DefaultListModel<String> listModel;
	// 用户信息，用来接收从登录里传来了用户的信息
	User u;

	public MainChatFrame(User u) {
		// 获取了用户的信息
		System.out.println("2、用户信息传到这ChatFrame(User u)里了：" + u.toString());
		this.u = u;

		init();

		// 为发送和刷新按钮绑定事件监听器
		but2.addActionListener(this);
		but1.addActionListener(this);

		// 开始发送给服务器
		// sendMessage();

		// 退出聊天室模块
		final String name = u.getName();
		this.addWindowListener(new WindowAdapter() {

		});

	}

	/**
	 * 开始发送给服务器
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
		// 1、建立客户端数据报套接字对象
		// 2、开始发送消息到指定服务器
		try {
			ds = new DatagramSocket();
			ia = InetAddress.getByName("10.170.249.135");
			// while(true){
			// 3、消息处理
			// 向服务器发送消息
			String name = this.u.getName() + "进入了聊天室";
			byte[] buf = name.getBytes();
			outdp = new DatagramPacket(buf, buf.length, ia, 2000);

			ds.send(outdp);

			// 从服务器接送消息
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
	 * 设置聊天室窗体
	 */
	public void init() {
		this.setTitle("【" + u.getName() + "】" + "进入了聊天室");

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
	 * 把左右p5,p6面板设置到p7去
	 */
	private void setPanel7() {
		p7 = new JPanel();
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.add(p5);
		p7.add(p6);

		this.getContentPane().add(p7);
	}

	/**
	 * p6面板设置，设置在线好友列表
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
		js3.setBorder(new TitledBorder("好友列表"));
		p6.add(js3, BorderLayout.NORTH);
		but2 = new JButton("刷新");
		p6.add(but2, BorderLayout.SOUTH);
	}

	/**
	 * p5面板设置，把p1，p4设置到p5面板中去
	 */
	private void setPanel5() {
		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p1, BorderLayout.NORTH);
		p5.add(p4, BorderLayout.SOUTH);
	}

	/**
	 * p4面板设置，把p2,p3设置到p4面板中去
	 */
	private void setPanel4() {
		p4 = new JPanel();
		p4.setLayout(new GridLayout(2, 1));
		p4.add(p2);
		p4.add(p3);
	}

	/**
	 * p3面板设置，设置消息发送框
	 */
	private void setSouthP3() {
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		text = new JTextField(30);
		but1 = new JButton("发送");
		p3.add(text);
		p3.add(but1);
	}

	/**
	 * p2面板设置，设置私聊
	 */
	private void setSouthWestP2() {
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		lab = new JLabel("对");
		box = new JComboBox<String>();
		box.addItem("所有人");
		checkBox = new JCheckBox();
		checkBox.setText("私聊");
		p2.add(lab);
		p2.add(box);
		p2.add(checkBox);
	}

	/**
	 * p1面板设置，设置主聊天频道，私聊和群聊
	 */
	private void setNorthWestP1() {
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 1));

		area1 = new JTextArea(10, 10);
		area1.setEditable(false);
		js1 = new JScrollPane(area1);
		js1.setBorder(new TitledBorder("主聊天频道"));// 设置滑动面板边框标题
		p1.add(js1);

		area2 = new JTextArea(10, 10);
		area2.setEditable(false);
		js2 = new JScrollPane(area2);
		js2.setBorder(new TitledBorder("主聊天频道"));
		p1.add(js2);
	}
}
