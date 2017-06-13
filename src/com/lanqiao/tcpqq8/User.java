package com.lanqiao.tcpqq8;

import java.net.Socket;

public class User {
	String name;
	String sex;
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
