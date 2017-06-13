package com.lanqiao.tcpqq7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	static Socket socket;
	ChatFrame cf;
	
	public ClientSocket(ChatFrame cf) {
		this.cf = cf;
		
		//客户端连接服务器，建立连接
		try {
			socket = new Socket(ChatUtil.ADDRESS, ChatUtil.PORT);
			//开启线程内部类完成客户端的读写操作
			new Thread(new ClientReadWriteThread(socket)).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace()	;
		}
		
	}
	
	//创建线程内部类完成客户端的读写操作
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
			try {
				while((messege=br.readLine())!=null){
					System.out.println("标识符："+messege);
					switch (messege) {
					case ChatUtil.FRIEND_LIST:
						messege = br.readLine();
						System.out.println("用户信息："+messege);
						String[] info = messege.split(":");
						
						cf.listModel.removeAllElements();
						cf.box.removeAllItems();
						cf.box.addItem("所有人");
						
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
						
					case ChatUtil.CLOSE_ROOM:
						messege = br.readLine();
						cf.area1.append(messege+"\n");
						cf.area2.append(messege+"\n");
						break;
						
					case ChatUtil.PUBLIC_CHAT:
						messege = br.readLine();
						cf.area1.append(messege+"\n");
						break;
						
					case ChatUtil.PRIVATE_CHAT:
						messege = br.readLine();
						System.out.println("PRIVATE_CHAT"+messege);
						System.out.println(cf.u.getName());
						//根据当前的信息是否包含当前客户端的信息，如果包含的则对这私聊这两个客户端发送信息。
						if(messege.contains(cf.u.getName())){
							cf.area2.append(messege+"\n");							
						}
						break;
					/*case ChatUtil.REFRESH:
						messege = br.readLine();
						System.out.println("用户信息："+messege);
						String[] info1 = messege.split(":");
						
						cf.listModel.removeAllElements();
						cf.box.removeAllItems();
						cf.box.addItem("所有人");
						for (int i = 0; i < info1.length; i++) {
							cf.listModel.addElement(info1[i]);
							cf.box.addItem(info1[i]);
						}
						break;*/
					default:
						System.out.println("Nothing");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
