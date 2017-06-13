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
		// 1�������ͻ������ݱ��׽��ֶ���
		try {
			ds = new DatagramSocket();

			// 2����ʼ������Ϣ��ָ��������
			try {
				ia = InetAddress.getByName("127.0.0.1");
				// while(true){
				// 3����Ϣ����
				// �������������Ϣ
				String name = cf.u.getName() + "������������";
				byte[] buf = name.getBytes();
				outdp = new DatagramPacket(buf, buf.length, ia, 2000);

				ds.send(outdp);

				// �ӷ�����������Ϣ
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
			// 2����ʼ������Ϣ��ָ��������
			try {
				ia = InetAddress.getByName("127.0.0.1");
				// while(true){
				// 3����Ϣ����
				// �������������Ϣ
				String name = cf.u.getName() + "������������";
				byte[] buf = name.getBytes();
				outdp = new DatagramPacket(buf, buf.length, ia, 2000);

				ds.send(outdp);

				// �ӷ�����������Ϣ
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
