package com.lanqiao.tcpqq3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	//���������û�����Ϣ
	List<User> usersList = new ArrayList<User>();
	//���������û���������
	List<PrintWriter> printList = new ArrayList<PrintWriter>();
	public Server() {
		try {

			ServerSocket ss = new ServerSocket(1766);

			int count = 0;
			while (true) {
				System.out.println("�ȴ��ͻ�������.....");
				Socket s = ss.accept();
				String name = s.getInetAddress().getHostName();
				int port = s.getPort();
				System.out.println("��ǰ��" + (++count) + "�ͻ��ˣ�" + name + ":"
						+ port + "������");
				//��Ӷ�Ӧ�û���������
				printList.add(new PrintWriter(s.getOutputStream()));
				ServerReadWriteThread srwt = new ServerReadWriteThread(s);
				Thread t = new Thread(srwt);
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	class ServerReadWriteThread implements Runnable{
		Socket s;
		BufferedReader br;
		public ServerReadWriteThread(Socket s) {
			this.s = s;
			try {
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String message = null;
			try {
				while((message=br.readLine())!=null){//��һ�ζ�ȡ���Ǳ�ʶ
					System.out.println("ģ����Ϣ��"+message);
					switch (message) {
					case ChatUtil.FRIEND_LIST:
						message = br.readLine();//�ڶ��ζ�
						System.out.println("�ͻ���Ϣ��"+message);
						String[] userinfo = message.split(":");
						User user = new User(userinfo[0],userinfo[1],s); 
						usersList.add(user);
						sendAllMessge(ChatUtil.FRIEND_LIST);
						StringBuffer sbf = new StringBuffer();
						for (int i = 0; i < usersList.size(); i++) {
							user  = usersList.get(i);
							sbf.append(user.getName()+"("+user.getSex()+"):");
						}
						String info = sbf.deleteCharAt(sbf.length()-1).toString();
						sendAllMessge(info);
						break;
					default:
						System.out.println("nothing");
						break;
					}
					
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void sendAllMessge(String msg) {
			for (int i = 0; i < printList.size(); i++) {
				PrintWriter pw = printList.get(i);
				pw.println(msg);
				pw.flush();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Server s = new Server();
	}

}
