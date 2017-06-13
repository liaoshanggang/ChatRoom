package com.lanqiao.udpqq1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClientSocket {
	DatagramSocket ds;
	InetAddress ia;
	DatagramPacket outdp;
	DatagramPacket indp;
	MainChatFrame cf;

	public UDPClientSocket(MainChatFrame cf) {
		this.cf = cf;
		// 1、建立客户端数据报套接字对象
		try {
			ds = new DatagramSocket();

			// 2、开始发送消息到指定服务器
			try {
				ia = InetAddress.getByName("127.0.0.1");
				// while(true){
				// 3、消息处理
				// 向服务器发送消息
				String name = cf.u.getName() + "进入了聊天室";
				byte[] buf = name.getBytes();
				outdp = new DatagramPacket(buf, buf.length, ia, 2000);

				ds.send(outdp);

				// 从服务器接送消息
				indp = new DatagramPacket(buf, buf.length);
				ds.receive(indp);
				String receive = new String(indp.getData(), 0, indp.getLength());
				cf.area1.append(receive + "\n");
				// }
			} catch (IOException e) {
				e.printStackTrace();
			}

			// new Thread(new UDPClientSocketSendReceive()).start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private class UDPClientSocketSendReceive implements Runnable {

		@Override
		public void run() {
			// 2、开始发送消息到指定服务器
			try {
				ia = InetAddress.getByName("127.0.0.1");
				// while(true){
				// 3、消息处理
				// 向服务器发送消息
				String name = cf.u.getName() + "进入了聊天室";
				byte[] buf = name.getBytes();
				outdp = new DatagramPacket(buf, buf.length, ia, 2000);

				ds.send(outdp);

				// 从服务器接送消息
				indp = new DatagramPacket(buf, buf.length);
				ds.receive(indp);
				String receive = new String(indp.getData(), 0, indp.getLength());
				cf.area1.append(receive + "\n");
				// }
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
