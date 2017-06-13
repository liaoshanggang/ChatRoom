package com.lanqiao.tcpqq10.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

/**
 * @Description: 服务器界面
 * @author forward
 * @date 2017年5月1日 下午5:09:45
 * @version V1.0
 */
@SuppressWarnings("serial")
public class ServerGUI extends JFrame implements ActionListener {
	// 停止，取消按钮
	// JButton startButton;
	// JButton stopButton;

	// JLabel labelLeft;
	// 大标题
	JLabel labelRight;

	// 5个方向面板
	JPanel jPanelTop;

	// 5个方向滑动窗格
	JScrollPane jScrollPaneLeft;
	JScrollPane jScrollPaneCenter;
	JScrollPane jScrollPaneRight;
	JScrollPane jScrollPaneBottom;
	// 5个方向滑动窗格里面的文本区域
	JTextArea jTextAreaLeft;
	JTextArea jTextAreaCenter;
	JTextArea jTextAreaRight;
	JTextArea jTextAreaBottom;

	// Server server;

	public ServerGUI() {
		init();
		this.setVisible(true);
		// 界面启动时启动服务器端socket
		new Server(this);
	}

	private void init() {
//		try {
//			this.setTitle("ip：" + InetAddress.getLocalHost().getHostAddress()
//					+ "服务器开启");
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
		this.setSize(600, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		//this.setResizable(false);

		// 依次设置5个方向位置的面板
		setjPanelTop();
		setjPanelBottom();
		setjPanelLeft();
		setjPanelCenter();
		setjPanelRight();

		// startButton.addActionListener(this);
		// stopButton.addActionListener(this);

	}

	private void setjPanelRight() {

		jTextAreaRight = new JTextArea();
		jTextAreaRight.setEditable(false);
		jTextAreaRight.setBackground(Color.PINK);

		jScrollPaneRight = new JScrollPane(jTextAreaRight);
		jScrollPaneRight.setLayout(new ScrollPaneLayout());
		jScrollPaneRight.setPreferredSize(new Dimension(0, 420));

		this.add(jScrollPaneRight, BorderLayout.EAST);
	}

	private void setjPanelCenter() {

		jTextAreaCenter = new JTextArea();
		jTextAreaCenter.setEditable(false);
		jTextAreaCenter.setBackground(Color.PINK);

		jScrollPaneCenter = new JScrollPane(jTextAreaCenter);
		jScrollPaneCenter.setLayout(new ScrollPaneLayout());
		jScrollPaneCenter.setPreferredSize(new Dimension(100, 420));

		this.add(jScrollPaneCenter, BorderLayout.CENTER);
	}

	private void setjPanelLeft() {

		jTextAreaLeft = new JTextArea();
		jTextAreaLeft.setEditable(false);
		jTextAreaLeft.setBackground(Color.LIGHT_GRAY);

		jScrollPaneLeft = new JScrollPane(jTextAreaLeft);
		jScrollPaneLeft.setLayout(new ScrollPaneLayout());
		jScrollPaneLeft.setPreferredSize(new Dimension(270, 420));

		this.add(jScrollPaneLeft, BorderLayout.WEST);
	}

	public void setjPanelTop() {

		labelRight = new JLabel("聊天系统日记");
		labelRight.setFont(new Font("楷体", 0, 20));

		// startButton = new JButton("启动");
		// stopButton = new JButton("取消");
		// jPanelTop.add(startButton);
		// jPanelTop.add(cancelButton);

		jPanelTop = new JPanel();
		jPanelTop.setLayout(new FlowLayout());
		jPanelTop.setBackground(Color.WHITE);
		jPanelTop.add(labelRight);
		jPanelTop.setPreferredSize(new Dimension(0, 35));
		this.add(jPanelTop, BorderLayout.NORTH);
	}

	public void setjPanelBottom() {

		jTextAreaBottom = new JTextArea();
		jTextAreaBottom.setEditable(false);
		jTextAreaBottom.setBackground(Color.WHITE);

		jScrollPaneBottom = new JScrollPane(jTextAreaBottom);
		jScrollPaneBottom.setLayout(new ScrollPaneLayout());
		jScrollPaneBottom.setPreferredSize(new Dimension(0, 30));

		this.add(jScrollPaneBottom, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * if (e.getSource() == startButton) { server = new Server(this);
		 * server.start(); startButton.setEnabled(false); } if (e.getSource() ==
		 * cancelButton) { if (server != null) { server.interrupt();
		 * System.exit(0); } }
		 */
	}

	public static void main(String[] args) {
		new ServerGUI();
	}
}
