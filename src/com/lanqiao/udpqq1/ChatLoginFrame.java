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
	// 标签姓名，地址，端口
	JLabel labName, labAaddress, labPart;
	// 对应标签的姓名，地址，端口的文本框
	JTextField textName, textAddress, textPort;
	// 性别单选按钮
	JRadioButton radioMan, radiowoman, radioSecurity;
	// 单选按钮组
	ButtonGroup gb;
	// 连接，断开按钮
	JButton butConnet, butBrock;
	// 获取单选按钮上的性别
	String sex;

	/**
	 * 登陆界面初始化
	 */
	public void init() {

		this.setTitle("登录界面");
		this.setSize(350, 200);
		this.setResizable(false);// 设置不可以调整大小
		// 设置窗口相对于指定组件的位置。设置为null表面窗口将置于屏幕的中央
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public ChatLoginFrame() {
		init();
		// 面板p1,p2,p3,p4的设置
		setPanel1();

		setPanel2();

		setPanel3();

		setPanel4();

		// 为连接按钮绑定事件监听器
		butConnet.addActionListener(this);

		this.getContentPane().add(p4);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new ChatLoginFrame();// 登陆界面开始
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
		} else if (radiowoman.isSelected()) {
			sex = radiowoman.getText();
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
			if (inAddress.equals(FlagUtil.ADDRESS)
					&& outPort.equals(String.valueOf(FlagUtil.PORT))) {

				// 隐藏当前界面
				this.setVisible(false);

				// 显示聊天界面
				User u = new User(name, sex);
				System.out
						.println("1、LoginFrame actionPerformed(ActionEvent e):"
								+ u.toString());
				new MainChatFrame(u);
				// System.out.println("1、登录成功进入主聊天界面");

			} else {
				// 显示提示框
				JOptionPane.showMessageDialog(this, "服务器地址或端口不正确,请重新输入",
						"连接失败", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 面板p4设置，也就是把p1,p2,p3设置到p4里面
	 */
	private void setPanel4() {
		p4 = new JPanel(new GridLayout(3, 1));
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
	}

	/**
	 * 面板p3设置，该面板有连接，断开按钮
	 */
	private void setPanel3() {
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		butConnet = new JButton("连接");
		butBrock = new JButton("断开");
		p3.add(butConnet);
		p3.add(butBrock);
	}

	/**
	 * 面板p2设置，该面板有地址，端口标签和文本框
	 */
	private void setPanel2() {
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labAaddress = new JLabel("地址：");
		textAddress = new JTextField(10);
		textAddress.setText(FlagUtil.ADDRESS);

		labPart = new JLabel("端口");
		textPort = new JTextField(10);
		textPort.setText(String.valueOf(FlagUtil.PORT));

		p2.add(labAaddress);
		p2.add(textAddress);
		p2.add(labPart);
		p2.add(textPort);
	}

	/**
	 * 面板p1设置，该面板有用户的初始信息，姓名，性别
	 */
	private void setPanel1() {
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		labName = new JLabel("姓名：");
		textName = new JTextField(10);
		
		// 设置用户名为当地地址
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
		radioMan = new JRadioButton("男");
		radiowoman = new JRadioButton("女");
		radioSecurity = new JRadioButton("保密");

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
