package com.lanqiao.tcpqq10.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lanqiao.tcpqq10.client.*;

/**
 * @Description: 服务器端
 * @author forward
 * @date 2017年4月29日 下午6:21:13
 * @version V1.0
 */
public class Server {
	// 保存所有用户的信息
	List<User> usersList = new ArrayList<User>();
	// 保存所有用户的流对象
	List<PrintWriter> printList = new ArrayList<PrintWriter>();
	
	ServerGUI serverGUI;
	ServerSocket ss;
	int c;
	public Server(ServerGUI sgui) {
		System.out.println("public Server(ServerGUI sgui) {");
		this.serverGUI = sgui;

		try {
			ss = new ServerSocket(1766);

			String showText;
			int count = 0;
			while (true) {
				showText = "等待客户端连接.....";
				System.out.println(showText);
				showInTextAreaLeft(showText);
				Socket s = ss.accept();
				String name = s.getInetAddress().getHostAddress();
				serverGUI.setTitle(name);
				int port = s.getPort();
				showText = "当前第" + (++count) + "客户端：" + name + ":" + port
						+ "连接了";
				System.out.println(showText);
				showInTextAreaLeft(showText);
				// 添加对应用户的流对象
				printList.add(new PrintWriter(s.getOutputStream()));
				
				// 开启线程内部类完成服务端读写操作
				ServerReadWriteThread srwt = new ServerReadWriteThread(s);
				Thread t = new Thread(srwt);
				t.start();
				System.out.println(c++);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void showInTextAreaLeft(String showText) {
		serverGUI.jTextAreaLeft.append(showText + "\n");
	}

	private void showInTextAreaCenter(String showText) {
		serverGUI.jTextAreaCenter.append(showText + "\n");
	}

	// 创建线程内部类完成服务端读写操作
	class ServerReadWriteThread implements Runnable {
		Socket s;
		BufferedReader br;
		
		public ServerReadWriteThread(Socket s) {
			System.out.println("socket"+s);
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
			String messege = null;
			try {
				while ((messege = br.readLine()) != null) {// 第一次读取的是标识
					System.out.println("模块信息：" + messege);
					Server.this.showInTextAreaCenter(messege);
					switch (messege) {
					case ChatUtil.FRIEND_LIST:
						messege = br.readLine();// 第二次读
						System.out.println(ChatUtil.FRIEND_LIST);
						System.out.println(messege);

						// 拆分字符串信息并存入usersList列表中去
						String[] userinfo = messege.split(":");
						User user = new User(userinfo[0], userinfo[1], s);
						usersList.add(user);

						// 从列表中获取用户信息并连接字符串信息
						StringBuffer sbf = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						// 删除最后一个:冒号
						messege = sbf.deleteCharAt(sbf.length() - 1).toString();

						sendAllMessge(ChatUtil.FRIEND_LIST);
						sendAllMessge(messege);
						break;
					case ChatUtil.OPEN_ROOM:
						messege = br.readLine();
						System.out.println(ChatUtil.OPEN_ROOM);
						System.out.println(messege);
						sendAllMessge(ChatUtil.OPEN_ROOM);
						sendAllMessge(messege);
						break;
					case ChatUtil.CLOSE_ROOM:
						messege = br.readLine();
						System.out.println(ChatUtil.CLOSE_ROOM);
						System.out.println(messege);
						sendAllMessge(ChatUtil.CLOSE_ROOM);
						sendAllMessge(messege);
						// 当退出聊天室时,删除用户信息
						for (int i = 0; i < usersList.size(); i++) {
							User u = usersList.get(i);
							if (u.getSocket() == this.s) {
								System.out.println("从列表中删除了" + u.getName());
								// u.getSocket().close();
								usersList.remove(u);
								break;
							}
						}
						// 当退出聊天室时，从列表中拼接字符串并重新向发送客户端刷新用户的信息
						StringBuffer sbf1 = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf1.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						
						//当第一个用户进来时，退出会有数组异常，所以要判断聊天室是否没人。
						if(sbf1.length()!=0){							
							String info1 = sbf1.deleteCharAt(sbf1.length() - 1)
									.toString();
							sendAllMessge(ChatUtil.FRIEND_LIST);
							sendAllMessge(info1);
						}
						break;
					case ChatUtil.PUBLIC_CHAT:
						messege = br.readLine();
						System.out.println(ChatUtil.PUBLIC_CHAT);
						System.out.println(messege);
						sendAllMessge(ChatUtil.PUBLIC_CHAT);
						sendAllMessge(messege);
						break;
					case ChatUtil.PRIVATE_CHAT:
						messege = br.readLine();
						System.out.println(ChatUtil.PRIVATE_CHAT);
						System.out.println(messege);
						sendAllMessge(ChatUtil.PRIVATE_CHAT);
						sendAllMessge(messege);
						break;

					// case ChatUtil.REFRESH:
					// StringBuffer sbf2 = new StringBuffer();
					// for (int i = 0; i < usersList.size(); i++) {
					// user = usersList.get(i);
					// sbf2.append(user.getName() + "(" + user.getSex()
					// + "):");
					// }
					// String info2 = sbf2.deleteCharAt(sbf2.length() - 1)
					// .toString();
					// sendAllMessge(ChatUtil.FRIEND_LIST);
					// sendAllMessge(info2);
					// break;

					default:
						System.out.println("nothing");
						break;
					}
					Server.this.showInTextAreaCenter(messege);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 广播发送消息
		private void sendAllMessge(String msg) {
			for (int i = 0; i < printList.size(); i++) {
				PrintWriter pw = printList.get(i);
				System.out.println(printList);
				pw.println(msg);
				pw.flush();
			}
		}

	}

//	public static void main(String[] args) {
//		new Server();
//	}

}
