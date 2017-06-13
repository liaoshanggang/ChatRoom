package com.lanqiao.tcpqq9.client;

public final class ChatUtil {
	// 1、静态常量，标识服务器地址和端口唯一位置//10.170.252.33//172.20.62.108
	public static final String ADDRESS = "172.20.62.108";// localhost
	public static final int PORT = 1766;// 端口

	// 标识码、标识窗口功能模块
	public static final String FRIEND_LIST = "FRIEND_LIST";// 好友列表
	public static final String SPEAK_INFO = "SPEAK_INFO";// 说话消息
	public static final String OPEN_ROOM = "OPEN_ROOM";// 进入聊天室
	public static final String PRIVATE_CHAT = "PRIVATE_CHAT";// 私聊
	public static final String PUBLIC_CHAT = "PUBLIC_CHAT";// 群聊
	public static final String CLOSE_ROOM = "CLOSE_ROOM";// 离开房间
	public static final String REFRESH = "REFRESH";// 刷新

}
