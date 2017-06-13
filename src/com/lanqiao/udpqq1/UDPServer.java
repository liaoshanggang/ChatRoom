package com.lanqiao.udpqq1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class UDPServer {
	DatagramSocket ds;
	DatagramPacket indp;
	DatagramPacket outdp;
	List<String> userslist = new ArrayList<String>();

	// static int count = 0;
	public UDPServer() {
		new Thread(new UDPClientSocketSendReceive()).start();
	}

	private class UDPClientSocketSendReceive implements Runnable {

		@Override
		public void run() {
			try {
				ds = new DatagramSocket(2000);
				byte[] buf = new byte[512];
				while (true) {
					// 接收从客户端发来的消息
					// int count = 0;
					// System.out.println("收到"+(++count) +"客户端发来的条消息");
					indp = new DatagramPacket(buf, buf.length);
					ds.receive(indp);// 接送
					String receive = new String(indp.getData(), 0,
							indp.getLength());
					System.out.println("服务器端接到客户端发来的消息：" + receive);
					userslist.add(receive);
					// 响应客户端
					SocketAddress address = indp.getSocketAddress();

					StringBuffer sbf = new StringBuffer();
					for (int i = 0; i < userslist.size(); i++) {
						sbf.append(userslist.get(i));
					}
					buf = new String(sbf.toString()).getBytes();
					outdp = new DatagramPacket(buf, buf.length, address);
					ds.send(outdp);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// new Thread(new UDPClientSocketSendReceive()).start();
		}

	}

	public static void main(String[] args) {
		System.out.println("服务器开启");
		new UDPServer();
	}
}
