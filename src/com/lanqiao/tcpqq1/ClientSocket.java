package com.lanqiao.tcpqq1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	
	public ClientSocket() {
		//�ͻ������ӷ���������������
		try {
			Socket socket = new Socket(ChatUtil.ADDRESS, ChatUtil.PORT);
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
}
