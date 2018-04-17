package cn.itcast.server;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	
	public static void main(String[] args) throws Exception {
		// ServerSocket 对象可以监听端口
		ServerSocket serversocket = new ServerSocket(8888);
		while(true) {
			Socket socket = serversocket.accept();	// 等待客户端的连接请求，一旦有请求过来，就结束阻塞，返回客户端对象
			//System.out.println(socket.getInetAddress());
			// 一旦有客户来访问， 就另开一个新线程去提供服务， main线程继续等待下一个客户的连接
			new Thread(new MyService(socket)).start();

		}
	}

}
