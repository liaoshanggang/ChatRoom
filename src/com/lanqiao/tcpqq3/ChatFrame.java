package com.lanqiao.tcpqq3;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

public class ChatFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel p1,p2,p3,p4,p5,p6,p7;
	JScrollPane js1,js2,js3;
	JTextArea area1,area2;
	JLabel lab;
	JComboBox<String> box;//私聊下拉列表
	JCheckBox checkBox;
	JTextField text;
	JButton but1,but2;
	JList<String> list;
	DefaultListModel<String> listModel;
	User u;
	
	public void init(){
		this.setTitle(u.getName()+"进入了聊天室");
		
		//p1面板设置，设置主聊天频道，私聊和公聊
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,1));
		
		area1 = new JTextArea(10,10);
		area1.setEditable(false);
		js1 = new JScrollPane(area1);
		js1.setBorder(new TitledBorder("主聊天频�?"));//设置滑动面板边框标题
		p1.add(js1);
		
		area2 = new JTextArea(10,10);
		area2.setEditable(false);
		js2 = new JScrollPane(area2);
		js2.setBorder(new TitledBorder("主聊天频�?"));
		p1.add(js2);
		
		//p2面板设置，设置私�?
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		lab = new JLabel("�?");
		box = new JComboBox<String>();
		box.addItem("�?有人");
		checkBox = new JCheckBox();
		checkBox.setText("私聊");
		p2.add(lab);
		p2.add(box);
		p2.add(checkBox);
		
		//p3面板设置，设置消息发送框
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		text = new JTextField(30);
		but1 = new JButton("发�??");
		p3.add(text);
		p3.add(but1);
		
		//p4面板设置，把p2,p3设置到p4面板中去
		p4 = new JPanel();
		p4.setLayout(new GridLayout(2,1));
		p4.add(p2);
		p4.add(p3);
		
		//p5面板设置，把p1，p4设置到p5面板中去
		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p1,BorderLayout.NORTH);
		p5.add(p4,BorderLayout.SOUTH);
		
		//p6面板设置，设置好友列�?
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
		p6.add(js3,BorderLayout.NORTH);
		but2 = new JButton("刷新");
		p6.add(but2,BorderLayout.SOUTH);
		
		//把左右p5,p6面板设置到p7�?
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
		//this.pack();
	}
	
	
	
	public ChatFrame(User u){
		this.u = u;
		init();
		//p2.add(box);
		//连接服务�?
		getSocket();
	}
	
	public void getSocket(){
		ClientSocket cs = new ClientSocket(this);
		PrintWriter out = null;
		try {
			out = new PrintWriter(cs.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(ChatUtil.FRIEND_LIST);//1008611
		out.flush();//刷新
		out.println(u.getName()+":"+u.getSex());//张三:性别
		out.flush();

	}
}
