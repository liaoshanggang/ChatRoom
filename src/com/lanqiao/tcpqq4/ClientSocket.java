package com.lanqiao.tcpqq4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	Socket socket;
	ChatFrame cf;
	public ClientSocket(ChatFrame cf) {
		this.cf = cf;
		
		//客户端连接服务器，建立连�?
		try {
			socket = new Socket(ChatUtil.ADDRESS, ChatUtil.PORT);
			new Thread(new ClientReadWriteThread(socket)).start();;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace()	;
		}
		
	}
	
	class ClientReadWriteThread implements Runnable{
		Socket s;
		BufferedReader br;
		public ClientReadWriteThread(Socket socket) {
			this.s = socket;
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String messege;
			StringBuffer sbf = new StringBuffer();
			try {
				while((messege=br.readLine())!=null){
					System.out.println("标识符："+messege);
					switch (messege) {
					case ChatUtil.FRIEND_LIST:
						messege = br.readLine();
						System.out.println("用户信息�?"+messege);
						String[] info = messege.split(":");
						
						cf.listModel.removeAllElements();
						cf.box.removeAllItems();
						cf.box.addItem("�?有人");
						for (int i = 0; i < info.length; i++) {
							cf.listModel.addElement(info[i]);
							cf.box.addItem(info[i]);
						}
						break;
					case ChatUtil.OPEN_ROOM:
						messege = br.readLine();
						cf.area1.append(messege+"\n");
						cf.area2.append(messege+"\n");
						break;
					default:
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
