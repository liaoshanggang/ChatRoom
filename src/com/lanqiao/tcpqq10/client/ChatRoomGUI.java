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
 * @Description: 聊天室界面
 * @author forward
 * @date 2017年4月29日 上午9:15:21
 * @version V1.0
 */
@SuppressWarnings("serial")
public class ChatRoomGUI extends JFrame implements ActionListener {

	JPanel p1, p2, p3, p4, p5, p6, p7;
	// 聊天室的左上，左下，右滑动窗格
	JScrollPane js1, js2, js3;
	// 群聊区域，私聊区域
	JTextArea pubTextArea, priTextArea;
	// "对"标签
	JLabel lab;
	// 私聊下拉列表组合框
	JComboBox<String> box;
	// 私聊复选框
	JCheckBox checkBox;
	// 发送消息文本框
	JTextField sendText;
	// 发送和刷新按钮
	JButton sendBtn; 
//	JButton refreshBtn;
	// 聊天室的列表和列表模型
	JList<String> list;
	DefaultListModel<String> listModel;
	// 用户信息，用来接收从登录里传来了用户的信息
	User u;

	public ChatRoomGUI(User u) {
		// 获取了用户的信息
		System.out.println("2、用户信息传到这ChatFrame(User u)里了：" + u.toString());
		
		this.u = u;
		init();

		// 为发送和刷新按钮绑定事件监听器
		sendBtn.addActionListener(this);
		System.out.println("sendBtn.addActionListener(this)");
		//refreshBtn.addActionListener(this);

		// 连接服务器
		getSocket();

		System.out.println("this.addWindowListener(new WindowAdapter() {");
		// 退出聊天室模块
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
					String str = "【" + name + "】" + "退出了聊天室了";
					pw.println(str);// 某某进入聊天室
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
	 * 服务器连接
	 */
	public void getSocket() {
		new ClientSocket(this);

		//进入聊天室模块
		PrintWriter out = null;
		try {
			out = new PrintWriter(ClientSocket.socket.getOutputStream());

			out.println(ChatUtil.FRIEND_LIST);// 好友列表标识符
			System.out.println(ChatUtil.FRIEND_LIST);
			out.flush();// 刷新
			
			String str = u.getName() + ":" + u.getSex();
			out.println(str);// 某某:性别
			System.out.println(str);
			out.flush();// 刷新

			out.println(ChatUtil.OPEN_ROOM);// 聊天室标识符
			System.out.println(ChatUtil.OPEN_ROOM);
			out.flush();
			str = "【" + u.getName() + "】" + "进入了聊天室";
			out.println(str);// 某某进入聊天室
			System.out.println(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ChatRoomGUI actionPerformed(ActionEvent e)");
		//群聊和私聊模块
		// 群聊和私聊处理事件
		if (e.getActionCommand().equals("发送")) {
			try {
				PrintWriter pw = new PrintWriter(
						ClientSocket.socket.getOutputStream());
				if (checkBox.isSelected()) {// 如果私聊复选框选中则发送给对应用户私聊消息
					pw.println(ChatUtil.PRIVATE_CHAT);
					System.out.println(ChatUtil.PRIVATE_CHAT);
					pw.flush();
					
					
					String str = "【" + this.u.getName() + "】对【"
							+ this.box.getSelectedItem() + "】说："
							+ sendText.getText();
					pw.println(str);
					pw.flush();
					
					System.out.println(str);

				} else {// 如果私聊复选框未选中则发送给所有用户群聊消息
					pw.println(ChatUtil.PUBLIC_CHAT);
					System.out.println(ChatUtil.PUBLIC_CHAT);
					pw.flush();

					String str = "【" + this.u.getName() + "】说："
							+ sendText.getText();
					pw.println(str);
					System.out.println(str);
					pw.flush();
				}
				
				//当发送完后发送框置为空
				ChatRoomGUI.this.sendText.setText("");
				
			} catch (IOException e1) {
				// 异常提示框
				// JOptionPane.showMessageDialog(this, "发送异常",
				// "发送提醒",JOptionPane.INFORMATION_MESSAGE);

				e1.printStackTrace();
			}
			
			System.out.println("action结束");
		}
		//刷新模块
		// 刷新处理事件
		if (e.getActionCommand().equals("刷新")) {
			try {

				PrintWriter pw = new PrintWriter(
						ClientSocket.socket.getOutputStream());
				pw.println(ChatUtil.REFRESH);
				pw.flush();

			} catch (IOException e1) {
				// JOptionPane.showMessageDialog(this, "刷新异常",
				// "刷新提醒",JOptionPane.INFORMATION_MESSAGE);
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 设置聊天室窗体
	 */
	public void init() {
		System.out.println("进入ChatRoomGUI init()");
		this.setTitle("【" + u.getName() + "】" + "进入了聊天室");

		//p1面板设置，设置聊天频道，私聊和群聊
		setNorthWestP1();
		//p2面板设置，设置私聊
		setSouthWestP2();
		//p3面板设置，设置消息发送框
		setSouthP3();
		//p4面板设置，把p2,p3设置到p4面板中去
		setPanel4();
		//p5面板设置，把p1，p4设置到p5面板中去
		setPanel5();
		//p6面板设置，设置在线好友列表
		setPanel6();
		//把左右p5,p6面板设置到p7去
		setPanel7();

		this.setResizable(false);
		this.setSize(600, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		System.out.println("初始化完毕");
	}

	/**
	 * 把左右p5,p6面板设置到p7去
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
	 * p6面板设置，设置在线好友列表
	 */
	private void setPanel6() {
		System.out.println("setPanel6()");
		p6 = new JPanel();
		p6.setLayout(new BorderLayout());
		listModel = new DefaultListModel<String>();
		// listModel.addElement("张三");
		// listModel.addElement("李四");
		// listModel.addElement("王五");

		list = new JList<String>(listModel);
		list.setVisibleRowCount(18);
		list.setFixedCellHeight(25);
		list.setFixedCellWidth(150);
		list.setBackground(Color.PINK);
		
		js3 = new JScrollPane(list);
		js3.setBackground(Color.MAGENTA);
		js3.setBorder(new TitledBorder("好友列表"));
		p6.add(js3, BorderLayout.NORTH);
//		refreshBtn = new JButton("刷新");
//		p6.add(refreshBtn, BorderLayout.SOUTH);
	}

	/**
	 * p5面板设置，把p1，p4设置到p5面板中去
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
	 * p4面板设置，把p2,p3设置到p4面板中去
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
	 * p3面板设置，设置消息发送框
	 */
	private void setSouthP3() {
		System.out.println("setSouthP3()");
		sendText = new JTextField(30);
		sendText.setBackground(Color.WHITE);
		sendBtn = new JButton("发送");
		
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(Color.MAGENTA);
		p3.add(sendText);
		p3.add(sendBtn);
	}

	/**
	 * p2面板设置，设置私聊
	 */
	private void setSouthWestP2() {
		System.out.println("setSouthWestP2()");
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		lab = new JLabel("对");
		box = new JComboBox<String>();
		box.addItem("所有人");
		checkBox = new JCheckBox();
		checkBox.setText("私聊");
		p2.setBackground(Color.magenta);
		p2.add(lab);
		p2.add(box);
		p2.add(checkBox);
	}

	/**
	 * p1面板设置，设置聊天频道，私聊和群聊
	 */
	private void setNorthWestP1() {
		System.out.println("setNorthWestP1()");
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 1));

		pubTextArea = new JTextArea(10, 10);
		pubTextArea.setEditable(false);
		pubTextArea.setBackground(Color.PINK);
		js1 = new JScrollPane(pubTextArea);
		js1.setBorder(new TitledBorder("群聊频道"));// 设置滑动面板边框标题
		js1.setBackground(Color.MAGENTA);
		p1.add(js1);

		priTextArea = new JTextArea(10, 10);
		priTextArea.setEditable(false);
		priTextArea.setBackground(Color.PINK);
		js2 = new JScrollPane(priTextArea);
		js2.setBorder(new TitledBorder("私聊频道"));
		js2.setBackground(Color.MAGENTA);
		p1.add(js2);
	}
}
