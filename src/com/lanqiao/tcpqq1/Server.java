package com.lanqiao.tcpqq1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

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
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
	}

}
