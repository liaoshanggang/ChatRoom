package com.lanqiao.tcpqq8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	// 保存所有用户的信息
	List<User> usersList = new ArrayList<User>();
	// 保存所有用户的流对象
	List<PrintWriter> printList = new ArrayList<PrintWriter>();

	public Server() {
		try {

			ServerSocket ss = new ServerSocket(1766);

			int count = 0;
			while (true) {
				System.out.println("等待客户段连接.....");
				Socket s = ss.accept();
				String name = s.getInetAddress().getHostName();
				int port = s.getPort();
				System.out.println("当前第" + (++count) + "客户端：" + name + ":"
						+ port + "连接了");

				// 添加对应用户的流对象
				printList.add(new PrintWriter(s.getOutputStream()));

				// 开启线程内部类完成服务端读写操作
				ServerReadWriteThread srwt = new ServerReadWriteThread(s);
				Thread t = new Thread(srwt);
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 创建线程内部类完成服务端读写操作
	class ServerReadWriteThread implements Runnable {
		Socket s;
		BufferedReader br;

		public ServerReadWriteThread(Socket s) {
			this.s = s;
			try {
				br = new BufferedReader(new InputStreamReader(
						s.getInputStream()));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String message = null;
			try {
				while ((message = br.readLine()) != null) {// 第一次读取的是标识
					System.out.println("模块信息：" + message);
					switch (message) {
					case ChatUtil.FRIEND_LIST:
						message = br.readLine();// 第二次读
						System.out.println("客户信息：" + message);

						// 拆分字符串信息并存入usersList列表中去
						String[] userinfo = message.split(":");
						User user = new User(userinfo[0], userinfo[1], s);
						usersList.add(user);

						// 从列表中获取用户信息并连接字符串信息
						StringBuffer sbf = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						// 删除最后一个:
						String info = sbf.deleteCharAt(sbf.length() - 1)
								.toString();

						sendAllMessge(ChatUtil.FRIEND_LIST);
						sendAllMessge(info);
						break;
					case ChatUtil.OPEN_ROOM:
						message = br.readLine();
						sendAllMessge(ChatUtil.OPEN_ROOM);
						sendAllMessge(message);
						break;
					case ChatUtil.CLOSE_ROOM:
						message = br.readLine();
						sendAllMessge(ChatUtil.CLOSE_ROOM);
						sendAllMessge(message);
						//当退出聊天室时,删除用户信息
						for (int i = 0; i < usersList.size(); i++) {
							User u = usersList.get(i);
							if (u.getSocket() == this.s) {
								System.out.println("从列表中删除了" + u.getName());
								// u.getSocket().close();
								usersList.remove(u);
								break;
							}
						}
						//当退出聊天室时，从列表中拼接字符串并重新向发送客户端刷新用户的信息
						StringBuffer sbf1 = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf1.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						String info1 = sbf1.deleteCharAt(sbf1.length() - 1)
								.toString();
						sendAllMessge(ChatUtil.FRIEND_LIST);
						sendAllMessge(info1);
						break;
					case ChatUtil.PUBLIC_CHAT:
						message = br.readLine();
						sendAllMessge(ChatUtil.PUBLIC_CHAT);
						sendAllMessge(message);
						break;
					case ChatUtil.PRIVATE_CHAT:
						message = br.readLine();
						sendAllMessge(ChatUtil.PRIVATE_CHAT);
						sendAllMessge(message);
						break;
					/*
					 * case ChatUtil.REFRESH: StringBuffer sbf1 = new
					 * StringBuffer(); for (int i = 0; i < usersList.size();
					 * i++) { user = usersList.get(i);
					 * sbf1.append(user.getName() + "(" + user.getSex() + "):");
					 * } String info1 = sbf1.deleteCharAt(sbf1.length() - 1)
					 * .toString(); sendAllMessge(ChatUtil.FRIEND_LIST);
					 * sendAllMessge(info1); break;
					 */
					default:
						System.out.println("nothing");
						break;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 广播发送消息
		private void sendAllMessge(String msg) {
			for (int i = 0; i < printList.size(); i++) {
				PrintWriter pw = printList.get(i);
				pw.println(msg);
				pw.flush();
			}
		}

	}

	public static void main(String[] args) {
		new Server();
	}

}
