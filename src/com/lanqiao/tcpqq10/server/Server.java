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
	int c;
	public Server(ServerGUI sgui) {
		System.out.println("public Server(ServerGUI sgui) {");
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
				String name = s.getInetAddress().getHostAddress();
				serverGUI.setTitle(name);
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

	// �����߳��ڲ�����ɷ���˶�д����
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
				while ((messege = br.readLine()) != null) {// ��һ�ζ�ȡ���Ǳ�ʶ
					System.out.println("ģ����Ϣ��" + messege);
					Server.this.showInTextAreaCenter(messege);
					switch (messege) {
					case ChatUtil.FRIEND_LIST:
						messege = br.readLine();// �ڶ��ζ�
						System.out.println(ChatUtil.FRIEND_LIST);
						System.out.println(messege);

						// ����ַ�����Ϣ������usersList�б���ȥ
						String[] userinfo = messege.split(":");
						User user = new User(userinfo[0], userinfo[1], s);
						usersList.add(user);

						// ���б��л�ȡ�û���Ϣ�������ַ�����Ϣ
						StringBuffer sbf = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user = usersList.get(i);
							sbf.append(user.getName() + "(" + user.getSex()
									+ "):");
						}
						// ɾ�����һ��:ð��
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
						
						//����һ���û�����ʱ���˳����������쳣������Ҫ�ж��������Ƿ�û�ˡ�
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

		// �㲥������Ϣ
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
