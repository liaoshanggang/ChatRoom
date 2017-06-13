package com.lanqiao.tcpqq1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	
	public ClientSocket() {
		//客户端连接服务器，建立连接
		try {
			Socket socket = new Socket(ChatUtil.ADDRESS, ChatUtil.PORT);
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
