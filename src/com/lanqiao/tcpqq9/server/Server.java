package com.lanqiao.tcpqq9.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lanqiao.tcpqq9.client.ChatUtil;
import com.lanqiao.tcpqq9.client.User;

/**
 * @Description: ��������
 * @author forward
 * @date 2017��4��29�� ����6:21:13
 * @version V1.0
 */
public class Server {
	// ���������û�����Ϣ
	List<User> usersList = new ArrayList<User>();
	// ���������û���������
	List<PrintWriter> printList = new ArrayList<PrintWriter>();
	ServerGUI serverGUI;
	ServerSocket ss;

	public Server(ServerGUI sgui) {
		this.serverGUI = sgui;

		try {
			ss = new ServerSocket(1766);

			String showText;
			int count = 0;
			while (true) {
				showText = "�ȴ��ͻ�������.....";
				System.out.println(showText);
				showInTextAreaLeft(showText);
				Socket s = ss.accept();
				String name = s.getInetAddress().getHostName();
				int port = s.getPort();
				showText = "��ǰ��" + (++count) + "�ͻ��ˣ�" + name + ":" + port
						+ "������";
				System.out.println(showText);
				showInTextAreaLeft(showText);
				// ��Ӷ�Ӧ�û���������
				printList.add(new PrintWriter(s.getOutputStream()));

				// �����߳��ڲ�����ɷ���˶�д����
				ServerReadWriteThread srwt = new ServerReadWriteThread(s);
				Thread t = new Thread(srwt);
				t.start();
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

	// �����߳��ڲ�����ɷ���˶�д����
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
				while ((message = br.readLine()) != null) {// ��һ�ζ�ȡ���Ǳ�ʶ
					System.out.println("ģ����Ϣ��" + message);
					Server.this.showInTextAreaCenter(message);
					switch (message) {
					case ChatUtil.FRIEND_LIST:
						message = br.readLine();// �ڶ��ζ�
						System.out.println("�ͻ���Ϣ��" + message);

						// ����ַ�����Ϣ������usersList�б���ȥ
						String[] userinfo = message.split(":");
						User user = new User(userinfo[0], userinfo[1], s);
						usersList.add(user);

						// ���б��л�ȡ�û���Ϣ�������ַ�����Ϣ
						StringBuffer sbf = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						// ɾ�����һ��:
						message = sbf.deleteCharAt(sbf.length() - 1).toString();

						sendAllMessge(ChatUtil.FRIEND_LIST);
						sendAllMessge(message);
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
						// ���˳�������ʱ,ɾ���û���Ϣ
						for (int i = 0; i < usersList.size(); i++) {
							User u = usersList.get(i);
							if (u.getSocket() == this.s) {
								System.out.println("���б���ɾ����" + u.getName());
								// u.getSocket().close();
								usersList.remove(u);
								break;
							}
						}
						// ���˳�������ʱ�����б���ƴ���ַ������������Ϳͻ���ˢ���û�����Ϣ
						StringBuffer sbf1 = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf1.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						if(usersList.size()!=0){							
							String info1 = sbf1.deleteCharAt(sbf1.length() - 1)
									.toString();
							sendAllMessge(ChatUtil.FRIEND_LIST);
							sendAllMessge(info1);
						}
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
					Server.this.showInTextAreaCenter(message);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// �㲥������Ϣ
		private void sendAllMessge(String msg) {
			for (int i = 0; i < printList.size(); i++) {
				PrintWriter pw = printList.get(i);
				pw.println(msg);
				pw.flush();
			}
		}

	}

//	public static void main(String[] args) {
//		new Server();
//	}

}
