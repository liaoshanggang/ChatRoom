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
 * @Description: 登录界面
 * @author forward
 * @date 2017年4月29日 上午9:14:34
 * @version V1.0
 */
@SuppressWarnings("serial")
public class LoginGUI extends JFrame implements ActionListener {
	// 连接，取消按钮
	JButton loginBtn;
	JButton cancelBtn;

	// 用户名
	JTextField textName;
	// 性别单选按钮
	JRadioButton radioMan, radioWoman, radioSecurity;
	// 单选按钮组
	String sex;
	ButtonGroup gb;
	// 服务器IP、端口
	JTextField textAddress;
	JTextField textPort;

	public LoginGUI() {
		init();
		this.setVisible(true);
		// 设置关闭后程序停止
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 验证用户是否填写信息，如果没有填写，弹出提示对话框并返回
		// 如果都填写完则获取用户输入的姓名和性别的值
		String name = textName.getText();
		if ("".equals(textName.getText())) {
			JOptionPane.showMessageDialog(this, "请填写姓名");
			return;
		}

		if (radioMan.isSelected()) {
			sex = radioMan.getText();
		} else if (radioWoman.isSelected()) {
			sex = radioWoman.getText();
		} else if (radioSecurity.isSelected()) {
			sex = radioSecurity.getText();
		} else {
			JOptionPane.showMessageDialog(this, "请选择性别");
			return;// 直接返回
		}

		// 检查服务器地址和端口是否正确
		try {
			// 获取登陆界面地址和端口
			String inAddress = textAddress.getText();
			String outPort = textPort.getText();

			if (inAddress.equals(ChatUtil.ADDRESS)
					&& outPort.equals(String.valueOf(ChatUtil.PORT))) {// 如果正确,
				// 隐藏当前界面
				this.setVisible(false);

				// 显示聊天界面
				User u = new User(name, sex);
				System.out.println("用户：" + name + "性别：" + sex);
				new ChatRoomGUI(u);
				// System.out.println("1、登录成功进入主聊天界面");

			} else {// 如果不正确
				// 显示提示框
				JOptionPane.showMessageDialog(this, "服务器地址或端口不正确,请重新输入",
						"连接失败", JOptionPane.INFORMATION_MESSAGE);
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
		this.setTitle("登陆界面");
		this.setBounds(300, 300, 300, 300);

		// 不能放大缩小
		this.setResizable(false);

		// 获取屏幕大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// 让窗口居中显示
		this.setLocation(screenSize.width / 2 - 400 / 2,
				screenSize.height / 2 - 300 / 2);

		// 获取东，南，西，北，中每个面板，并设置位置
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

		// 设置透明
		northPanel.setOpaque(false);
		southPanel.setOpaque(false);
		westPanel.setOpaque(false);
		centerPanel.setOpaque(false);

	}

	// 设置中间面板
	private JPanel getCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// 设置用户名，为不可编辑的客户端本地主机地址
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

		// 设置性别单选
		JPanel jpanel = new JPanel();// 设置一个面板添加性别单选按钮

		this.radioMan = new JRadioButton("男");
		this.radioWoman = new JRadioButton("女");
		this.radioSecurity = new JRadioButton("保密");

		jpanel.setPreferredSize(new DimensionUIResource(150, 25));
		gb = new ButtonGroup();

		jpanel.add(radioMan);
		jpanel.add(radioWoman);
		jpanel.add(radioSecurity);

		this.gb.add(radioMan);
		this.gb.add(radioWoman);
		this.gb.add(radioSecurity);

		// 设置服务器文本框
		this.textAddress = new JTextField();
		textAddress.setText(ChatUtil.ADDRESS);
		textAddress.setEditable(true);
		textAddress.setPreferredSize(new DimensionUIResource(150, 25));

		// 设置端口文本框
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

	// 设置西面板
	private JPanel getWestPanel() {
		JPanel westPanel = new JPanel();

		westPanel.setPreferredSize(new Dimension(80, 0));
		// westPanel.setBackground(Color.RED);
		westPanel.setLayout(new FlowLayout());

		// 西面板分别添加用户名、性别、服务器IP、端口号标签
		JLabel label0 = new JLabel("用户名：");
		label0.setPreferredSize(new Dimension(70, 30));

		JLabel label1 = new JLabel("性别：");
		label1.setPreferredSize(new Dimension(70, 25));

		JLabel label2 = new JLabel("服务器IP：");
		label2.setPreferredSize(new Dimension(70, 25));

		JLabel label3 = new JLabel("端口号：");
		label3.setPreferredSize(new Dimension(70, 25));

		westPanel.add(label0);
		westPanel.add(label1);
		westPanel.add(label2);
		westPanel.add(label3);

		westPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		return westPanel;
	}

	// 设置东面板
	private JPanel getEastPanel() {
		JPanel eastPanel = new JPanel();
		return eastPanel;
	}

	// 设置南面板
	private JPanel getSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(0, 80));
		southPanel.setBackground(Color.white);

		// 南面板添2个按钮，连接和取消按钮
		loginBtn = new JButton("连接");
		cancelBtn = new JButton("取消");
		loginBtn.setPreferredSize(new Dimension(60, 30));
		cancelBtn.setPreferredSize(new Dimension(60, 30));
		southPanel.add(loginBtn);
		southPanel.add(cancelBtn);

		return southPanel;
	}

	// 设置北面板
	private JPanel getNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(0, 50));
		northPanel.setBackground(Color.WHITE);
		return northPanel;
	}
}
