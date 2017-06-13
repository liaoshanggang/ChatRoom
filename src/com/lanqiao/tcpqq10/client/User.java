package com.lanqiao.tcpqq10.client;

import java.net.Socket;
/**
 * @Description: 用户类，存放登录界面的信息
 * @author forward
 * @date 2017年4月29日 上午9:30:34
 * @version V1.0
 */
public class User {
	String name;//姓名
	String sex;//性别
	Socket socket;
	
	public User() {
	}
	
	public User(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}

	public User(String name, String sex, Socket socket) {
		super();
		this.name = name;
		this.sex = sex;
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public String toString() {
		return "User [" + name + "][" + sex + "]";
	}

}
